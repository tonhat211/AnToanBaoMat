package DAO;

import model.Constant;
import model.VerifyCode;
import service.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class VerifyCodeDAO implements IDAO<VerifyCode> {
    public static VerifyCodeDAO getInstance(){
        return new VerifyCodeDAO();
    }

    @Override
    public int insert(VerifyCode verifyCode) {
        int re=0;
        Random rand = new Random();
        String code ="";
        for(int i=0;i<6; i++) {
            code+= String.valueOf(rand.nextInt(9)+1);
        }
        verifyCode.setCode(code);
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into verifycode (code,email,isVerify) values (?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, verifyCode.getCode());
            pst.setString(2, verifyCode.getEmail());
            pst.setInt(3, verifyCode.getIsVerify());
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String insertNewCode(VerifyCode verifyCode) {
        int re=0;
        Random rand = new Random();
        String code ="";
        for(int i=0;i<6; i++) {
            code+= String.valueOf(rand.nextInt(9)+1);
        }
        verifyCode.setCode(code);
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into verifycode (code,email,isVerify) values (?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, verifyCode.getCode());
            pst.setString(2, verifyCode.getEmail());
            pst.setInt(3, verifyCode.getIsVerify());
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            if(re==1) return code;
            else return "insert code to database failed";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int update(VerifyCode verifyCode) {
        return 0;
    }

    @Override
    public int delete(VerifyCode verifyCode) {
        return 0;
    }

    @Override
    public ArrayList<VerifyCode> selectAll() {
        return null;
    }

    @Override
    public VerifyCode selectById(int id) {
        return null;
    }

    public VerifyCode selectTheLastCodeOf(String emailin) {
        VerifyCode re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from verifycode where email = ? ORDER by id desc LIMIT 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, emailin);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String code = rs.getString("code");
                String email = rs.getString("email");
                Timestamp time = rs.getTimestamp("time");
                int isVerify = rs.getInt("isVerify");
                re = new VerifyCode(code, email, time, isVerify);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int disableCode(String code) {
        int re = 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update verifycode set isVerify = ? where code = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Constant.USED_CODE);
            pst.setString(2, code);
            re = pst.executeUpdate();
            return re;

        } catch (SQLException e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    public int verifyCode(String codein, String emailin) {
        boolean res = false;
        VerifyCode code = VerifyCodeDAO.getInstance().selectTheLastCodeOf(emailin);
        if (code == null) {
            return Constant.WRONG_CODE;
        }
        if (!codein.equals(code.getCode())) {
            return Constant.WRONG_CODE;
        }
        if (code.getIsVerify() == 0) {
            return Constant.USED_CODE;
        }
        long codeTime = code.getTime().getTime();
        long currentTimestamp = System.currentTimeMillis();
        long timeDifferenceMillis = currentTimestamp - codeTime;

        long seconds = timeDifferenceMillis / 1000;
        if (seconds > 0 && seconds < 300) {
            VerifyCodeDAO.getInstance().disableCode(codein);
            return Constant.USED_CODE;
        } else {
            return Constant.EXPIRED_CODE;
        }
    }

    public static void main(String[] args) {
        int re = VerifyCodeDAO.getInstance().verifyCode("11111","2003tonhat@gmail.com");
        System.out.println(re);
    }
}

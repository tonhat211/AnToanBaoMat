package DAO;

import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeriesDAO implements IDAO<String>{
    public static SeriesDAO getInstance(){
        return new SeriesDAO();
    }
    @Override
    public int insert(String s) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into series (name) values(?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, s);

            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int update(String s) {
        return 0;
    }

    @Override
    public int delete(String s) {
        return 0;
    }

    @Override
    public ArrayList<String> selectAll() {
        return null;
    }

    @Override
    public String selectById(int id) {
        return "";
    }

    public static void main(String[] args) {
        System.out.println(SeriesDAO.getInstance().insert("S23"));
    }
}

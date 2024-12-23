package DAO;

import model.PublicKey;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PublicKeyDAO implements IDAO<PublicKey> {
    public static PublicKeyDAO getInstance(){
        return new PublicKeyDAO();
    }

    @Override
    public int insert(PublicKey publicKey) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into publicKeys (user_id,content) values(?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, publicKey.userID);
            pst.setString(2, publicKey.content);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(PublicKey publicKey) {
        return 0;
    }

    @Override
    public int delete(PublicKey publicKey) {
        return 0;
    }

    @Override
    public ArrayList<PublicKey> selectAll() {
        return null;
    }

    @Override
    public PublicKey selectById(int id) {
        return null;
    }

    public int disablePublicKey(PublicKey publicKey, String time) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update publicKeys set endAt = ? where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,time);
            pst.setInt(2, publicKey.id);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDateTime selectNewestReportTime(int userID) {
        LocalDateTime re=null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select endAt from publicKeys where user_id = ? and endAt is not null order by id desc limit 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userID);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                re = rs.getObject("endAt", LocalDateTime.class);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

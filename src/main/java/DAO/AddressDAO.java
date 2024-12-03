package DAO;

import model.Address;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressDAO implements IDAO<Address>{
    public static AddressDAO getInstance(){
        return new AddressDAO();
    }
    @Override
    public int insert(Address a) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into addresses (userID,receiver,phone,street,village,district,province) values (?,?,?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,a.getId()); // dung tam id cua address lam id user
            pst.setString(2,a.getReceiver());
            pst.setString(3,a.getPhone());
            pst.setString(4,a.getStreet());
            pst.setString(5,a.getVillage());
            pst.setString(6,a.getDistrict());
            pst.setString(7,a.getProvince());

            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Address a) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update addresses set receiver=?, phone=?," +
                    "street=?," +
                    "village=?," +
                    "district=?," +
                    "province=? " +
                    " where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,a.getReceiver());
            pst.setString(2,a.getPhone());
            pst.setString(3,a.getStreet());
            pst.setString(4,a.getVillage());
            pst.setString(5,a.getDistrict());
            pst.setString(6,a.getProvince());
            pst.setInt(7,a.getId());
            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Address address) {
        int re = 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "delete from addresses where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,address.getId());
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Address> selectAll() {
        return null;
    }

    @Override
    public Address selectById(int idin) {
        Address re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from addresses where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,idin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String receiver = rs.getString("receiver");
                String phone = rs.getString("phone");
                String street = rs.getString("street");
                String village = rs.getString("village");
                String district = rs.getString("district");
                String province = rs.getString("province");
                re = new Address(id, receiver, phone, street, village, district, province);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Address> selectByUserID(int userIDin) {
        ArrayList<Address> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from addresses where userID = ? order by updateTime desc";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String receiver = rs.getString("receiver");
                String phone = rs.getString("phone");
                String street = rs.getString("street");
                String village = rs.getString("village");
                String district = rs.getString("district");
                String province = rs.getString("province");
                res.add(new Address(id,receiver,phone,street,village,district,province));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}

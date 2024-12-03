package DAO;

import com.google.gson.Gson;
import model.Brand;
import model.User;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDAO implements IDAO<Brand>{
    public static BrandDAO getInstance(){
        return new BrandDAO();
    }
    @Override
    public int insert(Brand brand) {
        int re=0;
        try {
//             String jsonData = "{\"name\":\"John\", \"age\":30, \"city\":\"New York\"}";
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into brands (name,country,cateIDs) values(?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, brand.getName());
            pst.setString(2, brand.getCountry());
            pst.setString(3, brand.getCateIDsJSON());

            re = pst.executeUpdate();

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Brand brand) {
        return 0;
    }

    @Override
    public int delete(Brand brand) {
        return 0;
    }

    @Override
    public ArrayList<Brand> selectAll() {
        ArrayList<Brand> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from brands;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                String cateIDs = rs.getString("cateIDs");
                res.add(new Brand(id,name,country,Brand.getCateIDsFromJSON(cateIDs)));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public ArrayList<Brand> selectByCate(int cate) {
//        ArrayList<Brand> res = new ArrayList<>();
//        try {
//            Connection conn = JDBCUtil.getConnection();
//            String sql = "select * from brands WHERE JSON_CONTAINS(cateIDs, ?);;";
//            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setInt(1, cate);
//            ResultSet rs = pst.executeQuery();
//            while(rs.next()){
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String country = rs.getString("country");
//                String cateIDs = rs.getString("cateIDs");
//                res.add(new Brand(id,name,country,Brand.getCateIDsFromJSON(cateIDs)));
//            }
//            JDBCUtil.closeConnection(conn);
//            return res;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public Brand selectById(int id) {
        return null;
    }

    public ArrayList<Brand> selectByCategory(int cateID){
        ArrayList<Brand> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM brands WHERE JSON_CONTAINS(cateIDs, ?, '$') and avai = 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, cateID+"");
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String name = rs.getString("name");
                String country = rs.getString("name");
                String cateIDs = rs.getString("cateIDs");
                res.add(new Brand(id,name,country,Brand.getCateIDsFromJSON(cateIDs)));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ArrayList<Brand> bs = BrandDAO.getInstance().selectByCategory(1);
        System.out.println(bs.size());
    }

}

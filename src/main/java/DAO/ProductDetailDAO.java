package DAO;

import model.CartUnit;
import model.ProductDetail;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDetailDAO implements IDAO<ProductDetail>{
    public static ProductDetailDAO getInstance(){
        return new ProductDetailDAO();
    }

    @Override
    public int insert(ProductDetail detail) { // sua lai
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO `productdsetails` (`productID`, `version`, `color`, `ram`, `rom`, `price`, `qty`, `saleDate`, `hardware`, `des`) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setInt(1, detail.getProduct().getId());
//            pst.setString(2, detail.getVersion());
//            pst.setString(3, detail.getColor());
//            pst.setInt(4,  detail.getRam());
//            pst.setInt(5, detail.getRom());
//            pst.setDouble(6, detail.getPrice());
//            pst.setInt(7, detail.getQty());
////            pst.setDate(8, detail.getSaleDate());
//            pst.setString(9, detail.getHardware());
//            pst.setString(10, detail.getDescription());
//            re = pst.executeUpdate();
//            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(ProductDetail productDetail) {
        return 0;
    }

    @Override
    public int delete(ProductDetail productDetail) {
        return 0;
    }

    @Override
    public ArrayList<ProductDetail> selectAll() {
        return null;
    }

    @Override
    public ProductDetail selectById(int id) {
        return null;
    }

    public ArrayList<ProductDetail> selectByProductID(int productIDin) {
        ArrayList<ProductDetail> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM productdetails where productID = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, productIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productID = rs.getInt("productID");
                String color = rs.getString("color");
                int ram = rs.getInt("ram");
                int rom = rs.getInt("rom");
                double price = rs.getInt("price");
                int qty = rs.getInt("qty");
                res.add(new ProductDetail(id, productID, color, ram, rom, price, qty));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductDetail> selectByVersion(int productIDin, String kind, String version,String other) {
        ArrayList<ProductDetail> res = new ArrayList<>();
        String condition1="";
        String condition2="";
        if(kind.equalsIgnoreCase("color")) {
            condition1 = "color = \""+version+"\"";
            String tokens[] = other.split("==");
            condition2 = "ram = " + tokens[0] + " and rom =" + tokens[1];
        } else {
            String tokens[] = version.split("==");
            if(tokens.length<=1) {
                condition1 = "rom =" + tokens[0];
            } else {
                condition1 = "ram = " + tokens[0] + " and rom =" + tokens[1];

            }
            condition2 = "color = \""+other+"\"";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM productdetails where productID = ? and "+ condition1 + " and "+ condition2 + " and qty > 0;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, productIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productID = rs.getInt("productID");
                String color = rs.getString("color");
                int ram = rs.getInt("ram");
                int rom = rs.getInt("rom");
                double price = rs.getInt("price");
                int qty = rs.getInt("qty");
                res.add(new ProductDetail(id, productID, color, ram, rom, price, qty));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int selectIDByVersion(int productID, String color, int ram, int rom) {
        int re=0;
        String ramCondition="";
        if(ram!=0) {
            ramCondition="and ram = " + ram;
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT id FROM productdetails where productID=? " +
                    "and color = ? " +
                    "and rom = ? " +
                    ramCondition;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,productID);
            pst.setString(2,color);
            pst.setInt(3,rom);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                re = rs.getInt("id");

            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateSaledQty(ArrayList<CartUnit> orderedCarts) {
        int re=0;
        String newValue ="";
        String condition="(";
        for(CartUnit c : orderedCarts) {
            newValue += "WHEN id = "+c.getProductDetailID()+" THEN qty-" +c.getQty()+"\n";
            condition+= c.getProductDetailID()+",";
        }
        condition = condition.substring(0, condition.length()-1);
        condition += ")";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update productdetails set qty = case \n" +
                                                                newValue +
                                                        "END\n" +
                                                "where id in " + condition+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

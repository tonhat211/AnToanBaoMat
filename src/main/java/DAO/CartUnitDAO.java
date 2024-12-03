package DAO;

import model.Cart;
import model.CartUnit;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartUnitDAO implements IDAO<Cart> {
    public static CartUnitDAO getInstance(){
        return new CartUnitDAO();
    }

    @Override
    public int insert(Cart cart) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into carts (userID,productDetailID,qty) values(?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, cart.getUserId());
            pst.setInt(2, cart.getproductDetailID());
            pst.setInt(3, cart.getQty());
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Cart cart) {
        return 0;
    }

    @Override
    public int delete(Cart cart) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "delete from carts where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,cart.getId());
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Cart> selectAll() {
        return null;
    }

    @Override
    public Cart selectById(int id) {
      return null;
    }

    public ArrayList<CartUnit> selectByUserID(int userIDin) {
        ArrayList<CartUnit> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT c.id, d.id as productDetailID, p.name, p.version, p.thumbnail, d.color, d.ram, d.rom, d.price, c.qty\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tjoin carts c on d.id = c.productDetailID\n" +
                    "where c.userID = ?\n" +
                    "group by p.id\n" +
                    "order by c.updateTime desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int deid = rs.getInt("productDetailID");
                String name = rs.getString("name");
                String version = rs.getString("version");
                String thumbnail = rs.getString("thumbnail");
                String color = rs.getString("color");
                int ram = rs.getInt("ram");
                int rom = rs.getInt("rom");
                double price = rs.getDouble("price");
                int qty = rs.getInt("qty");

                res.add(new CartUnit(id,deid,name,version,thumbnail,color,ram,rom,price,qty));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateQty(int id, String operation) {
        int re=0;
        String newQty = "qty-1";
        if(operation.equalsIgnoreCase("plus")){
            newQty= "qty+1";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update carts set qty = "+ newQty +" where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re = pst.executeUpdate();

            if(re==1) {
                String sql2 = "select qty from carts where id = ?";
                PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,id);
                ResultSet rs = pst2.executeQuery();
                while(rs.next()){
                    re = rs.getInt("qty");
                }
            }

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkExistCart(Cart cart) {
        int re = 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from carts where userID = ? and productDetailID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,cart.getUserId());
            pst.setInt(2,cart.getproductDetailID());
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

    public ArrayList<CartUnit> selectByIDs(ArrayList<Integer> ids) {
        ArrayList<CartUnit> res = new ArrayList<>();
        String condition ="(";
        for(Integer id : ids) {
            condition+=id+",";
        }
        condition=condition.substring(0,condition.length()-1);
        condition+=")";

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT c.id, d.id as productDetailID, p.name, p.version, p.thumbnail, d.color, d.ram, d.rom, d.price, c.qty\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tjoin carts c on d.id = c.productDetailID\n" +
                    "where c.id in " + condition +
                    " group by p.id\n" +
                    "order by c.updateTime desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int deid = rs.getInt("productDetailID");
                String name = rs.getString("name");
                String version = rs.getString("version");
                String thumbnail = rs.getString("thumbnail");
                String color = rs.getString("color");
                int ram = rs.getInt("ram");
                int rom = rs.getInt("rom");
                double price = rs.getDouble("price");
                int qty = rs.getInt("qty");

                res.add(new CartUnit(id,deid,name,version,thumbnail,color,ram,rom,price,qty));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public int deleteOrderedCarts(ArrayList<Integer> ids) {
        int re=0;
        String condition ="(";
        for(Integer id : ids) {
            condition+=id+",";
        }
        condition=condition.substring(0,condition.length()-1);
        condition+=")";

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "delete from carts \n" +
                    "where id in " + condition +";";
            PreparedStatement pst = conn.prepareStatement(sql);
            re=pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Cart cart = new Cart(1,12,120);
        System.out.println(cart.getUserId());
        System.out.println(cart.getproductDetailID());
        System.out.println(CartUnitDAO.getInstance().checkExistCart(cart));
    }


}

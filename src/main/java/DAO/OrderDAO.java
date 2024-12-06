package DAO;

import model.*;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderDAO implements IDAO<Order> {
    public static OrderDAO getInstance(){
        return new OrderDAO();
    }

    @Override
    public int insert(Order order) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into orders (money,userID,address) values(?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1, order.getMoney());
            pst.setInt(2, order.getUserID());
            pst.setString(3, order.getAddress());
            re = pst.executeUpdate();

            String sql2 = "select max(id) as id from orders where userID= "+order.getUserID()+";";
            PreparedStatement pst2 = conn.prepareStatement(sql2);
            ResultSet rs = pst2.executeQuery();
            while(rs.next()){
                re = rs.getInt("id");
            }

            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Order order) {
        return 0;
    }

    @Override
    public int delete(Order order) {
        return 0;
    }

    @Override
    public ArrayList<Order> selectAll() {
        return null;
    }


    public int insertOrderDetail(int orderID,ArrayList<CartUnit> carts) {
        int re=0;
        String valueParams = "";
        for(CartUnit c : carts){
            valueParams+="(" + orderID +","+c.getProductDetailID()+","+c.getPrice()+","+c.getQty() +"),";
        }
        valueParams = valueParams.substring(0, valueParams.length()-1);

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into orderDetails (orderID,productDetailID,currentPrice,qty) values "+ valueParams+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Order> selectByUserID(int userIDin) {
        ArrayList<Order> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders where userID= ;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                int userID = rs.getInt("userID");
                String address = rs.getString("address");
                LocalDateTime dateSet = rs.getObject("dateSet",LocalDateTime.class);
                LocalDateTime dateFinish = rs.getObject("dateFinish",LocalDateTime.class);
                int status = rs.getInt("status");
                res.add(new Order(id,money,userID,address,dateSet,dateFinish,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<CartUnit> selectByOrderID(ArrayList<Integer> ids) {
        ArrayList<CartUnit> res = new ArrayList<>();
        String idCondition = "(";
        for(Integer id : ids){
            idCondition+=id+",";
        }
        idCondition=idCondition.substring(0, idCondition.length()-1);
        idCondition+=")";
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, d.id as productDetailID, p.name, p.version, p.thumbnail, d.color, d.ram, d.rom, od.currentPrice, od.qty\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tjoin orderdetails od on d.id = od.productDetailID" +
                    "where od.id in " + idCondition +";\n";
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
                double price = rs.getDouble("currentPrice");
                int qty = rs.getInt("qty");

                res.add(new CartUnit(id,deid,name,version,thumbnail,color,ram,rom,price,qty));
            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int selectStatus(int id) {
        int re=0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select status from orders where id = ? ;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                re = rs.getInt("status");
            }
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> selectOrderByStatus(int statusCondition, int offset, int amount) {
        ArrayList<Order> res = new ArrayList<>();
        String condition = "" ;
        if(statusCondition!=-1) {
            condition= "where status = '" + statusCondition + "'";
        }
        System.out.println(condition);
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " " + condition +
                    "\n   order by updateTime desc \n" +
                    "limit ?,?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, offset);
            pst.setInt(2, amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                int userID = rs.getInt("userID");
                String address = rs.getString("address");
                LocalDateTime dateSet = rs.getObject("dateSet",LocalDateTime.class);
                LocalDateTime updateTime = rs.getObject("updateTime",LocalDateTime.class);
                int status = rs.getInt("status");
                res.add(new Order(id,money,userID,address,dateSet,updateTime,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<OrderDetail> selectDetailByOrders(ArrayList<Order> orders) {
        ArrayList<OrderDetail> res = new ArrayList<>();
        if(orders.size()==0) {return new ArrayList<>();}
        String idList = "(" ;
        for(Order o : orders){
            idList+=o.getId()+",";
        }
        idList=idList.substring(0, idList.length()-1);
        idList+=")";
//        System.out.println(idList);
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select od.orderID as oid, pd.id as pdid, \n" +
                    "p.name, p.version,  \n" +
                    "    pd.color, pd.ram, pd.rom, " +
                    "    od.qty, od.currentPrice, \n" +
                    "   p.thumbnail \n" +
                    "    from orderdetails od join productdetails pd on od.productDetailID = pd.id \n" +
                    "                       join products p on p.id = pd.productID \n" +
                    "   where od.orderID in " + idList +";";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int oid = rs.getInt("oid");
                int pdid = rs.getInt("pdid");
                String name = rs.getString("name");
                String version = rs.getString("version");
                String color = rs.getString("color");
                int ram = rs.getInt("ram");
                int rom = rs.getInt("rom");
                int qty = rs.getInt("qty");
                double currentPrice = rs.getDouble("currentPrice");
                String thumbnail = rs.getString("thumbnail");

                ProductUnit productUnit = new ProductUnit(pdid, name, version, color, String.valueOf(ram), String.valueOf(rom), thumbnail);
                OrderDetail orderDetail = new OrderDetail(oid,productUnit, currentPrice, qty);
                res.add(orderDetail);
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<OrderUnit> selectOrderUnitByStatus(int statusCondition, int offset, int amount) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        Map<Order,OrderUnit> maps = new LinkedHashMap<>();
        ArrayList<Order> orders = selectOrderByStatus(statusCondition, offset, amount);
        ArrayList<OrderDetail> details = selectDetailByOrders(orders);

        for (Order o : orders) {
            maps.put(o, new OrderUnit(o));
        }
        for(OrderDetail d : details) {
            int orderID = d.orderId;
            maps.get(new Order(orderID)).details.add(d);
        }
        for (OrderUnit orderUnit : maps.values()) {
            res.add(orderUnit);
        }

        return res;

    }

    public int selectCountOrderUnitBy(int statusCondition) {
        int re=0;
        String condition = "" ;
        if(statusCondition!=-1) {
            condition= "where status = '" + statusCondition + "'";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select count(*) as count from orders " +
                    " " + condition +";";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                re = rs.getInt("count");
            }
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> selectOrderByTime(int statusCondition, String time, int offset, int amount) {
        ArrayList<Order> res = new ArrayList<>();
        String condition = "" ;
        if(statusCondition!=-1) {
            condition= " status = '" + statusCondition + "' and ";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where " + condition + " (updateTime like ? or dateSet like ?) "+
                    "\n   order by updateTime desc \n" +
                    "limit ?,?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+time+"%");
            pst.setString(2, "%"+time+"%");
            pst.setInt(3, offset);
            pst.setInt(4, amount);
            System.out.println(pst);

            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                int userID = rs.getInt("userID");
                String address = rs.getString("address");
                LocalDateTime dateSet = rs.getObject("dateSet",LocalDateTime.class);
                LocalDateTime updateTime = rs.getObject("updateTime",LocalDateTime.class);
                int status = rs.getInt("status");
                res.add(new Order(id,money,userID,address,dateSet,updateTime,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<OrderUnit> selectOrderUnitByTime(int statusCondition, String time, int offset, int amount) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        Map<Order,OrderUnit> maps = new LinkedHashMap<>();
        ArrayList<Order> orders = selectOrderByTime(statusCondition,time, offset, amount);
        ArrayList<OrderDetail> details = selectDetailByOrders(orders);

        for (Order o : orders) {
            maps.put(o, new OrderUnit(o));
        }
        for(OrderDetail d : details) {
            int orderID = d.orderId;
            maps.get(new Order(orderID)).details.add(d);
        }
        for (OrderUnit orderUnit : maps.values()) {
            res.add(orderUnit);
        }

        return res;

    }
    public ArrayList<Order> selectOrderByID(int statusCondition, String idin, int offset, int amount) {
        ArrayList<Order> res = new ArrayList<>();
        String condition = "" ;
        if(statusCondition!=-1) {
            condition= " status = '" + statusCondition + "' and ";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where " + condition + " id like ?"+
                    "\n   order by updateTime desc \n" +
                    "limit ?,?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+idin+"%");
            pst.setInt(2, offset);
            pst.setInt(3, amount);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                int userID = rs.getInt("userID");
                String address = rs.getString("address");
                LocalDateTime dateSet = rs.getObject("dateSet",LocalDateTime.class);
                LocalDateTime updateTime = rs.getObject("updateTime",LocalDateTime.class);
                int status = rs.getInt("status");
                res.add(new Order(id,money,userID,address,dateSet,updateTime,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<OrderUnit> selectOrderUnitByID(int statusCondition, String id, int offset, int amount) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        Map<Order,OrderUnit> maps = new LinkedHashMap<>();
        ArrayList<Order> orders = selectOrderByID(statusCondition,id, offset, amount);
        ArrayList<OrderDetail> details = selectDetailByOrders(orders);

        for (Order o : orders) {
            maps.put(o, new OrderUnit(o));
        }
        for(OrderDetail d : details) {
            int orderID = d.orderId;
            maps.get(new Order(orderID)).details.add(d);
        }
        for (OrderUnit orderUnit : maps.values()) {
            res.add(orderUnit);
        }

        return res;

    }


    public int updateStatus(int idin, int newStatus) {
        int re= 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update orders set status = ? where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,newStatus);
            pst.setInt(2,idin);
            re = pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<OrderUnit> selectOrderUnitOf(int id) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        Map<Order,OrderUnit> maps = new LinkedHashMap<>();
        ArrayList<Order> orders = selectOrderOf(id);
        ArrayList<OrderDetail> details = selectDetailByOrders(orders);

        for (Order o : orders) {
            maps.put(o, new OrderUnit(o));
        }
        for(OrderDetail d : details) {
            int orderID = d.orderId;
            maps.get(new Order(orderID)).details.add(d);
        }
        for (OrderUnit orderUnit : maps.values()) {
            res.add(orderUnit);
        }

        return res;

    }

    public ArrayList<Order> selectOrderOf(int userIDin) {
        ArrayList<Order> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where userID = ? " +
                    "\n   order by updateTime desc; \n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                int userID = rs.getInt("userID");
                String address = rs.getString("address");
                LocalDateTime dateSet = rs.getObject("dateSet",LocalDateTime.class);
                LocalDateTime updateTime = rs.getObject("updateTime",LocalDateTime.class);
                int status = rs.getInt("status");
                res.add(new Order(id,money,userID,address,dateSet,updateTime,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<OrderUnit> selectOrderUnitOfAndBy(int id, int status) {
        ArrayList<OrderUnit> res = new ArrayList<>();
        Map<Order,OrderUnit> maps = new LinkedHashMap<>();
        ArrayList<Order> orders = selectOrderOfAndBy(id,status);
        ArrayList<OrderDetail> details = selectDetailByOrders(orders);

        for (Order o : orders) {
            maps.put(o, new OrderUnit(o));
        }
        for(OrderDetail d : details) {
            int orderID = d.orderId;
            maps.get(new Order(orderID)).details.add(d);
        }
        for (OrderUnit orderUnit : maps.values()) {
            res.add(orderUnit);
        }

        return res;

    }

    public ArrayList<Order> selectOrderOfAndBy(int userIDin, int statusIn) {
        String statusCondition = " and status = " + statusIn;
        if(statusIn==-1) {
            statusCondition="";
        }
        ArrayList<Order> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where userID = ? " + statusCondition +
                    "\n   order by updateTime desc; \n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userIDin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                int userID = rs.getInt("userID");
                String address = rs.getString("address");
                LocalDateTime dateSet = rs.getObject("dateSet",LocalDateTime.class);
                LocalDateTime updateTime = rs.getObject("updateTime",LocalDateTime.class);
                int status = rs.getInt("status");
                res.add(new Order(id,money,userID,address,dateSet,updateTime,status));
            }
            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public OrderUnit selectByID(int id) {
        ArrayList<Order> orders= new ArrayList<>();
        Order order = OrderDAO.getInstance().selectById(id);
        orders.add(order);
        ArrayList<OrderDetail> details = selectDetailByOrders(orders);

        OrderUnit re = new OrderUnit(order,details);

        return re;

    }

    public Order selectById(int idin) {
        Order re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from orders\n" +
                    " where id = ?; \n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idin);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                int userID = rs.getInt("userID");
                String address = rs.getString("address");
                LocalDateTime dateSet = rs.getObject("dateSet",LocalDateTime.class);
                LocalDateTime updateTime = rs.getObject("updateTime",LocalDateTime.class);
                int status = rs.getInt("status");
                re=new Order(id,money,userID,address,dateSet,updateTime,status);
            }
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public double selectTotalMoneyOfUser(int id) {
        double re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select sum(money) as total from orders where userID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                re = rs.getDouble("total");
            }
            JDBCUtil.closeConnection(conn);
            return re;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        OrderUnit orderUnit = OrderDAO.getInstance().selectByID(30);
        System.out.println(orderUnit.order.getAddress());
    }
}

package DAO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.AdminProductController;
import model.*;
import service.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductUnitDAO implements IDAO<ProductUnit>{
    public static ProductUnitDAO getInstance(){
        return new ProductUnitDAO();
    }
    @Override
    public int insert(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public int update(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public int delete(ProductUnit productUnit) {
        return 0;
    }

    @Override
    public ArrayList<ProductUnit> selectAll() {
        return null;
    }

    @Override
    public ProductUnit selectById(int pid) {
        ProductUnit pu = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "p.avai as avai\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "where p.id=?\n" +
                    "group by p.id\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, pid);
//            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int avai=rs.getInt("avai");
                pu = new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, 0, null,avai);

            }
            JDBCUtil.closeConnection(conn);
            return pu;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectByCategory(int categoryID,int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String condition="";
        if(categoryID!= Constant.ALL) {
            condition = "where p.cateID=?\n";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram,\n" +
                    "p.avai as avai,\n" +
                    "sum(d.qty) as totalQty\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                    condition +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            if(categoryID!= Constant.ALL) {
                pst.setInt(1, categoryID);
                pst.setInt(2, offset);
                pst.setInt(3, amount);
            } else {
                pst.setInt(1, offset);
                pst.setInt(2, amount);
            }
//            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                int avai=rs.getInt("avai");
                int totalQty=rs.getInt("totalQty");

                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram,avai,totalQty));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectByCategoryForAdmin(int categoryID,int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String condition="";
        if(categoryID!= Constant.ALL) {
            condition = "and p.cateID=?\n";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "p.avai as avai,\n" +
                    "sum(d.qty) as totalQty,\n" +
                    "b.id as brandID,\n" +
                    "b.name as brand,\n" +
                    "p.cateID as cateID\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "    left join brands b on p.brandID = b.id\n" +
                    " where p.avai !=" + Constant.DELETE + " " +
                        condition +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?";
            PreparedStatement pst = conn.prepareStatement(sql);
            if(categoryID!= Constant.ALL) {
                pst.setInt(1, categoryID);
                pst.setInt(2, offset);
                pst.setInt(3, amount);
            } else {
                pst.setInt(1, offset);
                pst.setInt(2, amount);
            }
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                int avai=rs.getInt("avai");
                int totalQty=rs.getInt("totalQty");
                int brandID = rs.getInt("brandID");
                String brand=rs.getString("brand");
                Brand b = new Brand(brandID,brand);
                int cateID=rs.getInt("cateID");


                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price,avai,totalQty,b,cateID));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int selectCountByCategory(int categoryID) {
        int re=0;
        String condition="";
        if(categoryID!= Constant.ALL) {
            condition = "where cateID=?\n";
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT count(*) as count from products " + condition+";";
            PreparedStatement pst = conn.prepareStatement(sql);
            if(categoryID!= Constant.ALL) pst.setInt(1, categoryID);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                re = rs.getInt("count");
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<ProductUnit> selectCrossSells(int productID,int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram,\n" +
                    "p.avai as avai\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id and s.main=1\n" +
                    "where p.id in (select otherID from crosssells where productID = ?)\n" +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, productID);
            pst.setInt(2, offset);
            pst.setInt(3, amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                int avai=rs.getInt("avai");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectBySaleProgram(int main,int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram,\n" +
                    "p.avai as avai\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                    "where s.main=?\n" +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, main);
            pst.setInt(2, offset);
            pst.setInt(3, amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                int avai=rs.getInt("avai");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectByBrands(ArrayList<Integer> brandIDs,int sort, int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String condition="";
        if(!brandIDs.contains(0)) {
            condition = " where p.brandID in (";
            for(Integer id : brandIDs) {
                condition+=id+",";
            }
            condition=condition.substring(0, condition.length()-1);
            condition+=")";

        }
        String sortSql="";
        if(sort== Constant.NEW) {
            sortSql="JSON_EXTRACT(p.firstsale, '$.date') DESC";
        } else if(sort== Constant.PRICE_UP) {
            sortSql="min(d.price) asc";
        } else if (sort== Constant.PRICE_DOWN) {
            sortSql="min(d.price) desc";
        } else {
            sortSql="p.prominence desc";
        }

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram,\n" +
                    "p.avai as avai\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                     condition +"\n " +
                    "group by p.id\n" +
                    "order by "+ sortSql +"\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,offset);
            pst.setInt(2,amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                int avai=rs.getInt("avai");
                String saleProgram=rs.getString("saleprogram");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> selectByFilters(ArrayList<String> osList, ArrayList<Integer> priceRange, int sort, int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String osCondition="";
        String osListTemp="";
        if(!osList.isEmpty()) {
            for(String os : osList) {
                osListTemp+=os+"|";
            }
            osListTemp=osListTemp.substring(0, osListTemp.length()-1);
            osCondition = " where JSON_EXTRACT(p.config, '$.os') REGEXP '(?i)"+osListTemp+"'";
        }

        String priceCondition="";
        if(priceRange.size()==2) {
            priceCondition = " and d.price >= "+ priceRange.get(0)+" and d.price< "+priceRange.get(1)+" ";
        }

        String sortSql="";
        if(sort== Constant.NEW) {
            sortSql="JSON_EXTRACT(p.firstsale, '$.date') DESC";
        } else if(sort== Constant.PRICE_UP) {
            sortSql="min(d.price) asc";
        } else if (sort== Constant.PRICE_DOWN) {
            sortSql="min(d.price) desc";
        } else {
            sortSql="p.prominence desc";
        }

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram,\n" +
                    "p.avai as avai\n" +
                    "from products p join productdetails d on p.id = d.productID " +
                                            priceCondition+ "\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                    osCondition + "\n" +
                    "group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,offset);
            pst.setInt(2,amount);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                int avai=rs.getInt("avai");
                String saleProgram=rs.getString("saleprogram");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<ProductUnit> selectBySearch(String searchInput,int offset, int amount) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String[] tokens = searchInput.split(" ");
        String condition=" where ";
        for(String token : tokens) {
            condition+= "p.name like '%"+token+"%' and ";
        }
        condition=condition.substring(0, condition.length()-5);
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "s.id as saleprogramID,\n" +
                    "s.name as saleprogram,\n" +
                    "p.cateID\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "    left join saleprograms s on s.objectID = p.id  and s.main=1\n" +
                    condition +
                    " group by p.id\n" +
                    "order by p.prominence desc\n" +
                    "limit ?,?\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,offset);
            pst.setInt(2,amount);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int saleProgramID=rs.getInt("saleprogramID");
                String saleProgram=rs.getString("saleprogram");
                int cateID=rs.getInt("cateID");

                res.add(new ProductUnit(id, name, version,cateID, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, saleProgramID, saleProgram));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int updateProduct(ProductUnit p) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set name =?," +
                    "   version=?," +
                    "   brandID=?," +
                    "   cateID=?," +
                    "   config=?," +
                    "   firstSale=?," +
                    "   prominence=?, " +
                    "   des=? " +
                    "where id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,p.name);
            pst.setString(2,p.version);
            pst.setInt(3,p.brand.getId());
            pst.setInt(4,p.cateID);
            pst.setString(5,p.config);
            pst.setString(6,p.firstSale);
            pst.setInt(7,p.prominence);
            pst.setString(8,p.des);
            pst.setInt(9,p.productID);
            System.out.println(pst);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateThumbnail(int id, String filePath) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set thumbnail =? " +
                    "where id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,filePath);
            pst.setInt(2,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateImgs(int id, ArrayList<String> filePaths) {
        int re =0;
        StringBuilder temp= new StringBuilder();
        for(String p : filePaths ) {
            temp.append("('"+p+"',"+id+"),");
        }
        String values="";
        if(temp.toString().length()>2) {
            values = temp.toString();
            values=values.substring(0, values.length()-1);
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "delete from images where parentID = ?; \n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            if(!filePaths.isEmpty()) {
                sql="insert into images (url,parentID) values " + values +";";
                pst = conn.prepareStatement(sql);
                re= pst.executeUpdate();
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductUnit selectForUpdateProduct(int idin) {
        ProductUnit re = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from products where id=?;";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,idin);
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                int avai=rs.getInt("avai");
                int brandID = rs.getInt("brandID");
                int cateID = rs.getInt("cateID");
                int prominence = rs.getInt("prominence");
                String des = rs.getString("des");
                Brand b = new Brand(brandID);
                re = new ProductUnit(id, name, version, b, cateID, config, thumbnail, firstSale, prominence,des);
            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int lockProduct(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set avai = "+ Constant.LOCK+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int activeProduct(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set avai = "+ Constant.ACTIVE+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteProduct(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update products set avai = "+ Constant.DELETE+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductDetail> selectDetails(int pid) {
        ArrayList<ProductDetail> res =new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from productdetails where productID = ? and avai != ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, pid);
            pst.setInt(2, Constant.DELETE);
//            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String color = rs.getString("color");
                int ram= rs.getInt("ram");
                int rom= rs.getInt("rom");
                double price=rs.getDouble("price");
                int qty=rs.getInt("qty");
                int avai=rs.getInt("avai");

                res.add(new ProductDetail(id,color,ram,rom,price,qty,avai));


            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateDetail(ProductDetail detail) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update productDetails set color = ?, " +
                    "ram= ?, " +
                    "rom=?, " +
                    "price=?, " +
                    "qty=? " +
                    " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,detail.getColor());
            pst.setInt(2,detail.getRam());
            pst.setInt(3,detail.getRom());
            pst.setDouble(4,detail.getPrice());
            pst.setInt(5,detail.getQty());
            pst.setInt(6,detail.getId());
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductDetail selectDetail(int did) {
        ProductDetail re =null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from productdetails where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, did);
//            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String color = rs.getString("color");
                int ram= rs.getInt("ram");
                int rom= rs.getInt("rom");
                double price=rs.getDouble("price");
                int qty=rs.getInt("qty");
                int avai=rs.getInt("avai");

                re = new ProductDetail(id,color,ram,rom,price,qty,avai);


            }
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int lockDetail(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update productdetails set avai = "+ Constant.LOCK+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int activeDetail(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update productdetails set avai = "+ Constant.ACTIVE+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteDetail(int id) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "update productdetails set avai = "+ Constant.DELETE+ " where id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertProduct(ProductUnit p) {
        int re =0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into products (name, version, brandID, cateID, config, thumbnail, firstSale, des, prominence) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,p.name);
            pst.setString(2,p.version);
            pst.setInt(3,p.brand.getId());
            pst.setInt(4,p.cateID);
            pst.setString(5,p.config);
            pst.setString(6,p.thumbnail);
            pst.setString(7,p.firstSale);
            pst.setString(8,p.des);
            pst.setInt(9,p.prominence);
            System.out.println(pst);
            re= pst.executeUpdate();
            int id=0;
            if(re==1) {
                sql = "select max(id) as id from products;";
                pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    id = rs.getInt("id");
                }
            }
            JDBCUtil.closeConnection(conn);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertDetails(int pid, ArrayList<ProductDetail> details) {
        int re =0;
        StringBuilder temp = new StringBuilder();
        for(ProductDetail detail : details) {
            temp.append("("+pid+",'"+detail.getColor()+"',"+detail.getRam()+","+detail.getRom()+","+detail.getPrice()+","+detail.getQty()+"),\n");
        }
        String values = temp.toString();
        if(temp.length()>3) {
            values = values.substring(0, values.length()-2);
        }
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "insert into productdetails (productID, color, ram, rom, price, qty) values " + values +";";
            PreparedStatement pst = conn.prepareStatement(sql);
            System.out.println(pst);
            re= pst.executeUpdate();
            JDBCUtil.closeConnection(conn);
            return re;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProductUnit> searchForAdmin(String idin) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "p.avai as avai,\n" +
                    "sum(d.qty) as totalQty,\n" +
                    "b.id as brandID,\n" +
                    "b.name as brand,\n" +
                    "p.cateID as cateID\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "    left join brands b on p.brandID = b.id\n" +
                    " where p.avai !=" + Constant.DELETE + " and p.id like ?" +
                    "group by p.id\n" +
                    "order by p.prominence desc;\n";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+idin+"%");
            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                int avai=rs.getInt("avai");
                int totalQty=rs.getInt("totalQty");
                int brandID = rs.getInt("brandID");
                String brand=rs.getString("brand");
                Brand b = new Brand(brandID,brand);
                int cateID=rs.getInt("cateID");


                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price,avai,totalQty,b,cateID));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //    test
public String renderUpdateForm(ProductUnit p,ArrayList<Brand> brands, ArrayList<Image> imgs) {
    String re="";
    re = "                    <form action=\"adminproduct\" method=\"POST\" id=\"updateInfoForm\" enctype=\"multipart/form-data\">\n" +
            "                        <h4 class=\"confirm-content\" style=\"text-align: center\">Cập nhật thông tin sản phẩm</h4>\n" +
            "                        <div class=\"flex-roww\" style=\"justify-content: space-around; margin-top: 10px;\">\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <label for=\"name\">Tên</label>\n" +
            "                                <input type=\"text\" class=\"form-control\" id=\"name\" name=\"name\" value=\""+p.getName()+"\" aria-describedby=\"emailHelp\" placeholder=\"Nhập tên sản phẩm\" required>\n" +
            "                            </div>\n" +
            "                            <div class=\"form-group flex-roww grid-col-4\">\n" +
            "                                <label for=\"id\">ID:</label>\n" +
            "                                <input type=\"text\" class=\"form-control\" id=\"id\" name=\"id\" aria-describedby=\"emailHelp\" placeholder=\"ID\" value=\""+p.getProductID()+"\" readonly>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"form-group grid-col-4\">\n" +
            "                            <label for=\"version\">Phiên bản</label>\n" +
            "                            <input type=\"text\" class=\"form-control\" id=\"version\" name=\"version\" value=\""+p.getVersion()+"\" aria-describedby=\"emailHelp\" placeholder=\"Nhập phiên bản\" required>\n" +
            "                        </div>\n" +
            "                        <div class=\"flex-roww\" style=\"justify-content: space-around; margin-top: 15px;\">\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <label>Thương hiệu</label>\n" +
            getBrandSelect(p.brand.id,brands) +
            "                            </div>\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <label>Phân loại</label>\n" +
            getCateSelect(p.cateID)+
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"flex-roww\" style=\"justify-content: space-around; margin-top: 15px;\">\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <label for=\"saleDate\">Ngày mở bán</label>\n" +
            "                                <input type=\"date\" class=\"form-control\" id=\"saleDate\" name=\"saleDate\" value=\""+p.getSaleDate()+"\" placeholder=\"Nhập ngày mở bán\" required>\n" +
            "                            </div>\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <label for=\"initial-price\">Giá mở bán</label>\n" +
            "                                <input type=\"number\" class=\"form-control\" id=\"initial-price\" name=\"initialPrice\" value=\""+p.getInitialPrice()+"\" placeholder=\"Nhập giá mở bán\" required>\n" +
            "                            </div>\n" +
            "                            <input type=\"text\" name=\"firstSale\"  value=\""+p.firstSale+"\">\n" +
            "                        </div>\n" +
            "                        <div class=\"flex-roww\" style=\"justify-content: space-around; margin-top: 15px;\" >\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <label>Cấu hình</label>\n" +
            "                                <table id=\"configTable\" class=\"table\">\n" +
            "                                    <thead>\n" +
            "                                    <tr>\n" +
            "                                        <th>Tên</th>\n" +
            "                                        <th>Giá trị</th>\n" +
            "                                    </tr>\n" +
            "                                    </thead>\n" +
            "                                    <tbody>\n" +
            getConfigurationTable(p.config)+
            "                                    </tbody>\n" +
            "                                </table>\n" +
            "                                <input type=\"text\" name=\"config\">\n" +
            "                            </div>\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"flex-roww\" style=\"justify-content: space-around; margin-top: 15px;\" >\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <label>Nổi bật</label>\n" +
            "                                <input type=\"text\" name=\"feature\" value=\""+getFeatureValue(p.config)+"\" placeholder=\"Nhập thuộc tính nổi bật\">\n" +
            "                            </div>\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"flex-roww\" style=\"justify-content: space-around; margin-top: 15px;\" >\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <label for=\"prominence\">Độ nổi bật</label>\n" +
            "                                <input type=\"number\" class=\"form-control\" id=\"prominence\" name=\"prominence\" value=\""+p.prominence+"\" placeholder=\"Nhập độ nổi bật\" required>\n" +
            "                            </div>\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                            </div>\n" +
            "\n" +
            "                        </div>\n" +
            "                        <div class=\"flex-roww\" style=\"justify-content: space-around; margin-top: 20px;\" >\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                                <p>Ảnh thumbnail</p>\n" +
            "                                <input id=\"thumbnailInput\" type=\"file\" name=\"thumbnail\" accept=\".jpg, .jpeg, .png\"  onchange=\"previewImage()\" />\n" +
            "                                <div class=\"thumbnail-img-container grid-col-6\" style=\"margin-top: 15px;\">\n" +
            "                                    <img src=\"./assets/img/thumbnail/"    +p.thumbnail+"\" alt=\"\" id=\"thumbnailPreview\" style=\"width:100%\">\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                            <div class=\"form-group grid-col-4\">\n" +
            "                            </div>\n" +

            "\n" +
            "                        </div>\n" +
            "                        <div class=\"flex-roww\" style=\"justify-content: space-around; margin-top: 20px;\" >\n" +
            "                            <div class=\"form-group\">\n" +
            "                                <p>Ảnh chi tiết</p>\n" +
            "                                <!--                new -->\n" +
            "                                <div class=\"upload__box\">\n" +
            "                                    <div class=\"upload__btn-box\">\n" +
            "                                        <label class=\"upload__btn\">\n" +
            "                                            <p>Upload images</p>\n" +
            "                                            <input type=\"file\" multiple data-max_length=\"20\" name=\"imgs\" class=\"upload__inputfile\">\n" +
            "                                        </label>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"upload__img-wrap\">" +
            getImgs(imgs) +
            "                       </div>\n" +
            "                                </div>\n" +
            "\n" +
            "<script>\n" +
            "                                function previewImage() {\n" +
            "                                    var file = document.getElementById('thumbnailInput').files[0];\n" +
            "                                    var reader = new FileReader();\n" +
            "\n" +
            "                                    reader.onload = function(e) {\n" +
            "                                        var imagePreview = document.getElementById('thumbnailPreview');\n" +
            "                                        imagePreview.src = e.target.result;\n" +
            "                                        imagePreview.style.display = 'block';  // Hiển thị ảnh\n" +
            "                                    }\n" +
            "\n" +
            "                                    if (file) {\n" +
            "                                        reader.readAsDataURL(file);\n" +
            "                                    }\n" +
            "                                }\n" +
            "\n" +
            "                            </script>" +
            "                                <script>\n" +
            "                                    uploadImg();\n" +
            "\n" +
            "                                    function uploadImg() {\n" +
            "                                        let imgWrap = \"\";\n" +
            "                                        let imgArray = [];\n" +
            "\n" +
            "                                        // Find all file input elements with the class 'upload__inputfile'\n" +
            "                                        document.querySelectorAll('.upload__inputfile').forEach(function (inputFile) {\n" +
            "                                            inputFile.addEventListener('change', function (e) {\n" +
            "                                                // Get the closest '.upload__box' and find '.upload__img-wrap' inside it\n" +
            "                                                imgWrap = inputFile.closest('.upload__box').querySelector('.upload__img-wrap');\n" +
            "                                                const maxLength = parseInt(inputFile.getAttribute('data-max_length'), 10);\n" +
            "\n" +
            "                                                const files = e.target.files;\n" +
            "                                                const filesArr = Array.from(files);\n" +
            "                                                let iterator = 0;\n" +
            "\n" +
            "                                                filesArr.forEach(function (f, index) {\n" +
            "                                                    if (!f.type.match('image.*')) {\n" +
            "                                                        return;\n" +
            "                                                    }\n" +
            "\n" +
            "                                                    if (imgArray.length > maxLength) {\n" +
            "                                                        return false;\n" +
            "                                                    } else {\n" +
            "                                                        let len = 0;\n" +
            "                                                        for (let i = 0; i < imgArray.length; i++) {\n" +
            "                                                            if (imgArray[i] !== undefined) {\n" +
            "                                                                len++;\n" +
            "                                                            }\n" +
            "                                                        }\n" +
            "                                                        if (len > maxLength) {\n" +
            "                                                            return false;\n" +
            "                                                        } else {\n" +
            "                                                            imgArray.push(f);\n" +
            "\n" +
            "                                                            const reader = new FileReader();\n" +
            "                                                            reader.onload = function (e) {\n" +
            "                                                                const html = `\n" +
            "                                <div class='upload__img-box'>\n" +
            "                                    <div style='background-image: url(${e.target.result})'\n" +
            "                                         data-number='${document.querySelectorAll(\".upload__img-close\").length}'\n" +
            "                                         data-file='${f.name}' class='img-bg'>\n" +
            "                                        <div class='upload__img-close'></div>\n" +
            "                                    </div>\n" +
            "                                </div>`;\n" +
            "                                                                imgWrap.insertAdjacentHTML('beforeend', html);\n" +
            "                                                                initializeImageRemoval();\n" +
            "\n" +
            "                                                                iterator++;\n" +
            "                                                            };\n" +
            "                                                            reader.readAsDataURL(f);\n" +
            "                                                        }\n" +
            "                                                    }\n" +
            "                                                });\n" +
            "                                            });\n" +
            "                                        });\n" +
            "\n" +
            "                                        // Handle the image removal\n" +
            "                                        function initializeImageRemoval() {\n" +
            "                                            // Get all current upload__img-close elements\n" +
            "                                            const closeButtons = document.querySelectorAll('.upload__img-close');\n" +
            "\n" +
            "                                            // Add click event listener to each button directly\n" +
            "                                            closeButtons.forEach(button => {\n" +
            "                                                button.addEventListener('click', function (e) {\n" +
            "                                                    console.log(\"remove img\");\n" +
            "\n" +
            "                                                    // Find the closest element with the class 'img-bg' to the clicked target\n" +
            "                                                    const imgBox = e.target.closest('.img-bg');\n" +
            "\n" +
            "                                                    // Get the 'data-file' attribute from the 'img-bg' element\n" +
            "                                                    const fileName = imgBox.getAttribute('data-file');\n" +
            "\n" +
            "                                                    // Remove the file from the 'imgArray'\n" +
            "                                                    for (let i = 0; i < imgArray.length; i++) {\n" +
            "                                                        if (imgArray[i].name === fileName) {\n" +
            "                                                            imgArray.splice(i, 1);  // Remove the matching file from 'imgArray'\n" +
            "                                                            break;\n" +
            "                                                        }\n" +
            "                                                    }\n" +
            "\n" +
            "                                                    // Remove the image box (the parent div) from the DOM\n" +
            "                                                    imgBox.parentNode.remove();\n" +
            "                                                });\n" +
            "                                            });\n" +
            "                                        }\n" +
            "\n" +
            "// Call this function whenever a new image with a close button is added\n" +
            "                                    }\n" +
            "\n" +
            "                                </script>\n" +
            "\n" +
            "                                <!--                end new-->\n" +
            "                            </div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "                        </div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "                        <p>Mô tả</p>\n" +
            "                        <div class=\"toolbar\">\n" +
            "                            <button onclick=\"document.execCommand('bold')\"><b>B</b></button>\n" +
            "                            <button onclick=\"document.execCommand('italic')\"><i>I</i></button>\n" +
            "                            <button onclick=\"document.execCommand('underline')\"><u>U</u></button>\n" +
            "                        </div>\n" +
            "\n" +
            "                        <textarea contenteditable=\"true\" id=\"editor\" class=\"editor\" name=\"description\" placeholder=\"Bắt đầu soạn thảo văn bản ở đây...\"></textarea>\n" +
            "                        <br>\n" +
            "                        <div class=\"flex-roww\" style=\"margin-top:20px; justify-content: space-around\">\n" +
            "                            <input type=\"text\" name=\"action\" value=\"update\">\n" +
            "                            <button class=\"btn  btn-fourth btn-cancel\" type=\"button\" onclick=\"closeModal(event);\">Hủy</button>\n" +
            "                            <button class=\"btn btn-primary btn-confirm\" onclick=\"\" type=\"submit\">Lưu</button>\n" +
            "                        </div>\n" +
            "                    </form>\n";
    return re;
}


    public String getConfigurationTable(String config) {
        String re="";
        JsonObject jsonObject = JsonParser.parseString(config).getAsJsonObject();
        for (String key : jsonObject.keySet()) {
            JsonElement value = jsonObject.get(key);
            if(value.isJsonArray()) continue;
            re+=    "<tr>\n" +
                    "    <td><input type=\"text\" value=\""+key+"\" onkeydown=\"handleKeyDown(event, 1)\"></td>\n" +
                    "    <td><input type=\"text\" value=\""+value.getAsString()+"\" onkeydown=\"handleKeyDown(event, 2)\"></td>\n" +
                    "</tr>\n";

        }
        return re;
    }

    public String getBrandSelect(int selected,ArrayList<Brand> brands) {
        String re = "";
        re = "<select name=\"brandID\" data-default=\""+selected+"\">\n";
        for(Brand b : brands) {
            re+= "<option value=\""+b.getId()+"\" "+(b.getId()==selected?"selected":"") +">"+b.getName()+"</option>\n";
        }
        re+="</select>\n";
        return re;
    }

    public String getCateSelect(int selected) {
        String re = "";
        re = "<select name=\"cateID\" data-default=\""+selected+"\">\n";
        for(int i=1;i<=3;i++) {
            re+= "<option value=\""+i+"\" "+(i==selected?"selected":"") +">"+Constant.getCategoryName(i)+"</option>\n";
        }
        re+="</select>\n";
        return re;
    }

    public String getFeatureValue(String config) {
        String re="";
        JsonElement jsonElement = JsonParser.parseString(config);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray features = jsonObject.getAsJsonArray("features");
        for(JsonElement f : features) {
            re+="f.getAsString()"+",";
        }
        re= re.substring(0, re.length()-1);
        return re;

    }

    public String getImgs(ArrayList<Image> images) {
        String re="";
        for(Image image : images) {
            re+="<div class=\"upload__img-box\">\n" +
                    "<div style=\"background-image: url("+image.getUrl()+")\"  data-number=\"0\" data-file=\"iphone-11-trang-600x600.jpg\"  class=\"img-bg\">\n" +
                    "<div class=\"upload__img-close\"></div>\n" +
                    "</div>\n" +
                    "</div>";
        }
        return  re;

    }

    public ArrayList<ProductUnit> selectByDetailIds(ArrayList<Integer> ids) {
        ArrayList<ProductUnit> res = new ArrayList<>();
        String idsCondition="(";
        for(Integer i : ids) {
            idsCondition+=i+",";
        }
        idsCondition=idsCondition.substring(0, idsCondition.length()-1);
        idsCondition+=")";

        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT p.id, p.name, p.version, p.config, p.thumbnail, p.firstsale,\n" +
                    "GROUP_CONCAT(DISTINCT d.ram ORDER BY d.ram ASC SEPARATOR '-') AS ram,\n" +
                    "GROUP_CONCAT(DISTINCT d.rom ORDER BY d.rom ASC SEPARATOR '-') AS rom,\n" +
                    "min(d.price) as price,\n" +
                    "count(DISTINCT c.id) as totalComment,\n" +
                    "avg(c.star) as rate,\n" +
                    "p.avai as avai\n" +
                    "from products p join productdetails d on p.id = d.productID\n" +
                    "\tleft join comments c on c.objectID = p.id\n" +
                    "where de.id in " + idsCondition +
                    " group by p.id\n";
            PreparedStatement pst = conn.prepareStatement(sql);
//            System.out.println(pst);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String version= rs.getString("version");
                String config= rs.getString("config");
                String thumbnail=rs.getString("thumbnail");
                String firstSale=rs.getString("firstsale");
                String ram=rs.getString("ram");
                String rom=rs.getString("rom");
                double price=rs.getDouble("price");
                double star=rs.getDouble("rate");
                int totalComment=rs.getInt("totalComment");
                int avai=rs.getInt("avai");
                res.add(new ProductUnit(id, name, version, config ,thumbnail, firstSale, ram, rom, price, star, totalComment, 0, null,avai));

            }
            JDBCUtil.closeConnection(conn);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }







    public static void main(String[] args) {
        ProductUnit p = ProductUnitDAO.getInstance().selectById(52);

    }
}

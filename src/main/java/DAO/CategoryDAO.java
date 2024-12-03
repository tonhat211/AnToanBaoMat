package DAO;

import model.Category;
import model.User;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO implements IDAO<Category> {
    public static CategoryDAO getInstance(){
        return new CategoryDAO();
    }

    @Override
    public int insert(Category category) {
        return 0;
    }

    @Override
    public int update(Category category) {
        return 0;
    }

    @Override
    public int delete(Category category) {
        return 0;
    }

    @Override
    public ArrayList<Category> selectAll() {
        ArrayList<Category> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select * from categories;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int avai = rs.getInt("avai");

                res.add(new Category(id,name,avai));
            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category selectById(int id) {
        return null;
    }
}

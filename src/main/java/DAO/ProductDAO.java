package DAO;

import model.Product;
import model.ProductDetail;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO implements IDAO<Product> {
    public static ProductDAO getInstance(){
        return new ProductDAO();
    }
    @Override
    public int insert(Product product) {
        return 0;
    }

    @Override
    public int update(Product product) {
        return 0;
    }

    @Override
    public int delete(Product product) {
        return 0;
    }

    @Override
    public ArrayList<Product> selectAll() {
        return null;
    }

    @Override
    public Product selectById(int id) {
        return null;
    }

}

package DAO;

import model.CrossSell;
import model.User;
import service.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CrossSellDAO implements IDAO<CrossSell> {
    public static CrossSellDAO getInstance(){
        return new CrossSellDAO();
    }
    @Override
    public int insert(CrossSell crossSell) {
        return 0;
    }

    @Override
    public int update(CrossSell crossSell) {
        return 0;
    }

    @Override
    public int delete(CrossSell crossSell) {
        return 0;
    }

    @Override
    public ArrayList<CrossSell> selectAll() {
        return null;
    }

    @Override
    public CrossSell selectById(int id) {
        return null;
    }

}

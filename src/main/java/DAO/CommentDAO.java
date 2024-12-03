package DAO;

import model.Comment;
import model.Constant;
import model.User;
import service.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class CommentDAO implements IDAO<Comment> {
    public static CommentDAO getInstance(){
        return new CommentDAO();
    }

    @Override
    public int insert(Comment comment) {
        return 0;
    }

    @Override
    public int update(Comment comment) {
        return 0;
    }

    @Override
    public int delete(Comment comment) {
        return 0;
    }

    @Override
    public ArrayList<Comment> selectAll() {
        return null;
    }

    @Override
    public Comment selectById(int id) {
        return null;
    }

    public ArrayList<Comment> selectObjectID(int oID) {
        ArrayList<Comment> res = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "select c.id as cmtID, c.content,c.star,u.id as userID, u.name as userName, c.time " +
                    "from comments c join users u on c.userID = u.id " +
                    "where c.objectID = ? and c.avai = "+ Constant.ACTIVE+ " " +
                    "order by c.id desc;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, oID);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("cmtID");
                String content = rs.getString("content");
                int star = rs.getInt("star");
                int userID = rs.getInt("userID");
                String userName = rs.getString("userName");
                Timestamp time = rs.getTimestamp("time");

                res.add(new Comment(id,content,star,new User(userID,userName),time));
            }

            JDBCUtil.closeConnection(conn);
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Comment {
    private int id;
    private ProductDetail productDetail;
    private String content;
    private int star;
    private User user;
    private Timestamp timestamp;
    private int avai;

    public Comment() {
    }

    public Comment(int id, String content, int star, User user, Timestamp timestamp) {
        this.id = id;
        this.content = content;
        this.star = star;
        this.user = user;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }

    public String getCommentItem() {
        String re="";
        re="<div class=\"comment-item\" id=\"cmt"+this.getId()+"\">\n" +
                "                            <div class=\"comment-header flex-roww\">\n" +
                "                                <p class=\"comment-owner\">"+this.user.getName()+"</p>\n" +
                "                                <p class=\"buy-check\"><i class=\"bi bi-check-circle\"></i> Đã mua tại thietbididong</p>\n" +
                "                            </div>\n" +
                "                            <div class=\"rating\">\n" +
                "                                <span class=\"star\" data-value=\"1\"><i class=\"bi bi-star\"></i></span>\n" +
                "                                <span class=\"star\" data-value=\"2\"><i class=\"bi bi-star\"></i></span>\n" +
                "                                <span class=\"star\" data-value=\"3\"><i class=\"bi bi-star\"></i></span>\n" +
                "                                <span class=\"star\" data-value=\"4\"><i class=\"bi bi-star\"></i></span>\n" +
                "                                <span class=\"star\" data-value=\"5\"><i class=\"bi bi-star\"></i></span>\n" +
                "                            </div>\n" +
                "                            <script>\n" +
                "                                initRating('#cmt"+this.getId()+"',"+this.getStar()+");\n" +
                "                            </script>\n" +
                "                            <div class=\"comment-content\">\n" +
                "                                <p>"+this.getContent()+"</p>\n" +
                "                            </div>\n" +
                "                            <div class=\"seperate-horizontal-90\" style=\"margin: 20px 0;\"></div>\n" +
                "                        </div>";
        return re;
    }

    public static String getCommentItems(ArrayList<Comment> comments) {
        String re="";
        for(Comment c:comments){
            re+=c.getCommentItem();
        }
        return re;
    }

    public static double getAvgRate(ArrayList<Comment> comments) {
        double re=0;
        if(comments.size()==0) return 0;
        for(Comment c:comments){
            re+=c.getStar();
        }
        re= re/comments.size();
        return re;
    }

    public static int getTotalComments(ArrayList<Comment> comments) {
        return comments.size();
    }
}

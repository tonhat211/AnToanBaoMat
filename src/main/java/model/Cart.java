package model;

public class Cart {
    private int id;
    private int userId;
    private int productDetailID;
    private int qty;

    public Cart() {
    }

    public Cart(int id) {
        this.id = id;
    }

    public Cart(int id, int userId, int productDetailID, int qty) {
        this.id = id;
        this.userId = userId;
        this.productDetailID = productDetailID;
        this.qty = qty;
    }

    public Cart(int userId, int productDetailID, int qty) {
        this.userId = userId;
        this.productDetailID = productDetailID;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getproductDetailID() {
        return productDetailID;
    }

    public void setproductDetailID(int productDetailID) {
        this.productDetailID = productDetailID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

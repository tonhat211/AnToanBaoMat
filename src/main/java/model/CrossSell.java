package model;

public class CrossSell {
    private int id;
    private int productID;
    private String version;
    private String products;

    public CrossSell(int id, int productID, String version, String products) {
        this.id = id;
        this.productID = productID;
        this.version = version;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}



package model;

public class CartUnit {
    private int id;
    private int productDetailID;
    private String name;
    private String version;
    private String thumbnail;
    private String color;
    private int ram;
    private int rom;
    private double price;
    private int qty;

    public CartUnit() {
    }

    public CartUnit(int id, int productDetailID, String name, String version, String thumbnail, String color, int ram, int rom, double price, int qty) {
        this.id = id;
        this.productDetailID = productDetailID;
        this.name = name;
        this.version = version;
        this.thumbnail = thumbnail;
        this.color = color;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductDetailID() {
        return productDetailID;
    }

    public void setProductDetailID(int productDetailID) {
        this.productDetailID = productDetailID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getFullName() {
        return this.name + " " + this.version;
    }

    public String getClassification() {
        return this.version + " - " + this.color + " - " + this.ram + " - " + this.rom;
    }

    public String getCurrentPrice() {
        return ProductUnit.formatPrice(this.getPrice());
    }

    public String getTotalMoney() {
        return ProductUnit.formatPrice(this.getPrice()*this.getQty());
    }
}

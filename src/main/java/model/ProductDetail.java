package model;

import java.sql.Date;
import java.util.List;

public class ProductDetail {
    private int id;
    private int productID;
    private String color;
    private int ram;
    private int rom;
    private double price;
    private int qty;
    public int avai;


    public ProductDetail() {
    }

    public ProductDetail(int id, int productID, String color, int ram, int rom, double price, int qty) {
        this.id = id;
        this.productID = productID;
        this.color = color;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.qty = qty;
    }

//admin
    public ProductDetail(int id, String color, int ram, int rom, double price, int qty,int avai) {
        this.id = id;
        this.color = color;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.qty = qty;
        this.avai = avai;
    }

    // admin add product
    public ProductDetail(String color, int ram, int rom, double price, int qty) {
        this.color = color;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.qty = qty;

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isActive() {
        if(this.avai==1) return true;
        else return false;
    }
}

package model;

import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private String series;
    private Brand brand;
    private Category category;
    private double star;
    private int prominence;
    private String thumbnail;
    private int avai;

    public int brandID;
    public int cateID;
    public String firstSale;


    public Product() {
    }

    public Product(int id, String name, String series, Brand brand, Category category, double star, int prominence, String thumbnail, int avai) {
        this.id = id;
        this.name = name;
        this.series = series;
        this.brand = brand;
        this.category = category;
        this.star = star;
        this.prominence = prominence;
        this.thumbnail = thumbnail;
        this.avai = avai;
    }

    //cho select by category -- truy van mac dinh, khong filter
    public Product(int id, String name, double star, String thumbnail) {
        this.id = id;
        this.name = name;
        this.star = star;
        this.thumbnail = thumbnail;
    }



    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getProminence() {
        return prominence;
    }

    public void setProminence(int prominence) {
        this.prominence = prominence;
    }

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }
}

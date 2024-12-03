package model;

import com.google.gson.Gson;

public class Brand {
    public int id;
    public String name;
    public String country;
    public int[] cateIDs;
    public int avai;

    public Brand() {
    }

    public Brand(int id, String name, String country, int[] cateIDs) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.cateIDs = cateIDs;
//        this.avai = avai;
    }

    public Brand(String name, String country, int[] cateIDs) {
        this.name = name;
        this.country = country;
        this.cateIDs = cateIDs;
    }

    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Brand(int id) {
        this.id = id;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int[] getcateIDs() {
        return cateIDs;
    }

    public void setcateIDs(int[] cateIDs) {
        this.cateIDs = cateIDs;
    }

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }
    
    public String getCateIDsJSON() {
        Gson gson = new Gson();
        return gson.toJson(cateIDs);
    }

    public static int[] getCateIDsFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, int[].class);
    }

    public static void main(String[] args) {
        int[] t = {1,2,3,4,66,99};
        Gson gson = new Gson();
        String s = gson.toJson(t);
        System.out.println(s);
        int[] a = gson.fromJson(s, int[].class);
        System.out.println(a.length);

    }
}

package model;

import java.util.ArrayList;

public class Address {
    private int id;
    private String receiver;
    private String phone;
    private String street;
    private String village;
    private String district;
    private String province;

    public Address() {
    }

    public Address(int id) {
        this.id = id;
    }

    public Address(int id, String receiver, String phone, String street, String village, String district, String province) {
        this.id = id;
        this.receiver = receiver;
        this.phone = phone;
        this.street = street;
        this.village = village;
        this.district = district;
        this.province = province;
    }


//    constructor cho nhung dia chi ma khong phai dia chi nhan hang (dia chi chi nhanh,...)
    public Address(int id, String street, String village, String district, String province) {
        this.id = id;
        this.street = street;
        this.village = village;
        this.district = district;
        this.province = province;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return this.street + ", " + this.village + ", " + this.district + ", " + this.province;
    }

}

package model;

public class Branch {
    private int id;
    private String name;
    private Address address;
    private String phone;
    private int avai;

    public Branch() {
    }

    public Branch(int id, String name, Address address, String phone, int avai) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.avai = avai;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }
}

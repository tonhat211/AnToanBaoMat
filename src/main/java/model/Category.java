package model;

public class Category {
    private int id;
    private String name;
    private int avai;
    public Category() {}

    public Category(int id, String name, int avai) {
        this.id = id;
        this.name = name;
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

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }
}

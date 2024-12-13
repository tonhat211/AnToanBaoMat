package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PartialOrder {
    public int id;
    public double totalMoney;
    public String receiver;
    public LocalDateTime dateSet;
    ArrayList<CartUnit> products;

    public PartialOrder(int id, double totalMoney, String receiver, LocalDateTime dateSet, ArrayList<CartUnit> products) {
        this.id = id;
        this.totalMoney = totalMoney;
        this.receiver = receiver;
        this.dateSet = dateSet;
        this.products = products;
    }
}

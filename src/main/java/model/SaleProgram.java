package model;

import DAO.ProductUnitDAO;
import DAO.SaleProgramDAO;
import com.google.gson.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SaleProgram {
    private int id;
    private int objectID;
    private String name;
    private int main;
    private String content;
    private double discount;
    private Date time;
    private LocalDateTime endTime;
    private int avai;

    public SaleProgram() {

    }

    public SaleProgram(int id, String name, double discount) {
        this.id = id;
        this.name = name;
        this.discount = discount;
    }

    public SaleProgram(int id, int objectID, String name, int main, String content, double discount, Date time, LocalDateTime endTime, int avai) {
        this.id = id;
        this.objectID = objectID;
        this.name = name;
        this.main = main;
        this.content = content;
        this.discount = discount;
        this.time = time;
        this.endTime = endTime;
        this.avai = avai;
    }

    public SaleProgram(int id) {
        this.id = id;
        this.name = "";
        this.content = "[]";
        this.endTime = LocalDateTime.parse("2003-10-23T14:30:00");
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndTimeString() {
        LocalDateTime d = this.endTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = formatter.format(d);
        return formattedDate;
    }


    public String[] getContentItems() {
        Gson gson = new Gson();
        String[] stringArray = gson.fromJson(this.content, String[].class);
        return stringArray;

    }

    public static LocalTime getRemainingTime() {
        System.out.println("getting end online sale time");
        LocalDateTime endTime = SaleProgramDAO.getInstance().getEndTimeOnline();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, endTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        LocalTime remainingTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);
        return remainingTime;
    }

    @Override
    public String toString() {
        return "SaleProgram{" +
                "id=" + id +
                ", objectID=" + objectID +
                ", name='" + name + '\'' +
                ", main=" + main +
                ", content='" + content + '\'' +
                ", discount=" + discount +
                ", time=" + time +
                ", endTime=" + endTime +
                ", avai=" + avai +
                '}';
    }

    public static void main(String[] args) {

    }

}

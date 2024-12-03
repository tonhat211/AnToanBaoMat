package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserInfo {
    public LocalDate dateIn;
    public String phone;
    public String gender;
    public LocalDate birthday;
    public String position;
    public String area;

    public UserInfo() {
    }

    public UserInfo(LocalDate dateIn, String phone, String gender, LocalDate birthday) {
        this.dateIn = dateIn;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDateInAsString() {
        return dateIn != null ? dateIn.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

    public String getBirthdayAsString() {
        return birthday != null ? birthday.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "dateIn=" + dateIn +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", position='" + position + '\'' +
                ", area='" + area + '\'' +
                '}';
    }

    public String toJson() {
        String re = "";
        re = "{\"dateIn\":\""+this.getDateInAsString()+"\",\"phone\":\""+this.getPhone()+"\",\"gender\":\""+this.getGender()+"\"," +
                "\"birthday\":\""+this.getBirthdayAsString()+"\",\"position\":\""+this.getPosition()+"\",\"area\":\""+this.getArea()+"\"}";
        return re;
    }
}


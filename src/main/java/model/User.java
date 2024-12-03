package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class User {
    public int id;
    public String name;
    public String email;
    public String password;
    public String[] roles;
    public Branch branch;
    public String info;
    public Image image;
    public int avai;

    public User() {
    }

    public User(int id, String name, String email, String password, String[] roles, Branch branch, String info, Image image, int avai) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.branch = branch;
        this.info = info;
        this.image = image;
        this.avai = avai;
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, String email, String info) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.info = info;
    }

    public User(String name, String email, String password,String info) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.info = info;
    }

    public User(int id,String name, String email, String[] roles, String info) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.info = info;
    }

    // dung trong image
    public User(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }

    public static String encodePwd(String pwd) {
        String code ="";
        for(int i=pwd.length() - 1 ; i>=0;i--){
            code+= (int) pwd.charAt(i);
        }

        return code;
    }

    public UserInfo getUserInfo() {
        // Tạo Gson với TypeAdapter tùy chỉnh
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        UserInfo info = gson.fromJson(this.info, UserInfo.class);
        return info;
    }

    public String getDateIn() {
        LocalDate day = getUserInfo().getDateIn();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = day.format(formatter);
        return formattedDate;
    }

    public String getPhone() {
        return getUserInfo().getPhone();
    }

    public String getBirthday() {
        LocalDate day = getUserInfo().getBirthday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = day.format(formatter);
        return formattedDate;
    }

    public boolean isLocked() {
        if(this.avai==Constant.LOCK) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", branch=" + branch +
                ", info='" + info + '\'' +
                ", image=" + image +
                ", avai=" + avai +
                '}';
    }

    public static String hashPassword(String password) {
        HashAlgorism hashAlgorism = new HashAlgorism();
        try {
            return hashAlgorism.hash(password);
        } catch (Exception e) {
            return "null";
        }
    }

    public boolean hasRole(String role) {
        if(this.roles==null) return false;
        String userRole = Arrays.toString(this.roles);
        userRole = userRole.toUpperCase();
        if(userRole.contains(role)) return true;
        else return false;
    }

    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();

        // Chuyển đổi sang LocalDate để chỉ lấy ngày
        LocalDate today = now.toLocalDate();
        System.out.println("Today: " +today.toString());

        // Định dạng ngày
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = today.format(formatter);
        System.out.println("Formatted Date: " + formattedDate);

        System.out.println("hash password: " + hashPassword(formattedDate));
    }


}

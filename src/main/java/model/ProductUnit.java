package model;

import DAO.ProductDetailDAO;
import DAO.ProductUnitDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.*;

public class ProductUnit {
    public int productID;
    public String name;
    public String version;
    public int cateID;
    public String config;
    public String thumbnail;
    public String firstSale;
    public String ram;
    public String rom;
    public double price;
    public double star;
    public int totalComment;
    public int saleProgramID;
    public String saleProgram;
    public String color;
    public int productDetailId;
    public int avai;
    public int totalQty;
    public Brand brand;
    public int prominence;
    public String des;
    public ArrayList<ProductDetail> details;

    public ProductUnit() {

    }


    // update admin
    public ProductUnit(int productID, String name, String version, Brand brand, int cateID, String config, String thumbnail, String firstSale, int prominence,String des) {
        this.productID = productID;
        this.name = name;
        this.version = version;
        this.brand = brand;
        this.cateID = cateID;
        this.config = config;
        this.thumbnail = thumbnail;
        this.firstSale = firstSale;
        this.prominence = prominence;
        this.des = des;
    }

    // base


    public ProductUnit(int productID, String name, String version, String config, String thumbnail, String firstSale, String ram, String rom, double price, double star, int totalComment, int saleProgramID, String saleProgram,int avai) {
        this.productID = productID;
        this.name = name;
        this.version = version;
        this.config = config;
        this.thumbnail = thumbnail;
        this.firstSale = firstSale;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.star = star;
        this.totalComment = totalComment;
        this.saleProgramID = saleProgramID;
        this.saleProgram = saleProgram;
        this.avai = avai;
    }

    public ProductUnit(int productID, String name, String version, String config, String thumbnail, String firstSale, String ram, String rom, double price, double star, int totalComment, int saleProgramID, String saleProgram,int avai,int totalQty) {
        this.productID = productID;
        this.name = name;
        this.version = version;
        this.config = config;
        this.thumbnail = thumbnail;
        this.firstSale = firstSale;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.star = star;
        this.totalComment = totalComment;
        this.saleProgramID = saleProgramID;
        this.saleProgram = saleProgram;
        this.avai = avai;
        this.totalQty  = totalQty;

    }

    public ProductUnit(int productID, String name, String version, String config, String thumbnail, String firstSale, String ram, String rom, double price,int avai,int totalQty,Brand brand,int cateID) {
        this.productID = productID;
        this.name = name;
        this.version = version;
        this.config = config;
        this.thumbnail = thumbnail;
        this.firstSale = firstSale;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.avai = avai;
        this.totalQty  = totalQty;
        this.brand = brand;
        this.cateID=cateID;
    }

    //insert product
    public ProductUnit(String name, String version, Brand brand, int cateID, String config, String thumbnail, String firstSale, String des, int prominence) {
        this.name = name;
        this.version = version;
        this.brand = brand;
        this.cateID = cateID;
        this.config = config;
        this.thumbnail = thumbnail;
        this.firstSale = firstSale;
        this.des = des;
        this.prominence = prominence;
    }


    // trong order


    public ProductUnit(int productDetailId, String name, String version, String color, String ram, String rom, String thumbnail) {
        this.productDetailId = productDetailId;
        this.name = name;
        this.version = version;
        this.ram = ram;
        this.rom = rom;
        this.color = color;
        this.thumbnail = thumbnail;
    }

    public ProductUnit(int productID, String name, String version, int cateID, String config, String thumbnail,
                       String firstSale, String ram, String rom, double price, double star, int totalComment, int saleProgramID,
                       String saleProgram) {
		super();
		this.productID = productID;
		this.name = name;
		this.version = version;
		this.cateID = cateID;
		this.config = config;
		this.thumbnail = thumbnail;
		this.firstSale = firstSale;
		this.ram = ram;
		this.rom = rom;
		this.price = price;
		this.star = star;
		this.totalComment = totalComment;
		this.saleProgramID = saleProgramID;
		this.saleProgram = saleProgram;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getsaleProgramID() {
        return saleProgramID;
    }

    public void setsaleProgramID(int saleProgramID) {
        this.saleProgramID = saleProgramID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public String getSaleProgram() {
        return saleProgram;
    }

    public void setSaleProgram(String saleProgram) {
        this.saleProgram = saleProgram;
    }

    public ArrayList<ProductDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<ProductDetail> details) {
        this.details = details;
    }

    public Date getSaleDate() {
        JsonObject jsonObject = JsonParser.parseString(this.firstSale).getAsJsonObject();
        String date = jsonObject.get("date").getAsString();
        return Date.valueOf(date);
    }

    public String getStatusItem() {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        Date saleDate = this.getSaleDate();
        if (saleDate.compareTo(today) > 0) {
            return "<p class=\"status-item status-soon\">Sắp ra mắt</p>";
        } else {
            long differenceInMillis = today.getTime() - saleDate.getTime();
            long differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);
            if((differenceInDays<60))
                return "<p class=\"status-item status-new\">Mẫu mới</p>";
            else {
                return "";
            }
        }
    }

    public String getSaleProgramItem() {
        String re = this.saleProgram;
        return re==null?"":"<i class=\"bi bi-gift\"></i> "+re;

    }

    public String getFullName() {
        return this.name + " " + this.version;
    }

    public String getFeatureItems() {
        String res="";
        JsonElement jsonElement = JsonParser.parseString(this.config);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray features = jsonObject.getAsJsonArray("features");
        for(JsonElement f : features) {
            res+="<p class=\"feature-item\">"+f.getAsString()+"</p>";
        }
        return res;
    }

    public String getCategoryName() {
        return Constant.getCategoryName(this.cateID);
    }

    public String getStorageVersionItems() {
        String re="";
        ArrayList<String> versions = new ArrayList<>();
        String[] rams = this.ram.split("-");
        String[] roms = this.rom.split("-");
        boolean sameRam = false;
        for(int i=0;i<rams.length-1;i++) {
            if (rams[i].equals(rams[i+1])) {
                sameRam=true;
                break;
            }
        }
        if((!sameRam) && rams.length>2 && roms.length>2 ) { // them thong tin ram vao version
            for(int i=0;i<rams.length;i++) {
                for(int j=0;j<roms.length;j++) {
                    versions.add(rams[i] +"-"+ roms[j]+"GB");
                }
            }
        } else {
            for(int j=0;j<roms.length;j++) {
                versions.add(roms[j]+"GB");
            }
        }
        if(versions.isEmpty()) {
            versions.add("1 phiên bản");
        }

        re = "   <div class=\"version-item active\" onclick=\"getPriceOf(event);\">\n" +
             "        <p>"+ versions.get(0) +"</p>\n" +
             "   </div>";

        for(int i=1;i<versions.size();i++) {
            re+=    "    <div class=\"version-item\" onclick=\"getPriceOf(event);\">\n" +
                    "         <p>"+ versions.get(i) +"</p>\n" +
                    "    </div>";
        }
        return re;
    }

    public static String formatPrice(double price) {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String priceString = numberFormat.format(price);
        return priceString;
    }

    public String getCurrentPrice() {
        return formatPrice(this.getPrice());
    }

    public double getRate() {
        double rate = this.star;
        int rateInt = (int) rate;
        double difference = rate - rateInt;
        if(difference>=0 && difference<0.25) {
            return rateInt;
        }
        if(difference>=0.25 && difference<0.75){
            return (double)rateInt + 0.5;
        }
        return rateInt +1;
    }

    public String getInitialPrice() {
//        System.out.println("first sale: " +this.firstSale);
        JsonObject jsonObject = JsonParser.parseString(this.firstSale).getAsJsonObject();
        double initialPrice = jsonObject.get("initial-price").getAsDouble();
        return formatPrice(initialPrice);

    }


    public String getUrlToDetail() {
        return "product?action=detail&id=" + this.productID;
    }

    public static String getCarouselItems(ArrayList<Image> images) {
        String re="";
        if(images==null || images.isEmpty()) {
            images=new ArrayList<>();
            images.add(new Image());
        }
        re+=    "   <div class=\"carousel-item active\">\n" +
                "       <img class=\"d-block w-100\" src=\"./assets/img/product/"+images.get(0).getUrl()+"\" alt=\""+images.get(0).getTitle()+"\">\n" +
                "   </div>";
        for(int i=1;i<images.size();i++) {
            re+=    "   <div class=\"carousel-item\">\n" +
                    "       <img class=\"d-block w-100\" src=\"./assets/img/product/"+images.get(i).getUrl()+"\" alt=\""+images.get(i).getTitle()+"\">\n" +
                    "   </div>";
        }
        return re;
    }

    public static String getCarouselIndicators(ArrayList<Image> images) {
        String re="";
        if(images==null || images.isEmpty()) {
            images=new ArrayList<>();
            images.add(new Image());
        }
        re+=    "   <li data-target=\"#carouselExampleIndicators\" data-slide-to=\""+0+"\" class=\"grid-col-1 active\">\n" +
                "       <img class=\"d-block w-100\" src=\"./assets/img/product/"+images.get(0).getUrl()+"\" alt=\"slide "+1+"\">\n" +
                "   </li>";
        for(int i=1;i<images.size();i++) {
            re+=    "   <li data-target=\"#carouselExampleIndicators\" data-slide-to=\""+i+"\" class=\"grid-col-1\">\n" +
                    "       <img class=\"d-block w-100\" src=\"./assets/img/product/"+images.get(i).getUrl()+"\" alt=\"slide "+(i+1)+"\">\n" +
                    "   </li>";
        }
        return re;
    }

    public String getConfiguration() {
        String re="";
//        {"screen":"oled", "display":"6.1 inch",
//                "front-camera":"12mpx", "main-camera":"200mpx",
//                "os": "one ui 6.1", "chip": "snapdragon 8 gen 2 for galaxy",
//                "battery":"3000mah", "charge":"20w", "features": ["oled","200mpx"]}
        JsonObject jsonObject = JsonParser.parseString(this.config).getAsJsonObject();
        re+=" <div class=\"grid__row p-des-info\">\n" +
            "     <p class=\"grid-col-5 des-label\">"+getConfigHelper("ram")+":</p>\n" +
            "     <p class=\"grid-col-5 des-info\">"+this.ram+" (GB)</p>\n" +
            "     <div class=\"seperate-horizontal-90\"></div>\n" +
            " </div>";
        re+=" <div class=\"grid__row p-des-info\">\n" +
            "     <p class=\"grid-col-5 des-label\">"+getConfigHelper("rom")+":</p>\n" +
            "     <p class=\"grid-col-5 des-info\">"+this.rom+" (GB)</p>\n" +
            "     <div class=\"seperate-horizontal-90\"></div>\n" +
            " </div>";
        for (String key : jsonObject.keySet()) {
            JsonElement value = jsonObject.get(key);
            if(value.isJsonArray()) continue;
            re+=" <div class=\"grid__row p-des-info\">\n" +
                "     <p class=\"grid-col-5 des-label\">"+getConfigHelper(key)+":</p>\n" +
                "     <p class=\"grid-col-5 des-info\">"+value.getAsString()+"</p>\n" +
                "     <div class=\"seperate-horizontal-100\"></div>\n" +
                " </div>";
        }
       return re;
    }

    public static String getConfigHelper(String input) {
        String re=input;
        switch (input)  {
            case "screen": {
                re= "Màn hình";
                break;
            }
            case "display": {
                re= "Hiển thị";
                break;
            }
            case "front-camera": {
                re= "Camera trước";
                break;
            }
            case "main-camera": {
                re= "Camera chính";
                break;
            }
            case "os": {
                re= "Hệ điều hành";
                break;
            }
            case "chip": {
                re= "Chip xử lý (CPU)";
                break;
            }
            case "battery": {
                re= "Pin (mAh)";
                break;
            }
            case "charge": {
                re= "Sạc (W)";
                break;
            }
            case "ram": {
                re= "RAM (GB)";
                break;
            }
            case "rom": {
                re= "ROM (GB)";
                break;
            }
        }
        return re;
    }

    public ArrayList<String> getStorageItems() {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<ProductDetail> details = this.details;
//        ArrayList<ProductDetail> details = ProductDetailDAO.getInstance().selectByProductID(52);
        ArrayList<Integer> rams = new ArrayList<>();
        ArrayList<Integer> roms = new ArrayList<>();

        for(ProductDetail detail : details) {
            if(!rams.contains(detail.getRam())) {
                rams.add(detail.getRam());
            }
            if(!roms.contains(detail.getRom())) {
                roms.add(detail.getRom());
            }
        }
        if(rams.size()<=1) {
            for(Integer rom : roms) {
                res.add(rom+ "GB");
            }
        } else {
            for(ProductDetail detail : details) {
                String s = detail.getRam() + "GB - " + detail.getRom()+"GB";
                if(!res.contains(s)) {
                    res.add(s);
                }
            }
        }
        return res;
    }

    public ArrayList<String> getColorItems() {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<ProductDetail> details = this.details;
        for(ProductDetail detail : details) {
            if(!res.contains(detail.getColor())) {
                res.add(detail.getColor());
            }
        }
        return res;
    }


    public ArrayList<String> getExhaustedItems() {
        ArrayList<ProductDetail> details = this.details;
//        ArrayList<ProductDetail> details = ProductDetailDAO.getInstance().selectByProductID(52);
        ArrayList<String> res = new ArrayList<>();
        String totalDetail="";
        Map<String,Integer> detailExhausteds = new HashMap<>();
        for(ProductDetail detail : details) {
            totalDetail+=detail.getColor()+"=="+detail.getRam()+"=="+detail.getRom()+"==";
            if(detail.getQty()==0) {
                if(detailExhausteds.containsKey(detail.getColor())) {
                    int count = detailExhausteds.get(detail.getColor());
                    detailExhausteds.replace(detail.getColor(), count+1);
                } else {
                    detailExhausteds.put(detail.getColor(),1);
                }
                if(detailExhausteds.containsKey(detail.getRam()+"")) {
                    int count = detailExhausteds.get(detail.getRam()+"");
                    detailExhausteds.replace(detail.getRam()+"", count+1);
                } else {
                    detailExhausteds.put(detail.getRam()+"",1);
                }
                if(detailExhausteds.containsKey(detail.getRom()+"")) {
                    int count = detailExhausteds.get(detail.getRom()+"");
                    detailExhausteds.replace(detail.getRom()+"", count+1);
                } else {
                    detailExhausteds.put(detail.getRom()+"",1);
                }

            }
        }
        String[] tokens = totalDetail.split("==");

        for (Map.Entry<String,Integer> item : detailExhausteds.entrySet()) {
            String key = item.getKey();
            int count=0;
            for(int i=0; i<tokens.length; i++) {
                if(tokens[i].equals(key)) {
                    count++;
                }
            }
            if(item.getValue()==count) {
                res.add(key);
            }
        }
        return res;
    }

    public boolean checkExhausted(String config) {
        if(this.getExhaustedItems().contains(config)) {
            return true;
        }
        return false;
    }

    public boolean isLocked() {
        if(this.avai==Constant.LOCK) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "ProductUnit{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", ram='" + ram + '\'' +
                ", rom='" + rom + '\'' +
                ", productDetailId=" + productDetailId +
                ", color='" + color + '\'' +
                '}';
    }

    public String getBrandName() {
        return this.brand.getName();
    }

    public int getBrandID() {
        return this.brand.getId();
    }

    public static void main(String[] args) {
        String s = "4GB";
        s=s.substring(0,s.indexOf("GB")).trim();
        System.out.println(Integer.parseInt(s));


    }

}

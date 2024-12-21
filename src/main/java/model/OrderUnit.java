package model;

import DAO.OrderDAO;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

public class OrderUnit {
    public Order order;
    public ArrayList<OrderDetail> details;

    public OrderUnit() {
        this.details = new ArrayList<>();
    }

    // Hàm xác minh chữ ký
    public static boolean verifySignature(String data, String signature, String publicKeyString) {
        try {
            // Giải mã chuỗi public key từ Base64
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);

            // Tạo đối tượng PublicKey từ public key bytes
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // Giải mã chuỗi chữ ký từ Base64
            byte[] signatureBytes = Base64.getDecoder().decode(signature);

            // Tạo đối tượng Signature và khởi tạo với thuật toán SHA256withRSA
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(publicKey);

            // Cung cấp dữ liệu để xác minh chữ ký
            sig.update(data.getBytes());

            // Kiểm tra chữ ký
            return sig.verify(signatureBytes);
        } catch (Exception e) {
            // In lỗi ra màn hình nếu có ngoại lệ xảy ra
            e.printStackTrace();
            return false;
        }
    }

    public OrderUnit(Order order) {
        this.order = order;
        this.details = new ArrayList<>();
    }

    public OrderUnit(Order order, ArrayList<OrderDetail> details) {
        this.order = order;
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderUnit orderUnit = (OrderUnit) o;
        return Objects.equals(order, orderUnit.order);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(order);
    }

    public String toString() {
        String str = order.toString();
        for (OrderDetail detail : details) {
            str += "\t" + detail.toString() + "\n";
        }
        return str;
    }

    public String getDateSet() {
        LocalDateTime time = order.getDateSet();
        return time.toLocalDate() + "</br>" + time.toLocalTime();
    }

    public String getDateSetInLine() {
        LocalDateTime time = order.getDateSet();
        return time.toLocalDate() + " " + time.toLocalTime();
    }

    public String getUpdateTime() {
        LocalDateTime time = order.getupdateTime();
        return time.toLocalDate() + "</br>" + time.toLocalTime();
    }

    public String getUpdateTimeInLine() {
        LocalDateTime time = order.getupdateTime();
        return time.toLocalDate() + " " + time.toLocalTime();
    }

    public int getOrderID() {
        return order.getId();
    }

    public String getProductList() {
        StringBuilder re = new StringBuilder();
        for (OrderDetail detail : details) {
            ProductUnit p = detail.getProductUnit();
            re.append("ID: " + p.productDetailId + " - " + p.getFullName() + " (" + p.color + "-" + p.ram + "-" + p.rom + ") Số lượng: " + detail.getQuantity() + "</br>");
        }
        return re.toString();
    }

    public String getTotalMoney() {
        return Constant.formatPrice(order.getMoney());
    }

    public int getStatus() {
        return order.getStatus();
    }

    public int getNextStatus() {
        int next = order.getStatus() + 1;
        if (next > Constant.COMPLETE) return -1;
        else return next;
    }

    public int getCancelStatus() {
        return Constant.CANCEL;
    }


    public String getStatusString() {
        return Constant.getStatusString(order.getStatus());
    }

//    public static String getStatusString(int status) {
//        return Constant.getStatusString(status);
//    }

    public String getNextStatusString() {
        String re = Constant.getStatusString(order.getStatus() + 1);
//        if(Constant.UNDEFINED.equalsIgnoreCase(re))
//            return "...";
//        else
        return re;
    }

    public String getReceiver() {
        String[] tokens = order.getAddress().split("|");
        if (tokens.length > 1) {
            return tokens[0];
        }
        return "";
    }

    public String getAddress() {
        String[] tokens = order.getAddress().split("|");
        if (tokens.length == 2) {
            return tokens[1];
        }
        return "";
    }

    public String getFullReveiver() {
        return order.getAddress();
    }

    public ArrayList<OrderDetail> getDetails() {
        return details;
    }

    public static void main(String[] args) {
//        LocalDateTime time = LocalDateTime.now();
//        System.out.println(time.toLocalDate());
        System.out.println(verifySignature("{\"id\":45,\"totalMoney\":8990000.0,\"receiver\":\"Nghia (0964967743)   |A, a, a, a\",\"dateSet\":\"2024-12-20T16:07:24\",\"products\":[{\"id\":0,\"productDetailID\":124,\"ram\":0,\"rom\":0,\"price\":8990000.0,\"qty\":1}]}", "S3nCniT9nThpEzpvVlFgN5vHbkBBxokbPgOFQAUlEUVeULbDRW9QhSskNImzTSGgT6JBibvR17ypfZomizUBkvRjs4/ofbC/b+uZDH77cIZrI6CKgk9rPSeUeBFCGZcBEt7nuGiSOAwDuM3wWMAw3rdu8BbIlTVlR1RHqTEz5Zss8oX521fTspTUE3VvJ2Pd70B/M9N+Ejjo0x4ahL5wvqpsJj+7133N/Vs5sn8II/qWQa2wns/fe9QOrcIwY81/U0ezP5ov6phQrYnnXSxTdUfxl4ynXJSHjCxFPHHKQ0mL2wySuoob2R8n1VTW+8KQCa2jINhL6wxxfqK5uqmpGg==", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAphPqXHD4M4q1hs9WvH4bFek4gLygxDNOHMRsYmoWPOl0lEJvRud0hBiF1EnCEOBjo0NJh/C8gw15uWBHsHdnzRjk/8BZjWLvcRfulA26a3ZOZ8unuwOCH22pSci2E6mbm4zY5cqOw/+SAS6tJUAOzyVvO9SHfG09h5DBls3p5LK9LbyKDvFD63bLpV6yTliCCPntjuGFG9wbX+R4t5vZzqiQfOYZMV7K7vJrduKT7iTAphrAFVf7VNH2/8HU+i0wyJ7mIdvuV5gAAYCu1BMD7MXXtiQYG4lEaREDPCQErtu7Nz64bkQfz6W6uG+JQy/t8k3le0BHDSd14E4vYRIvywIDAQAB"));
    }
}

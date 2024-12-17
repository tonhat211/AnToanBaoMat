package com.example.antoanbaomat;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SignatureVerification {

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


    public static void main(String[] args) {
        // Dữ liệu cần ký
        String data1 = "{\"id\":36,\"totalMoney\":8180000.0,\"receiver\":\"Hung Dong Nguyen (092413211)   |510 quốc lộ 13, Hiệp Bình Chánh, Thủ Đức, Hồ Chí Minh\",\"dateSet\":\"2024-12-16T23:54:25\",\"products\":[{\"id\":0,\"productDetailID\":161,\"ram\":0,\"rom\":0,\"price\":4690000.0,\"qty\":1},{\"id\":0,\"productDetailID\":88,\"ram\":0,\"rom\":0,\"price\":3490000.0,\"qty\":1}]}";
        String data2 = "{\"id\":36,\"totalMoney\":8180000.0,\"receiver\":\"Hung Dong Nguyen (092413211)   |510 quốc lộ 13, Hiệp Bình Chánh, Thủ Đức, Hồ Chí Minh\",\"dateSet\":\"2024-12-16T23:54:25\",\"products\":[{\"id\":0,\"productDetailID\":161,\"ram\":0,\"rom\":0,\"price\":4690000.0,\"qty\":1},{\"id\":0,\"productDetailID\":88,\"ram\":0,\"rom\":0,\"price\":3490000.0,\"qty\":1}]}";

        // Public key (của bạn cung cấp)
        String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAphPqXHD4M4q1hs9WvH4bFek4gLygxDNOHMRsYmoWPOl0lEJvRud0hBiF1EnCEOBjo0NJh/C8gw15uWBHsHdnzRjk/8BZjWLvcRfulA26a3ZOZ8unuwOCH22pSci2E6mbm4zY5cqOw/+SAS6tJUAOzyVvO9SHfG09h5DBls3p5LK9LbyKDvFD63bLpV6yTliCCPntjuGFG9wbX+R4t5vZzqiQfOYZMV7K7vJrduKT7iTAphrAFVf7VNH2/8HU+i0wyJ7mIdvuV5gAAYCu1BMD7MXXtiQYG4lEaREDPCQErtu7Nz64bkQfz6W6uG+JQy/t8k3le0BHDSd14E4vYRIvywIDAQAB";

        // Chữ ký hợp lệ (thay bằng chữ ký thực tế của bạn)
        String signature = "ewN9H8rjsHSZ1JQVBBmAa+Mnciq9wGXxLJHBfbRS/JJbk1UrCD6srxxW9j39SPO7hzVI4bdvRaHeRYe0SZriErPbzDv4J/YMK58vWHzIDx1EDUV+WpR6TP2bMowDsr0zNgmrZaxpYTovyKeghY7s7d/29e2egC6H2CJqsi72Ms6gC9cfXElLmPLQD+g5P0lSlF8NHwd0ORjvAz4ZYsEWb1dGc7+L9zt4KbEQeZYDM4N+4xQOTZmUYt/a74PN+46boX/z1/T27rkYSB43ZFevk+DyG+arObgCLN4Vv+zfYwwGd6R8mjIo/menu8KR5nlD0nJ5bxJuEqJGFjkuUzttrw==";
        // Kiểm tra chữ ký hợp lệ
        boolean is = verifySignature(data1, signature, publicKeyString);
        System.out.println("Chữ ký hợp lệ hay không: " + is);

    }
}

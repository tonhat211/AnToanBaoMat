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
        String data = "This is a test data";

        // Public key (của bạn cung cấp)
        String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAphPqXHD4M4q1hs9WvH4bFek4gLygxDNOHMRsYmoWPOl0lEJvRud0hBiF1EnCEOBjo0NJh/C8gw15uWBHsHdnzRjk/8BZjWLvcRfulA26a3ZOZ8unuwOCH22pSci2E6mbm4zY5cqOw/+SAS6tJUAOzyVvO9SHfG09h5DBls3p5LK9LbyKDvFD63bLpV6yTliCCPntjuGFG9wbX+R4t5vZzqiQfOYZMV7K7vJrduKT7iTAphrAFVf7VNH2/8HU+i0wyJ7mIdvuV5gAAYCu1BMD7MXXtiQYG4lEaREDPCQErtu7Nz64bkQfz6W6uG+JQy/t8k3le0BHDSd14E4vYRIvywIDAQAB";

        // Chữ ký hợp lệ (thay bằng chữ ký thực tế của bạn)
        String signature = "erdQEPnBTqc+yWOLyeJ6ej5AGzRkJJpwY09umwsw0+383H8RcFylwTTG7mspVx++nRxworDvaYF57sZrmYZkSmK4XD7+/kbxDN5NY6pu6ABOTcg8dZ+n1Wc/R/4U3JNa97Pdkc1fSsMAxp/XbXG+yC1EAMAZdHUrCqxXEsVgG2OTagk8G6HC3W+AOjmL17nFrMg+UGh1ya9avkH4tWKe4IALQ5F2Vi93qY9cxlhBPQlZAqdYDXvOyIRmm+oTTcd8cfM/VY8v0pfQv2EO6eGT0mWvF3gQCM4wEh7g2dEkM3MOjYHAr+9v042IkVQRd7GTAvZBsA0CRDVJzfxBPUZbow==";

        // Chữ ký sai (chỉ là ví dụ, thay bằng một chữ ký sai)
        String signatureInvalid = "xyz123abc456==";

        // Kiểm tra chữ ký hợp lệ
        boolean isValid = verifySignature(data, signature, publicKeyString);
        System.out.println("Chữ ký hợp lệ: " + isValid); // Nên in ra true

        // Kiểm tra chữ ký sai
        boolean isInvalid = verifySignature(data, signatureInvalid, publicKeyString);
        System.out.println("Chữ ký sai: " + isInvalid); // Nên in ra false
    }
}

package com.example.project3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class MailService {
    @Autowired
    private JavaMailSender emailSender;
    //thêm
//    public static String generateSecretKey() {
//        SecureRandom secureRandom = new SecureRandom();
//        byte[] key = new byte[32]; 
//        secureRandom.nextBytes(key);
//        return Base64.getEncoder().encodeToString(key);
//    } 
//    private static final String SECRET_KEY = "eFhYU1pTS0pNS01WQ09HTE1XVkxPU1RSU1RVV1dYQ0hXWk9LU0tLQ0hJWk9WUlNWV1dYWVo=";
//    
//    public String createJWT(String email) {
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//
//        // Set thời gian hết hạn token
//        long expMillis = nowMillis + 3600;
//        Date exp = new Date(expMillis);
//
//        // Tạo chữ ký
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//
//        // Tạo JWT
//        String jwt = Jwts.builder()
//            .setSubject(email)
//            .setIssuedAt(now)
//            .setExpiration(exp)
//            .signWith(signatureAlgorithm, signingKey)
//            .compact();
//
//        return jwt;
//    }
    //
    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        emailSender.send(message);
    }
//    //thêm
//    public Claims decodeJWT(String jwt) {
//        Claims claims;
//        try {
//            claims = Jwts.parser()
//                .setSigningKey(Base64.getDecoder().decode(SECRET_KEY))
//                .parseClaimsJws(jwt)
//                .getBody();
//        } catch (SignatureException e) {
//            throw new RuntimeException("Token không hợp lệ");
//        }
//        return claims;
//    }

//    public void handlePasswordResetRequest(HttpServletRequest request, HttpServletResponse response) {
//        String token = request.getParameter("token");
//        try {
//            Claims claims = decodeJWT(token);
//            String email = claims.getSubject();
//            // Kiểm tra thời gian hết hạn của token
//            if (claims.getExpiration().before(new Date())) {
//                throw new RuntimeException("Token đã hết hạn");
//            }
//            // Hiển thị trang đổi mật khẩu
//            showResetPasswordPage(email);
//        } catch (Exception e) {
//            // Hiển thị thông báo lỗi
//            showErrorMessage("Link không hợp lệ hoặc đã hết hạn");
//        }
//    }
//
//    // Các phương thức hỗ trợ khác
//    public void showResetPasswordPage(String email) {
//        // Hiển thị trang đổi mật khẩu cho email này
//        System.out.println("Show reset password page for: " + email);
//    }
//
//    public void showErrorMessage(String message) {
//        // Hiển thị thông báo lỗi
//        System.out.println("Error: " + message);
//    }
//    //
}

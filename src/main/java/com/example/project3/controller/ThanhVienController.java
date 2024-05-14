package com.example.project3.controller;

import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.entity.ThanhVien;
import com.example.project3.exception.ResourceNotFoundException;
import com.example.project3.service.ThanhVienService;
import com.example.project3.service.JwtService;
import com.example.project3.service.MailService;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class ThanhVienController {

    @Autowired
    ThanhVienService thanhVienService;

    @Autowired
    JwtService jwtService;

    public ThanhVienDTO getFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return thanhVienService.findByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> checkLogin(@RequestBody Map<String, String> loginRequest) throws JOSEException {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        ThanhVienDTO thanhVienDTO = thanhVienService.login(email, password);
        String token = jwtService.generateToken(email);
        Map<String, Object> response = new HashMap<>();
        response.put("thanhVien", thanhVienDTO);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<ThanhVienDTO> getInfo() {
        return ResponseEntity.ok(getFromToken());
    }

    @GetMapping("/test")
    public ResponseEntity<ThanhVienDTO> test() {
        return ResponseEntity.ok(thanhVienService.findByEmail("nntchinh2001@gmail.com"));
    }

    @PostMapping("/change-pass")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> body) {
        ThanhVienDTO thanhvienDTO = getFromToken();
        String oldPass = body.get("oldPass");
        String newPass = body.get("newPass");
        String confirmNewPass = body.get("confirmNewPass");
        //Kiểm tra login, nếu đúng thực hiện tiếp code bên dưới
        thanhVienService.login(thanhvienDTO.getEmail(), oldPass);
        if (oldPass.equals(newPass)) {
            throw new ResourceNotFoundException("Mật khẩu mới trùng với mật khẩu cũ!!!");
        }
        if (!newPass.equals(confirmNewPass)) {
            throw new ResourceNotFoundException("Mật khẩu mới không khớp!!!");
        }
        thanhVienService.changePassword(thanhvienDTO.getMaTV(), newPass);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đổi mật khẩu thành công!!!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody Map<String, String> emailRequest) throws MessagingException, UnsupportedEncodingException, JOSEException {
        String email = emailRequest.get("email");
        return ResponseEntity.ok(thanhVienService.forgetPassword(email));
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> passwordReset,
             @PathVariable String token) {
        if (jwtService.isValidToken(token)) {
            String email = jwtService.extractEmailFromToken(token);
            String newPass = passwordReset.get("newPass");
            String confirm = passwordReset.get("confirm");
            
            boolean flag = thanhVienService.resetPassword(newPass, confirm, email);
            
            
            return ResponseEntity.ok(flag);
        }
        else throw new RuntimeException("token not valid");
    }

    @PostMapping("/register")
    public ResponseEntity<?> createThanhVien(@RequestBody ThanhVienDTO dTO) {

        return new ResponseEntity<>(thanhVienService.create(dTO), HttpStatus.CREATED);
    }
}

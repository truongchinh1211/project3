package com.example.project3.controller;

import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.entity.ThanhVien;
import com.example.project3.exception.ResourceNotFoundException;
import com.example.project3.service.ThanhVienService;
import com.example.project3.service.JwtService;
import com.nimbusds.jose.JOSEException;
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

    @PostMapping("/changepass")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> body) {
        if (authorizationHeader == null) {
            throw new ResourceNotFoundException("Thiếu header cho request!!!");
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new ResourceNotFoundException("Header không đúng định dạng!!!");
        }
        if (body == null) {
            throw new ResourceNotFoundException("Thiếu body cho request!!!");
        }
        String token = authorizationHeader.substring(7);
        if (!jwtService.isValidToken(token)) {
            throw new ResourceNotFoundException("Token đã hết hạn!!!");
        }
        String email = jwtService.extractEmailFromToken(token);
        String oldPass = body.get("oldPass");
        String newPass = body.get("newPass");
        String confirmNewPass = body.get("confirmNewPass");
        //Kiểm tra login, nếu đúng thực hiện tiếp code bên dưới
        ThanhVienDTO thanhVienDTO = thanhVienService.login(email, oldPass);
        if (oldPass.equals(newPass)) {
            throw new ResourceNotFoundException("Mật khẩu mới trùng với mật khẩu cũ!!!");
        }
        if (!newPass.equals(confirmNewPass)) {
            throw new ResourceNotFoundException("Mật khẩu mới không khớp!!!");
        }
        thanhVienService.changePassword(thanhVienDTO.getMaTV(), newPass);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đổi mật khẩu thành công!!!");
        return ResponseEntity.ok(response);
    }

}

package com.example.project3.controller;

import com.example.project3.entity.ThanhVien;
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

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class ThanhVienController {
    @Autowired
    ThanhVienService thanhVienService;

    @Autowired
    JwtService jwtService;


    public ThanhVien getFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return thanhVienService.findByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> checkLogin(@RequestBody Map<String,String> loginRequest) throws JOSEException {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        ThanhVien thanhVien = thanhVienService.login(email,password);
        String token = jwtService.generateToken(email);
        Map<String,Object> response = new HashMap<>();
        response.put("thanhVien",thanhVien);
        response.put("token",token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<ThanhVien> getInfo(){
        return ResponseEntity.ok(getFromToken());
    }

    @GetMapping("/test")
    public ResponseEntity<ThanhVien>test(){
        return ResponseEntity.ok(thanhVienService.findByEmail("nntchinh2001@gmail.com"));
    }
}

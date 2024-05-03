package com.example.project3.controller;

import com.example.project3.entity.ThanhVien;
import com.example.project3.service.ThanhVienService;
import com.example.project3.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class ThanhVienController {
    @Autowired
    ThanhVienService thanhVienService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ThanhVien> checkLogin(@RequestBody Map<String,String> loginRequest){
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        return ResponseEntity.ok(thanhVienService.login(email,password));
    }

    @GetMapping()
    public ResponseEntity<ThanhVien>test(){
        return ResponseEntity.ok(thanhVienService.findByEmail("nntchinh2001@gmail.com"));
    }
}

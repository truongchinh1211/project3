package com.example.project3.controller;

import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.dto.XuLyDTO;
import com.example.project3.exception.ResourceNotFoundException;
import com.example.project3.service.JwtService;
import com.example.project3.service.ThanhVienService;
import com.example.project3.service.XuLyService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController
@RequestMapping("/api/v1/xuly")
@CrossOrigin("*")
public class XuLyController {

    @Autowired
    XuLyService xuLyService;

    @Autowired
    JwtService jwtService;

    @Autowired
    ThanhVienService thanhVienService;

    @GetMapping("/getByMaTV")
    public ResponseEntity<Map<String, Object>> getByMaTV(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null) {
            throw new ResourceNotFoundException("Thiếu header cho request!!!");
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new ResourceNotFoundException("Header không đúng định dạng!!!");
        }
        String token = authorizationHeader.substring(7);
        if (!jwtService.isValidToken(token)) {
            throw new ResourceNotFoundException("Token đã hết hạn!!!");
        }
        String email = jwtService.extractEmailFromToken(token);
        ThanhVienDTO thanhVienDTO = thanhVienService.findByEmail(email);
        long maTV = thanhVienDTO.getMaTV();
        
        List<XuLyDTO> list = xuLyService.findByMaTV(maTV);
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        return ResponseEntity.ok(response);
    }
}

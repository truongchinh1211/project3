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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    
    public ThanhVienDTO getFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return thanhVienService.findByEmail(email);
    }

    @GetMapping("/get-by-matv")
    public ResponseEntity<Map<String, Object>> getByMaTV() {
        ThanhVienDTO thanhvienDTO = getFromToken();
        long maTV = thanhvienDTO.getMaTV();
        
        List<XuLyDTO> list = xuLyService.findByMaTV(maTV);
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        return ResponseEntity.ok(response);
    }
}

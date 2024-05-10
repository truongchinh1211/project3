package com.example.project3.controller;

import com.example.project3.dto.ResponseThietBiDTO;
import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.dto.ThietBiDTO;
import com.example.project3.service.ThanhVienService;
import com.example.project3.service.ThietBiService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/v1/thietbi")
@CrossOrigin("*")
public class ThietBiController {
    @Autowired
    private ThietBiService thietBiService;
    
    @Autowired
    private ThanhVienService thanhVienService;

    public String getFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

    public ThanhVienDTO getDTOFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return thanhVienService.findByEmail(email);
    }

    @GetMapping()
    public ResponseEntity<List<ThietBiDTO>> getAllByKeyword(@RequestParam(value="keyword",required = false) String keyword){
        if (keyword == null || keyword.isEmpty())
            return ResponseEntity.ok(thietBiService.getAll());
        return ResponseEntity.ok(thietBiService.getAllByKeyword(keyword));
    }
    


}

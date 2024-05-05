package com.example.project3.controller;

import com.example.project3.dto.ThietBiDTO;
import com.example.project3.service.ThietBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/thietbi")
@CrossOrigin("*")
public class ThietBiController {
    @Autowired
    private ThietBiService thietBiService;

    @GetMapping()
    public ResponseEntity<List<ThietBiDTO>> getAllByKeyword(@RequestParam(required = false) String keyword){
        if (keyword == null || keyword.isEmpty())
            return ResponseEntity.ok(thietBiService.getAll());
        return ResponseEntity.ok(thietBiService.getAllByKeyword(keyword));
    }

}

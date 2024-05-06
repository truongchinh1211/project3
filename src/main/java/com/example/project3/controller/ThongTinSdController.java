package com.example.project3.controller;

import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.dto.ThietBiDTO;
import com.example.project3.dto.ThongTinSdDTO;
import com.example.project3.service.JwtService;
import com.example.project3.service.ThanhVienService;
import com.example.project3.service.ThongTinSdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/thongtinsd")
@CrossOrigin("*")
public class ThongTinSdController {
    @Autowired
    private ThongTinSdService thongTinSdService;
    @Autowired
    private ThanhVienService thanhVienService;

    public String getFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

    @PostMapping("datcho")
    public ResponseEntity<ThongTinSdDTO> muon(@RequestBody Map<String, Object> requestData) throws Exception {
        long maTB = Long.parseLong(requestData.get("maTB").toString());
        LocalDateTime TGDatCho= LocalDateTime.parse(requestData.get("TGDatCho").toString());
        ThietBiDTO thietBiDTO = new ThietBiDTO();
        thietBiDTO.setMaTB(maTB);
        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setEmail(getFromToken());
        ThongTinSdDTO thongTinSdDTO = new ThongTinSdDTO();
        thongTinSdDTO.setThanhVien(thanhVienDTO);
        thongTinSdDTO.setThietBi(thietBiDTO);
        return ResponseEntity.ok(thongTinSdService.reserve(thongTinSdDTO));
    }
}

package com.example.project3.controller;

import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.dto.ThietBiDTO;
import com.example.project3.dto.ThongTinSdDTO;
import com.example.project3.entity.ThietBi;
import com.example.project3.service.JwtService;
import com.example.project3.service.ThanhVienService;
import com.example.project3.service.ThongTinSdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

    public ThanhVienDTO getDTOFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return thanhVienService.findByEmail(email);
    }

    @PostMapping("datcho")
    public ResponseEntity<?> datcho(@RequestBody Map<String, Object> requestData) throws Exception {
        long maTB = Long.parseLong(requestData.get("maTB").toString());
        if (requestData.get("TGDatcho") == null || requestData.get("TGDatcho").toString().isEmpty()  ) {
        	String textRes = "Thời gian đặt chỗ không được bỏ trống!!";
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \""+textRes+"\"}");
		}
        LocalDateTime TGDatCho = LocalDateTime.parse(requestData.get("TGDatcho").toString());
        
        
        
        if (TGDatCho.isBefore(LocalDateTime.now())) {
        	String textRes = "Thời gian đặt chỗ phải sau thời điểm hiện tại!!";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \""+textRes+"\"}");
		}
        
        ThietBiDTO thietBiDTO = new ThietBiDTO();
        thietBiDTO.setMaTB(maTB);
        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setEmail(getFromToken());
        ThongTinSdDTO thongTinSdDTO = new ThongTinSdDTO();
        thongTinSdDTO.setThanhVien(thanhVienDTO);
        thongTinSdDTO.setThietBi(thietBiDTO);
        thongTinSdDTO.setTGDatCho(TGDatCho);
        return ResponseEntity.ok(thongTinSdService.reserve(thongTinSdDTO));
    }
    
    
    
    @PostMapping("huydatcho")
    public ResponseEntity<?> huyDatcho(@RequestBody Map<String, Object> requestData) throws Exception {
        long maTB = Long.parseLong(requestData.get("maTB").toString());
        LocalDateTime TGDatCho = LocalDateTime.parse(requestData.get("TGDatcho").toString());
        ThanhVienDTO tvDto = getDTOFromToken();
       
        thongTinSdService.deleteReserve(maTB,tvDto,TGDatCho.toLocalDate());
        
        Map<String, Object> respone = new HashMap<>();
        respone.put("success", "Xóa thành công!!");
        
        return ResponseEntity.ok(respone);

    }

    @GetMapping("/get-by-matv")
    public ResponseEntity<Map<String, Object>> getByMaTV() {
        ThanhVienDTO thanhvienDTO = getDTOFromToken();
        long maTV = thanhvienDTO.getMaTV();

        List<ThongTinSdDTO> list = thongTinSdService.findByMaTV(maTV);
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        return ResponseEntity.ok(response);
    }
    
    
    
}

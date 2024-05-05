package com.example.project3.dto;


import com.example.project3.entity.ThanhVien;
import lombok.Data;

@Data
public class ThanhVienDTO {
    private long maTV;
    private String hoTen;
    private String khoa;
    private String nganh;
    private String sdt;
    private String email;
    private String password;

    public static ThanhVienDTO convertToDTO(ThanhVien thanhVien){
        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setEmail(thanhVien.getEmail());
        thanhVienDTO.setKhoa(thanhVien.getKhoa());
        thanhVienDTO.setNganh(thanhVien.getNganh());
        thanhVienDTO.setSdt(thanhVien.getSDT());
        thanhVienDTO.setMaTV(thanhVien.getMaTV());
        thanhVienDTO.setHoTen(thanhVien.getHoTen());
        thanhVienDTO.setPassword(thanhVien.getPassword());
        return thanhVienDTO;
    }

}

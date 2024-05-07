package com.example.project3.entity;


import com.example.project3.dto.ThanhVienDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="thanhvien")
@Data
public class ThanhVien {
    @Id
    private long MaTV;
    @Column(name="HoTen")
    private String HoTen;
    @Column(name="Khoa")
    private String Khoa;
    @Column(name="Nganh")
    private String Nganh;
    @Column(name="SDT")
    private String SDT;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @OneToMany(mappedBy = "thanhVien", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ThongTinSd> thongTinSds;

    public static ThanhVien convertToEntity(ThanhVienDTO thanhVienDTO){
        ThanhVien thanhVien = new ThanhVien();
        thanhVien.setEmail(thanhVienDTO.getEmail());
        thanhVien.setKhoa(thanhVienDTO.getKhoa());
        thanhVien.setNganh(thanhVienDTO.getNganh());
        thanhVien.setSDT(thanhVienDTO.getSdt());
        thanhVien.setMaTV(thanhVienDTO.getMaTV());
        thanhVien.setHoTen(thanhVienDTO.getHoTen());
        thanhVien.setPassword(thanhVienDTO.getPassword());
        return thanhVien;
    }
    
    public ThanhVienDTO convertToDTO(){
        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setMaTV(MaTV);
        thanhVienDTO.setEmail(email);
        thanhVienDTO.setHoTen(HoTen);
        thanhVienDTO.setKhoa(Khoa);
        thanhVienDTO.setNganh(Nganh);
        thanhVienDTO.setPassword("");
        thanhVienDTO.setSdt(SDT);
        return thanhVienDTO;
    }
}

package com.example.project3.dto;

import com.example.project3.entity.ThanhVien;
import com.example.project3.entity.ThietBi;
import com.example.project3.entity.ThongTinSd;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ThongTinSdDTO {
    private long MaTT;
    private ThanhVienDTO thanhVien;
    private ThietBiDTO thietBi;
    private LocalDateTime TGVao;
    private LocalDateTime TGMuon;
    private LocalDateTime TGTra;
    private LocalDateTime TGDatCho;


    public static ThongTinSdDTO convertToDTO(ThongTinSd thongTinSd){
        ThongTinSdDTO thongTinSdDTO = new ThongTinSdDTO();
        thongTinSdDTO.setMaTT(thongTinSd.getMaTT());
        thongTinSdDTO.setThanhVien(ThanhVienDTO.convertToDTO(thongTinSd.getThanhVien()));
        thongTinSdDTO.setTGVao(thongTinSd.getTGVao());
        thongTinSdDTO.setTGTra(thongTinSd.getTGTra());
        thongTinSdDTO.setTGMuon(thongTinSd.getTGMuon());
        thongTinSdDTO.setThietBi(ThietBiDTO.convertToDTO(thongTinSd.getThietBi()));
        thongTinSdDTO.setTGDatCho(thongTinSd.getTGDatCho());
        return thongTinSdDTO;
    }
}
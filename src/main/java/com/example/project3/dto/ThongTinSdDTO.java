package com.example.project3.dto;

import com.example.project3.entity.ThanhVien;
import com.example.project3.entity.ThietBi;
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
}
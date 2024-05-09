package com.example.project3.entity;


import com.example.project3.dto.ThongTinSdDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="thongtinsd")
@Data
public class ThongTinSd {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long MaTT;
    
    
    @ManyToOne
    @JoinColumn(name = "MaTV")
    private ThanhVien thanhVien;
    @ManyToOne
    @JoinColumn(name = "MaTB")
    private ThietBi thietBi;
    @Column(name = "TGVao")
    private LocalDateTime TGVao;
    @Column(name = "TGMuon")
    private LocalDateTime TGMuon;
    @Column(name = "TGTra")
    private LocalDateTime TGTra;
    @Column(name = "TGDatCho")
    private LocalDateTime TGDatCho;

    public static ThongTinSd convertToEntity(ThongTinSdDTO thongTinSdDTO){
        ThongTinSd thongTinSd = new ThongTinSd();
        thongTinSd.setMaTT(thongTinSdDTO.getMaTT());
        thongTinSd.setThanhVien(ThanhVien.convertToEntity(thongTinSdDTO.getThanhVien()));
        thongTinSd.setTGVao(thongTinSdDTO.getTGVao());
        thongTinSd.setTGTra(thongTinSdDTO.getTGTra());
        thongTinSd.setTGMuon(thongTinSdDTO.getTGMuon());
        thongTinSd.setThietBi(ThietBi.convertToEntity(thongTinSdDTO.getThietBi()));
        thongTinSd.setTGDatCho(thongTinSdDTO.getTGDatCho());
        return thongTinSd;
    }
}

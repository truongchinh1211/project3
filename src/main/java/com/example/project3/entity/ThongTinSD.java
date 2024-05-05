package com.example.project3.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="thongtinsd")
@Data
public class ThongTinSd {
    @Id
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
}

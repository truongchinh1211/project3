package com.example.project3.entity;


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
    private List<ThongTinSD> thongTinSDs;
}

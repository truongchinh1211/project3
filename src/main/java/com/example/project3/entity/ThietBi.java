package com.example.project3.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="thietbi")
@Data
public class ThietBi {
    @Id
    private int MaTB;
    @Column (name = "TenTB")
    private String TenTB;
    @Column (name = "MoTaTB")
    private String MoTaTB;
    @OneToMany(mappedBy = "thietBi", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ThongTinSD> thongTinSDs;
}

package com.example.project3.entity;


import com.example.project3.dto.ThietBiDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="thietbi")
@Data
public class ThietBi {
    @Id
    private long MaTB;
    @Column (name = "TenTB")
    private String TenTB;
    @Column (name = "MoTaTB")
    private String MoTaTB;
    @OneToMany(mappedBy = "thietBi", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ThongTinSd> thongTinSds;


    public static ThietBi convertToEntity(ThietBiDTO thietBiDTO){
        ThietBi thietBi = new ThietBi();
        thietBi.setMaTB(thietBiDTO.getMaTB());
        thietBi.setTenTB(thietBiDTO.getTenTB());
        thietBi.setMoTaTB(thietBiDTO.getMoTaTB());
        return thietBi;
    }
}

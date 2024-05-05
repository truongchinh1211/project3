package com.example.project3.dto;

import com.example.project3.entity.ThietBi;
import lombok.Data;


@Data
public class ThietBiDTO {
    private long MaTB;
    private String TenTB;
    private String MoTaTB;

    public static ThietBiDTO convertToDTO(ThietBi thietBi){
        ThietBiDTO thietBiDTO = new ThietBiDTO();
        thietBiDTO.setMaTB(thietBi.getMaTB());
        thietBiDTO.setTenTB(thietBi.getTenTB());
        thietBiDTO.setMoTaTB(thietBi.getMoTaTB());
        return thietBiDTO;
    }
}
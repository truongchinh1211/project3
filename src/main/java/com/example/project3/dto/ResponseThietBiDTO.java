package com.example.project3.dto;

import lombok.Data;

@Data
public class ResponseThietBiDTO {

    private long MaTB;
    private String TenTB;
    private String MoTaTB;
    private Boolean isCurrentThanhVien;
    private Boolean isDatCho;
    private Boolean isMuon;

}

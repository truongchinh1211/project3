/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.project3.dto;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author ACER
 */

@Data
public class XuLyDTO {
    private long maXL;
    private long maTV;
    private String hinhThucXL;
    private Integer soTien;
    private LocalDate ngayXL;
    private Integer trangThaiXL;
}

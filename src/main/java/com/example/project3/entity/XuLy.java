/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.project3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author ACER
 */

@Entity
@Table(name="xuly")
@Data
public class XuLy {
    @Id
    private long MaXL;
    
    @Column(name="matv")
    private long maTV;
    
    private String HinhThucXL;
    private Integer SoTien;
    private LocalDate NgayXL;
    private Integer TrangThaiXL;
    
}

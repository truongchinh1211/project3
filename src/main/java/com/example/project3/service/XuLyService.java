package com.example.project3.service;

import com.example.project3.dto.XuLyDTO;
import com.example.project3.entity.XuLy;
import com.example.project3.repository.XuLyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class XuLyService {

    @Autowired
    XuLyRepository xuLyRepository;

    public List<XuLyDTO> findByMaTV(long maTV) {
        List<XuLy> xulyList = xuLyRepository.findByMaTV(maTV);
        List<XuLyDTO> xuLyDTOList = new ArrayList<>();
        for (XuLy xuLy : xulyList) {
            xuLyDTOList.add(convertToDTO(xuLy));
        }
        return xuLyDTOList;
    }

    public XuLyDTO convertToDTO(XuLy xuLy) {
        XuLyDTO xuLyDTO = new XuLyDTO();
        xuLyDTO.setMaXL(xuLy.getMaXL());
        xuLyDTO.setMaTV(xuLy.getMaTV());
        xuLyDTO.setHinhThucXL(xuLy.getHinhThucXL());
        xuLyDTO.setSoTien(xuLy.getSoTien());
        xuLyDTO.setNgayXL(xuLy.getNgayXL());
        xuLyDTO.setTrangThaiXL(xuLy.getTrangThaiXL());
        return xuLyDTO;
    }

}

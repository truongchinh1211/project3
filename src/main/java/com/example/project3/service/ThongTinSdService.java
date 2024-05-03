package com.example.project3.service;

import com.example.project3.dto.ThongTinSdDTO;
import com.example.project3.entity.ThongTinSd;
import com.example.project3.repository.ThongTinSDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ThongTinSdService {
    @Autowired
    private ThongTinSDRepository thongTinSDRepository;

    @Transactional
    public ThongTinSd datCho(ThongTinSdDTO thongTinSdDTO){
        ThongTinSd thongTinSd = convertToEntity(thongTinSdDTO);
        (thongTinSDRepository.findByTGDatCho(thongTinSd.getTGDatCho().toLocalDate()).isPresent()){
            throw new Exception()
        }
        return thongTinSDRepository.save(thongTinSd);
    }




    public ThongTinSd convertToEntity(ThongTinSdDTO thongTinSdDTO){
        ThongTinSd thongTinSd = new ThongTinSd();
        thongTinSd.setMaTT(thongTinSd.getMaTT());
        thongTinSd.setThanhVien(thongTinSdDTO.getThanhVien());
        thongTinSd.setTGVao(thongTinSdDTO.getTGVao());
        thongTinSd.setTGTra(thongTinSdDTO.getTGTra());
        thongTinSd.setTGMuon(thongTinSdDTO.getTGMuon());
        thongTinSd.setThietBi(thongTinSdDTO.getThietBi());
        thongTinSd.setTGDatCho(thongTinSdDTO.getTGDatCho());
        return thongTinSd;
    }
    public ThongTinSdDTO convertToDTO(ThongTinSd thongTinSd){
        ThongTinSdDTO thongTinSdDTO = new ThongTinSdDTO();
        thongTinSdDTO.setMaTT(thongTinSd.getMaTT());
        thongTinSdDTO.setThanhVien(thongTinSd.getThanhVien());
        thongTinSdDTO.setTGVao(thongTinSd.getTGVao());
        thongTinSdDTO.setTGTra(thongTinSd.getTGTra());
        thongTinSdDTO.setTGMuon(thongTinSd.getTGMuon());
        thongTinSdDTO.setThietBi(thongTinSd.getThietBi());
        thongTinSdDTO.setTGDatCho(thongTinSd.getTGDatCho());
        return thongTinSdDTO;
    }
}

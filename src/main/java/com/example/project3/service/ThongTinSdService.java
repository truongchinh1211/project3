package com.example.project3.service;

import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.dto.ThongTinSdDTO;
import com.example.project3.entity.ThanhVien;
import com.example.project3.entity.ThietBi;
import com.example.project3.entity.ThongTinSd;
import com.example.project3.exception.ResourceNotFoundException;
import com.example.project3.repository.ThongTinSDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThongTinSdService {
    @Autowired
    private ThongTinSDRepository thongTinSDRepository;

    @Autowired
    private ThanhVienService thanhVienService;

    @Transactional
    public ThongTinSdDTO reserve(ThongTinSdDTO thongTinSdDTO) throws Exception {
        ThongTinSd thongTinSd = convertToEntity(thongTinSdDTO);
        if(thongTinSDRepository.findReservations(thongTinSd.getTGDatCho().toLocalDate()).isPresent()){
            throw new Exception("Lỗi");
        }
        return convertToDTO(thongTinSDRepository.save(thongTinSd));
    }

    @Transactional
    public ThongTinSdDTO muon(ThongTinSdDTO thongTinSdDTO) throws Exception {

        ThongTinSd existedThongTinSd = thongTinSDRepository.findById(thongTinSdDTO.getMaTT())
                .orElseThrow(() -> new ResourceNotFoundException("không tìm thấy thông tin có mã: "+thongTinSdDTO.getMaTT()));
        existedThongTinSd.setTGMuon(thongTinSdDTO.getTGMuon());
        return convertToDTO(thongTinSDRepository.save(existedThongTinSd));
    }


    public ThongTinSd convertToEntity(ThongTinSdDTO thongTinSdDTO){
        ThongTinSd thongTinSd = new ThongTinSd();
        thongTinSd.setMaTT(thongTinSdDTO.getMaTT());
        thongTinSd.setThanhVien(ThanhVien.convertToEntity(thongTinSdDTO.getThanhVien()));
        thongTinSd.setTGVao(thongTinSdDTO.getTGVao());
        thongTinSd.setTGTra(thongTinSdDTO.getTGTra());
        thongTinSd.setTGMuon(thongTinSdDTO.getTGMuon());
        thongTinSd.setThietBi(ThietBi.convertToEntity(thongTinSdDTO.getThietBi()));
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

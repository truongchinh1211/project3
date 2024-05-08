package com.example.project3.service;

import com.example.project3.dto.ResponseThietBiDTO;
import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.dto.ThietBiDTO;
import com.example.project3.entity.ThietBi;
import com.example.project3.repository.ThietBiRepository;
import com.example.project3.repository.ThongTinSDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThietBiService {

    @Autowired
    private ThietBiRepository thietBiRepository;

    @Autowired
    private ThongTinSDRepository thongTinSDRepository;

    public List<ThietBiDTO> getAll() {
        List<ThietBi> thietBis = thietBiRepository.findAll();
        return thietBis.stream()
                .filter(Objects::nonNull)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ThietBiDTO> getAllByKeyword(String keyword) {
        List<ThietBi> list = thietBiRepository.getByKeyword(keyword);
        return list.stream()
                .filter(Objects::nonNull)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ResponseThietBiDTO> listByCurrentUser(ThanhVienDTO tv) {
        List<ThietBi> thietBis = thietBiRepository.findAll();
        List<ResponseThietBiDTO> responseThietBiDTOs = new ArrayList<>();

        for (ThietBi tb : thietBis) {
            responseThietBiDTOs.add(convertToResponseThietBiDTO(tb, tv.getMaTV()));
        }
        
        responseThietBiDTOs.sort(Comparator.comparing((ResponseThietBiDTO dto) -> dto.getIsDatCho() || dto.getIsMuon())
                );
                

        return responseThietBiDTOs;
    }

    public ResponseThietBiDTO convertToResponseThietBiDTO(ThietBi thietBi, long maTV) {
        ResponseThietBiDTO responseThietBiDTO = new ResponseThietBiDTO();
        responseThietBiDTO.setMaTB(thietBi.getMaTB());
        responseThietBiDTO.setTenTB(thietBi.getTenTB());
        responseThietBiDTO.setMoTaTB(thietBi.getMoTaTB());

        responseThietBiDTO.setIsCurrentThanhVien(
                checkIsCurrentThanhVien(thietBi.getMaTB(), maTV));
        
        responseThietBiDTO.setIsDatCho(checkIsDatCho(thietBi.getMaTB()));

        responseThietBiDTO.setIsMuon(checkIsMuon(thietBi.getMaTB()));

        return responseThietBiDTO;
    }

    public Boolean checkIsCurrentThanhVien(long maTB, long maTV) {
        long count = thongTinSDRepository.countByMaTBAndMaTVAndTGTraIsNull(maTB, maTV);
        return count > 0;
    }

    public Boolean checkIsMuon(long maTB) {
        long count = thongTinSDRepository.countByMaTBAndTGMuonIsNotNull(maTB);
        return count > 0;
    }

    public Boolean checkIsDatCho(long maTB) {
        long count = thongTinSDRepository.countByMaTBAndTGDatchoIsNotNull(maTB);
        return count > 0;
    }

    public ThietBiDTO convertToDTO(ThietBi thietBi) {
        return ThietBiDTO.convertToDTO(thietBi);
    }

}

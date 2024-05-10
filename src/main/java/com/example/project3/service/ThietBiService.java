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
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }









    public ThietBiDTO convertToDTO(ThietBi thietBi) {
        return ThietBiDTO.convertToDTO(thietBi);
    }

}

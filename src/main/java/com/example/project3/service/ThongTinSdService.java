package com.example.project3.service;

import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.dto.ThietBiDTO;
import com.example.project3.dto.ThongTinSdDTO;
import com.example.project3.entity.ThanhVien;
import com.example.project3.entity.ThietBi;
import com.example.project3.entity.ThongTinSd;
import com.example.project3.exception.ResourceNotFoundException;
import com.example.project3.repository.ThanhVienRepository;
import com.example.project3.repository.ThietBiRepository;
import com.example.project3.repository.ThongTinSDRepository;

import java.time.Duration;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ThongTinSdService {

    @Autowired
    private ThongTinSDRepository thongTinSDRepository;

    @Autowired
    private ThanhVienRepository thanhVienRepository;

    @Autowired
    private ThietBiRepository thietBiRepository;

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Transactional
    public ThongTinSdDTO reserve(ThongTinSdDTO thongTinSdDTO) throws Exception {
        ThanhVien thanhVien = thanhVienRepository.findByEmail(thongTinSdDTO.getThanhVien().getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thành viên này"));
        ThietBi thietBi = thietBiRepository.findById(thongTinSdDTO.getThietBi().getMaTB())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thiết bị này"));
        ThongTinSd thongTinSd = new ThongTinSd();
        thongTinSd.setTGDatCho(thongTinSdDTO.getTGDatCho());
        thongTinSd.setThanhVien(thanhVien);
        thongTinSd.setThietBi(thietBi);
        if (thongTinSDRepository.findReservations(thietBi.getMaTB(), thongTinSd.getTGDatCho().toLocalDate()).isPresent()) {
            throw new ResourceNotFoundException("Thiết bị đã được đặt chỗ vào ngày này!!");
        }
        LocalDateTime reservationTime = thongTinSd.getTGDatCho();
        LocalDateTime scheduledTime = reservationTime.plusHours(1);
        long delay = Duration.between(LocalDateTime.now(), scheduledTime).toMillis();
        scheduler.schedule(() -> check(thongTinSd), delay, TimeUnit.MILLISECONDS);
        return ThongTinSdDTO.convertToDTO(thongTinSDRepository.save(thongTinSd));

    }

    @Transactional
    public void deleteReserve(long maTB, ThanhVienDTO tvDto, LocalDate TGDatcho) throws Exception {

        Optional<ThongTinSd> ttSd = thongTinSDRepository.findReservation(maTB, tvDto.getMaTV(), TGDatcho);

        if (ttSd.isPresent()) {
            thongTinSDRepository.deleteById(ttSd.get().getMaTT());
        } else {
            throw new ResourceNotFoundException("Không tìm thấy sự đặt chỗ thiết bị!");
        }
    }

    @Transactional
    public void check(ThongTinSd thongTinSd) {
        ThongTinSd existingThongTinSd = thongTinSDRepository.findById(thongTinSd.getMaTT()).orElse(null);
        if (existingThongTinSd != null && existingThongTinSd.getTGMuon() == null) {
            thongTinSDRepository.delete(existingThongTinSd);
        }
    }

    public List<ThongTinSdDTO> findByMaTV(long maTV) {
        List<ThongTinSd> ttSuDung = thongTinSDRepository.getByMaTV(maTV);
        List<ThongTinSdDTO> ttSuDungDTOList = new ArrayList<>();
        for (ThongTinSd ttsd : ttSuDung) {
            ttSuDungDTOList.add(convertToDTO(ttsd));
        }
        return ttSuDungDTOList;
    }

    public ThongTinSdDTO convertToDTO(ThongTinSd ttsd) {
        ThongTinSdDTO ttsdDTO = new ThongTinSdDTO();
        ttsdDTO.setMaTT(ttsd.getMaTT());
        ttsdDTO.setThanhVien(ttsd.getThanhVien().convertToDTO());
        ttsdDTO.setThietBi(ttsd.getThietBi().convertToDTO());
        ttsdDTO.setTGVao(ttsd.getTGVao());
        ttsdDTO.setTGMuon(ttsd.getTGMuon());
        ttsdDTO.setTGTra(ttsd.getTGTra());
        ttsdDTO.setTGDatCho(ttsd.getTGDatCho());
        return ttsdDTO;
    }

    // nếu thiết bị free thì return true, ngược lại thì false
    public boolean isDeviceFree(long maTB, LocalDateTime timeDatCho) {
        String ngayDatCho = timeDatCho.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Optional<ThongTinSd> ttSd = thongTinSDRepository.checkReservation(maTB, ngayDatCho+"%");

        if (ttSd.isPresent()) {
            return false;
        } 
        return true;
    }
}

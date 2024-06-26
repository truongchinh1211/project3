package com.example.project3.service;

import com.example.project3.dto.ThanhVienDTO;
import com.example.project3.entity.ThanhVien;
import com.example.project3.exception.ResourceNotFoundException;
import com.example.project3.repository.ThanhVienRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import jakarta.mail.MessagingException;
import jakarta.persistence.Convert;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ThanhVienService {
    @Autowired
    MailService mailService;
    @Value("${url.path}")
    private String url;
    @Autowired
    private JwtService JwtService;
    @Autowired
    ThanhVienRepository thanhVienRepository;

    public ThanhVienDTO getOne(long maTV) {
        ThanhVien thanhVien = thanhVienRepository.findById(maTV)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thành viên với mã: " + maTV));
        ThanhVienDTO thanhVienDTO = convertToDTO(thanhVien);
        thanhVienDTO.setPassword(null);
        return thanhVienDTO;
    }

    public ThanhVienDTO login(String email, String password) {
        Optional<ThanhVien> thanhVienOptional = thanhVienRepository.findByEmail(email);
        if (thanhVienOptional.isPresent()) {
            ThanhVien thanhVien = thanhVienOptional.get();
            if (thanhVien.getPassword().equals(password)) {
                thanhVien.setPassword(null);
                return convertToDTO(thanhVien);
            }
        }
        throw new ResourceNotFoundException("Tài khoản hoặc mật khẩu không đúng!!");
    }

    public ThanhVienDTO findByEmail(String email) {
        Optional<ThanhVien> thanhVienOptional = thanhVienRepository.findByEmail(email);
        if (thanhVienOptional.isPresent()) {
            return convertToDTO(thanhVienOptional.get());
        }
        throw new ResourceNotFoundException("email không đúng!!");

    }

    @Transactional
    public ThanhVienDTO create(ThanhVienDTO thanhVienDTO) {
        Optional<ThanhVien> thanhVienOptional = thanhVienRepository.findByEmail(thanhVienDTO.getEmail());
        if (thanhVienOptional.isPresent()) {
            throw new DataIntegrityViolationException("Email đã được sử dụng !!!");
        }
        ThanhVien thanhVien = convertToEntity(thanhVienDTO);
        return convertToDTO(thanhVienRepository.save(thanhVien));
    }

    @Transactional
    public ThanhVienDTO update(ThanhVienDTO updatedThanhVienDTO) {
        // Kiểm tra xem thành viên có tồn tại không
        ThanhVien existingThanhVien = thanhVienRepository.findById(updatedThanhVienDTO.getMaTV())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thành viên với mã: " + updatedThanhVienDTO.getMaTV()));
        String updatedEmail = updatedThanhVienDTO.getEmail();
        if (!existingThanhVien.getEmail().equals(updatedEmail)) {
            Optional<ThanhVien> existingEmail = thanhVienRepository.findByEmail(updatedEmail);
            if (existingEmail.isPresent()) {
                throw new DataIntegrityViolationException("Email đã được sử dụng !!!");
            }
        }
        existingThanhVien = convertToEntity(updatedThanhVienDTO);
        return convertToDTO(thanhVienRepository.save(existingThanhVien));
    }

    @Transactional
    public void delete(long maTV) {
        try {
            thanhVienRepository.deleteById(maTV);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Không tìm thấy thành viên với mã: " + maTV);
        }
    }

    public ThanhVien convertToEntity(ThanhVienDTO thanhVienDTO) {
        ThanhVien thanhVien = new ThanhVien();
        thanhVien.setEmail(thanhVienDTO.getEmail());
        thanhVien.setKhoa(thanhVienDTO.getKhoa());
        thanhVien.setNganh(thanhVienDTO.getNganh());
        thanhVien.setSDT(thanhVienDTO.getSdt());
        thanhVien.setMaTV(thanhVienDTO.getMaTV());
        thanhVien.setHoTen(thanhVienDTO.getHoTen());
        thanhVien.setPassword(thanhVienDTO.getPassword());
        return thanhVien;
    }

    public ThanhVienDTO convertToDTO(ThanhVien thanhVien) {
        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setEmail(thanhVien.getEmail());
        thanhVienDTO.setKhoa(thanhVien.getKhoa());
        thanhVienDTO.setNganh(thanhVien.getNganh());
        thanhVienDTO.setSdt(thanhVien.getSDT());
        thanhVienDTO.setMaTV(thanhVien.getMaTV());
        thanhVienDTO.setHoTen(thanhVien.getHoTen());
        thanhVienDTO.setPassword(thanhVien.getPassword());
        return thanhVienDTO;
    }

    @Transactional
    public ThanhVienDTO changePassword(long maTV, String newPassword) {
        Optional<ThanhVien> thanhVienOptional = thanhVienRepository.findById(maTV);
        ThanhVien thanhVien = thanhVienOptional.get();
        thanhVien.setPassword(newPassword);
        return convertToDTO(thanhVienRepository.save(thanhVien));
    }
    
    public boolean forgetPassword(String email) throws UnsupportedEncodingException, MessagingException, JOSEException {
        Optional<ThanhVien> thanhVienOptional = thanhVienRepository.findByEmail(email);
        //thêm
        
        String token = JwtService.generateTokenCustomize(email);
        //
        String subject = "Quen mat khau";
        String body = "Nhap vao link de doi mat khau: " + url+"src/main/resources/Client/User/resetpassword.html?token=" + token;

        if (thanhVienOptional.isPresent()) {
            mailService.send(email, subject, body);

            return true;
        }
        else throw new ResourceNotFoundException("Không tìm thấy email: " + email);

    }
    
    public boolean resetPassword(String newPass, String confirm, String email) {

        
        if (!newPass.equals(confirm)) {
            throw new ResourceNotFoundException("Mật khẩu mới không khớp!!!");
        }
        
       Optional<ThanhVien> thanhVienOptional = thanhVienRepository.findByEmail(email);
       ThanhVien thanhVien = thanhVienOptional.get();
       
        changePassword(thanhVien.getMaTV(), newPass);
        
        return true;
    }
}

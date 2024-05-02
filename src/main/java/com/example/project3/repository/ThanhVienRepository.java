package com.example.project3.repository;

import com.example.project3.entity.ThanhVien;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ThanhVienRepository extends JpaRepository<ThanhVien,Long>{
    Optional<ThanhVien> findByEmail(String email);
}

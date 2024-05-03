package com.example.project3.repository;

import com.example.project3.entity.ThongTinSd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ThongTinSDRepository extends JpaRepository<ThongTinSd,Long> {
    @Query(value = "SELECT * FROM thongtinsd WHERE DATE(TGDatCho) = ?1", nativeQuery = true)
    Optional<List<ThongTinSd>> findByTGDatCho(LocalDate TGDatCho);
}

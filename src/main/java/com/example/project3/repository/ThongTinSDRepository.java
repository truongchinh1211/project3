package com.example.project3.repository;

import com.example.project3.entity.ThongTinSd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ThongTinSDRepository extends JpaRepository<ThongTinSd, Long> {

    @Query(value = "SELECT * FROM thongtinsd WHERE MaTB = ?1 AND DATE(TGDatCho) = ?2 ", nativeQuery = true)
    Optional<List<ThongTinSd>> findReservations(long MaTB, LocalDate TGDatCho);

    @Query(value = "SELECT * FROM thongtinsd WHERE MaTV = ?1", nativeQuery = true)
    List<ThongTinSd> getByMaTV(long MaTV);

}

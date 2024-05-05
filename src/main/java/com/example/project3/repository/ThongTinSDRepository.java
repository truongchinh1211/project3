package com.example.project3.repository;

import com.example.project3.entity.ThongTinSd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ThongTinSDRepository extends JpaRepository<ThongTinSd,Long> {
    @Query(value = "SELECT * FROM thongtinsd WHERE DATE(TGDatCho) = ?1", nativeQuery = true)
    Optional<List<ThongTinSd>> findReservations(LocalDate TGDatCho);

    @Query("SELECT t FROM ThongTinSd t WHERE t.TGDatCho <= :cutoffTime AND t.TGMuon IS NULL")
    List<ThongTinSd> findExpiredReservations(@Param("cutoffTime") LocalDateTime cutoffTime);
}

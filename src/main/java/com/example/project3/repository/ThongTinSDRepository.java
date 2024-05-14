package com.example.project3.repository;

import com.example.project3.entity.ThietBi;
import com.example.project3.entity.ThongTinSd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ThongTinSDRepository extends JpaRepository<ThongTinSd, Long> {

    @Query(value = "SELECT * FROM thongtinsd WHERE MaTB = ?1 AND DATE(TGDatCho) = ?2 AND TGTra IS NULL", nativeQuery = true)
    Optional<ThongTinSd> findReservations(long MaTB, LocalDate TGDatcho);

    @Query(value = "SELECT * from thongtinsd where MaTB = ?1 AND MaTV = ?2 AND DATE(TGDatcho) = ?3 AND TGTra IS NULL", nativeQuery = true)
    Optional<ThongTinSd> findReservation(long MaTB, long maTV, LocalDate TGDatcho);
    
    
    @Query(value = "SELECT * FROM thongtinsd WHERE MaTV = ?1 ORDER BY TGDatcho DESC", nativeQuery = true)
    List<ThongTinSd> getByMaTV(long MaTV);
    
    @Query(value = "SELECT * from thongtinsd where MaTB = ?1 "
            + "AND TGDatcho LIKE ?2 AND TGTra IS NULL", nativeQuery = true)
    Optional<ThongTinSd> checkReservation(long maTB, String ngayDatCho);

}

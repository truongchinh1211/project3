package com.example.project3.repository;

import com.example.project3.entity.ThietBi;
import com.example.project3.entity.ThongTinSd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ThongTinSDRepository extends JpaRepository<ThongTinSd, Long> {

    @Query(value = "SELECT * FROM thongtinsd WHERE MaTB = ?1 AND DATE(TGDatCho) = ?2 ", nativeQuery = true)
    Optional<List<ThongTinSd>> findReservations(long MaTB, LocalDate TGDatCho);

    @Query(value = "SELECT * FROM thongtinsd WHERE MaTB = ?1 AND TGTra IS NULL " , nativeQuery = true)
    Optional<ThongTinSd> checkTGTraIsNull(long MaTB);
    
    @Transactional
    @Modifying
    @Query(value = "delete from thongtinsd where MaTB = ?1 AND TGTra Is NULL", nativeQuery = true)
    void deleteByMaTBAndTGTraIsNull(long MaTB);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE thongtinsd SET TGDatcho = null, TGMuon = null, TGTra = ?2 WHERE MaTB = ?1", nativeQuery = true)
    void updateTraThietBi(long MaTB, LocalDate TGTra);
    
    
    
    @Query(value = "SELECT * FROM thongtinsd WHERE MaTV = ?1", nativeQuery = true)
    List<ThongTinSd> getByMaTV(long MaTV);
    
    
    @Query(value = "SELECT COUNT(*) FROM thongtinsd WHERE MaTB = ?1 AND MaTV = ?2 AND TGTra IS NULL",
            nativeQuery = true)
    long countByMaTBAndMaTVAndTGTraIsNull(long maTB, long maTV);
    
    @Query(value ="""
                  select count(*)
                  from thongtinsd
                  where thongtinsd.MaTB = ?1 
                  \tand thongtinsd.TGMuon is not null""", nativeQuery = true)
    long countByMaTBAndTGMuonIsNotNull(long maTB);
    
    @Query(value ="""
                  select count(*)
                  from thongtinsd
                  where thongtinsd.MaTB = ?1 
                  \tand thongtinsd.TGDatcho is not null""", nativeQuery = true)
    long countByMaTBAndTGDatchoIsNotNull(long maTB);

}

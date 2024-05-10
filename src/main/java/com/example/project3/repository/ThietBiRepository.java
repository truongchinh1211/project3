package com.example.project3.repository;

import com.example.project3.entity.ThietBi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThietBiRepository extends JpaRepository<ThietBi,Long> {
    @Query(value = "SELECT * FROM thietbi WHERE TenTB like %?1%", nativeQuery = true)
    List<ThietBi> getByKeyword(String keyword);
    
    
   
    
    @Override
    List<ThietBi> findAll();
    
    
    
}

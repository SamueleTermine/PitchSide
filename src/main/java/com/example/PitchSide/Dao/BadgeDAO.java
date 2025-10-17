package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Badge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeDAO extends CrudRepository<Badge, Long> {
    @Query("SELECT b FROM Badge b WHERE b.soglia_punti <= :punti")
    List<Badge> findAllBadgeSbloccati(int punti);
}


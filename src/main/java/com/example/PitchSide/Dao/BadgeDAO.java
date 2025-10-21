package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Badge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BadgeDAO extends CrudRepository<Badge, Long> {
    @Query("SELECT b FROM Badge b WHERE b.soglia_punti <= :punti")
    List<Badge> findBadgeSbloccati(@Param("punti") int punti);

    @Query("SELECT b FROM Badge b WHERE b.soglia_punti > :punti")
    List<Badge> findBadgeNonSbloccati(@Param("punti") int punti);


}


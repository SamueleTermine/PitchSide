package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Campionato;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

public interface CampionatoDAO extends CrudRepository<Campionato, Long> {
    Optional<Campionato> findByApiId(int apiId);
}

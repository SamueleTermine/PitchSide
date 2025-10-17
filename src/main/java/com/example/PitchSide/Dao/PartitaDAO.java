package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Partita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartitaDAO extends CrudRepository<Partita, Long> {
    @Query("SELECT p FROM Partita p WHERE p.id_partita = :idPartita")
    Optional<Partita> findByIdPartita(@Param("id_partita") Long idPartita);
}

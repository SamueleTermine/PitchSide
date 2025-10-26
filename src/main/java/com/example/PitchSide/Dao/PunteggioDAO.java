package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Punteggio;
import com.example.PitchSide.model.Utente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PunteggioDAO extends CrudRepository<Punteggio, Long> {

    Optional<Punteggio> findByUtente(Utente utente);

    @Query("SELECT u FROM Utente u ORDER BY u.punteggio.punteggio_totale DESC")
    List<Utente> findAllOrderByPunteggioDesc();

//    @Query("SELECT u FROM Utente u ORDER BY u.punteggio.punteggio_totale DESC LIMIT :limit")
//    List<Utente> findTopUtentiByPunteggioDesc(int limit);

}

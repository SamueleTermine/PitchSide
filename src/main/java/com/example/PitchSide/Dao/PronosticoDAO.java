package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Partita;
import com.example.PitchSide.model.Pronostico;
import com.example.PitchSide.model.Utente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface PronosticoDAO extends CrudRepository<Pronostico, Long> {

    List<Pronostico> findByUtente(Utente utente);

    List<Pronostico> findByPartita(Partita partita);

    @Query("SELECT COUNT(p) FROM Pronostico p WHERE p.utente = :utente AND p.data_pronostico >= :inizio AND p.data_pronostico < :fine")
    int conteggioPronostici(Utente utente, LocalDateTime inizio, LocalDateTime fine);
}
package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Punteggio;
import com.example.PitchSide.model.Utente;
import org.springframework.data.repository.CrudRepository;

public interface PunteggioDAO extends CrudRepository<Punteggio, Long> {
    Punteggio findByUtente(Utente utente);

}

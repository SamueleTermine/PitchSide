package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Preferito;
import com.example.PitchSide.model.Utente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreferitoDAO extends CrudRepository<Preferito, Long> {

    List<Preferito> findByUtente(Utente utente);

    boolean existsByUtenteAndPartita_IdPartita(Utente utente, Long idPartita);
}

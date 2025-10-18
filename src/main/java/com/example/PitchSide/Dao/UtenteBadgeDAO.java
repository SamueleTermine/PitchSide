package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Badge;
import com.example.PitchSide.model.Utente;
import com.example.PitchSide.model.Utente_Badge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UtenteBadgeDAO extends CrudRepository<Utente_Badge, Long> {

    boolean existsByUtenteAndBadge(Utente utente, Badge badge);

    List<Utente_Badge> findByUtente(Utente utente);
}

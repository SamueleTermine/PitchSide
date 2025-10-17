package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Utente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UtenteDAO extends CrudRepository<Utente, Long> {
    @Query("select u from Utente u where u.email = :email and u.password = :password")
    Utente findByEmailAndPassword(String email, String password);

    @Query("select count(u) > 0 from Utente u where u.nickname = :nickname")
    boolean nicknameEsiste(String nickname);

    @Query("select count(u) > 0 from Utente u where u.email = :email")
    boolean emailEsiste(String email);
}

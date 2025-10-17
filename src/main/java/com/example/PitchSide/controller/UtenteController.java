package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.UtenteDAO;
import com.example.PitchSide.model.Utente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtenteController {
    @Autowired
    private UtenteDAO utenteRepository;

    @PostMapping("/signup")
    public ResponseEntity<Utente> signup(@RequestBody Utente utente, HttpSession session) {
        // verifica unicità nickname/email, salva utente, ecc
        if (utenteRepository.nicknameEsiste(utente.getNickname()) || utenteRepository.emailEsiste(utente.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Utente nuovoUtente = utenteRepository.save(utente);
        session.setAttribute("utenteLoggato", nuovoUtente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoUtente);
    }
}


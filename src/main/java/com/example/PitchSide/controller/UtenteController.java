package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.UtenteDAO;
import com.example.PitchSide.model.Login;
import com.example.PitchSide.model.Utente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtenteController {
    @Autowired
    private UtenteDAO utenteRepository;

    @PostMapping("/signup")
    public ResponseEntity<Utente> signup(@RequestBody Utente utente, HttpSession session) {
        if (utenteRepository.nicknameEsiste(utente.getNickname()) || utenteRepository.emailEsiste(utente.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Utente nuovoUtente = utenteRepository.save(utente);
        session.setAttribute("utenteLoggato", nuovoUtente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoUtente);
    }

    @PostMapping("/signin")
    public ResponseEntity<Utente> signin(@RequestBody Login login, HttpSession session) {
        Utente utente = utenteRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        session.setAttribute("utenteLoggato", utente);
        return ResponseEntity.ok(utente);
    }

    @GetMapping("/profilo")
    public ResponseEntity<Utente> profilo(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(utente);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}


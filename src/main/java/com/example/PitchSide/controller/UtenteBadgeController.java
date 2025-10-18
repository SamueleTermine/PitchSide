package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.UtenteBadgeDAO;
import com.example.PitchSide.model.Utente;
import com.example.PitchSide.model.Utente_Badge;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtenteBadgeController {
    @Autowired
    private UtenteBadgeDAO utenteBadgeRepository;

    @GetMapping("/badge/sbloccati")
    public ResponseEntity<List<Utente_Badge>> getBadgeSbloccati(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(401).build();
        }

        List<Utente_Badge> badgeSbloccati = utenteBadgeRepository.findByUtente(utente);
        return ResponseEntity.ok(badgeSbloccati);
    }
}

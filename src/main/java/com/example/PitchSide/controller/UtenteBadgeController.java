package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.PunteggioDAO;
import com.example.PitchSide.Dao.UtenteBadgeDAO;
import com.example.PitchSide.model.Punteggio;
import com.example.PitchSide.model.Utente;
import com.example.PitchSide.model.Utente_Badge;
import com.example.PitchSide.service.PronosticoService;
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
    @Autowired
    private PronosticoService pronosticoService;

    @Autowired
    private PunteggioDAO punteggioRepository;

    @GetMapping("/badge/sbloccati")
    public ResponseEntity<List<Utente_Badge>> getBadgeSbloccati(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(401).build();
        }

        Punteggio punteggio = punteggioRepository.findByUtente(utente)
                .orElse(new Punteggio(null, utente, 0));
        pronosticoService.aggiornaBadgeUtente(utente, punteggio.getPunteggio_totale());

        List<Utente_Badge> badgeSbloccati = utenteBadgeRepository.findByUtente(utente);
        return ResponseEntity.ok(badgeSbloccati);
    }
}

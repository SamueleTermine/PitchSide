package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.PunteggioDAO;
import com.example.PitchSide.model.Punteggio;
import com.example.PitchSide.model.Utente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PunteggioController {

    @Autowired
    private PunteggioDAO punteggioRepository;


    @GetMapping("/classifica-globale")
    public ResponseEntity<List<Utente>> getClassificaGenerale() {
        List<Utente> utenti = punteggioRepository.findAllOrderByPunteggioDesc();
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/punteggio-utente")
    public ResponseEntity<Punteggio> getPunteggioUtente(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(401).build();
        }
        Punteggio punteggio_utente = utente.getPunteggio();
        return ResponseEntity.ok(punteggio_utente);
    }

    /*@GetMapping("/top/{limit}")
    public ResponseEntity<List<Utente>> getTopUtenti(@PathVariable int limit) {
        limit = limit > 100 ? 100 : limit;
        List<Utente> utenti = punteggioRepository.findTopUtentiByPunteggioDesc(limit);
        return ResponseEntity.ok(utenti);
    }*/

//    @GetMapping("/top/{limit}")
//    public ResponseEntity<List<Utente>> getTopUtenti() {
//        int limit = 10;
//        List<Utente> utenti = punteggioRepository.findTopUtentiByPunteggioDesc(limit);
//        return ResponseEntity.ok(utenti);
//    }
}

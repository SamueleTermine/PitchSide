package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.PartitaDAO;
import com.example.PitchSide.Dao.PreferitoDAO;
import com.example.PitchSide.model.Partita;
import com.example.PitchSide.model.Preferito;
import com.example.PitchSide.model.Utente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/preferiti")
public class PreferitoController {

    @Autowired
    private PreferitoDAO preferitoRepository;

    @Autowired
    private PartitaDAO partitaRepository;

    @PostMapping("/{idPartita}")
    public ResponseEntity<Preferito> aggiungiPreferito(@PathVariable Long idPartita, HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(401).build();
        }

        if (preferitoRepository.existsByUtenteAndPartita_IdPartita(utente, idPartita)) {
            return ResponseEntity.status(409).build();
        }

        Partita partita = partitaRepository.findById(idPartita).orElse(null);
        if (partita == null) {
            return ResponseEntity.notFound().build();
        }

        Preferito nuovoPreferito = new Preferito(utente, partita, LocalDateTime.now());
        Preferito nuovaPartitaPreferita = preferitoRepository.save(nuovoPreferito);
        return ResponseEntity.status(201).body(nuovaPartitaPreferita);
    }

    @DeleteMapping("/{idPartita}")
    public ResponseEntity<Void> rimuoviPreferito(@PathVariable Long idPartita, HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(401).build();
        }

        List<Preferito> preferiti = preferitoRepository.findByUtente(utente);
        for (Preferito p : preferiti) {
            if (p.getPartita().getIdPartita().equals(idPartita)) {
                preferitoRepository.delete(p);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Preferito>> getPreferitiUtente(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(401).build();
        }

        List<Preferito> preferiti = preferitoRepository.findByUtente(utente);
        return ResponseEntity.ok(preferiti);
    }
}
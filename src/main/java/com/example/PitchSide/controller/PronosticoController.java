package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.*;
import com.example.PitchSide.model.Pronostico;
import com.example.PitchSide.model.Utente;
import com.example.PitchSide.model.Partita;
import com.example.PitchSide.model.Punteggio;
import com.example.PitchSide.model.Badge;
import com.example.PitchSide.model.Utente_Badge;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class PronosticoController {

    @Autowired
    private PronosticoDAO pronosticoRepository;
    @Autowired
    private UtenteDAO utenteRepository;
    @Autowired
    private PartitaDAO partitaRepository;
    @Autowired
    private BadgeDAO badgeRepository;
    @Autowired
    private UtenteBadgeDAO utenteBadgeRepository;
    @Autowired
    private PunteggioDAO punteggioRepository;


    private static final int LIMITE_PRONOSTICI_GIORNO = 5;

    @PostMapping("/nuovoPronostico")
    public ResponseEntity<Pronostico> creaPronostico(@RequestBody Pronostico pronostico, HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LocalDate oggi = LocalDate.now();
        LocalDateTime inizioGiorno = oggi.atStartOfDay();
        LocalDateTime inizioGiornoSuccessivo = oggi.plusDays(1).atStartOfDay();

        int pronosticiOggi = pronosticoRepository.conteggioPronostici(utente, inizioGiorno, inizioGiornoSuccessivo);
        if (pronosticiOggi >= LIMITE_PRONOSTICI_GIORNO) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        pronostico.setUtente(utente);
        pronostico.setData_pronostico(LocalDateTime.now());
        Pronostico nuovoPronostico = pronosticoRepository.save(pronostico);

        Punteggio punteggio = punteggioRepository.findByUtente(utente);
        if (punteggio == null) {
            punteggio = new Punteggio();
            punteggio.setUtente(utente);
            punteggio.setPunteggio_totale(0);
        }
        int puntiOrigine = punteggio.getPunteggio_totale();

        if (pronostico.getEsito() != null) {
            if (pronostico.getEsito().equals(pronostico.getScelta())) {
                puntiOrigine += 3;
            } else {
                puntiOrigine = Math.max(0, puntiOrigine - 2);
            }
            punteggio.setPunteggio_totale(puntiOrigine);
            punteggioRepository.save(punteggio);

            List<Badge> badgeSbloccabili = badgeRepository.findAllBadgeSbloccati(puntiOrigine);
            for (Badge badge : badgeSbloccabili) {
                if (!utenteBadgeRepository.existsByUtenteAndBadge(utente, badge)) {
                    Utente_Badge nuovoBadge = new Utente_Badge(utente, badge, LocalDateTime.now());
                    utenteBadgeRepository.save(nuovoBadge);
                }
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoPronostico);
    }

    @GetMapping("/storico")
    public ResponseEntity<List<Pronostico>> storicoPronostici(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Pronostico> pronostici = pronosticoRepository.findByUtente(utente);
        return ResponseEntity.ok(pronostici);
    }

    @GetMapping("/partita/{idPartita}")
    public ResponseEntity<List<Pronostico>> pronosticiPerPartita(@PathVariable Long idPartita) {
        Partita partita = partitaRepository.findByIdPartita(idPartita).orElse(null);
        if (partita == null) {
            return ResponseEntity.notFound().build();
        }
        List<Pronostico> pronostici = pronosticoRepository.findByPartita(partita);
        return ResponseEntity.ok(pronostici);
    }
}



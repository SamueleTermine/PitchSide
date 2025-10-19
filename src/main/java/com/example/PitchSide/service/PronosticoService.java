package com.example.PitchSide.service;

import com.example.PitchSide.Dao.*;
import com.example.PitchSide.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PronosticoService {

    @Autowired
    private PronosticoDAO pronosticoRepository;

    @Autowired
    private PunteggioDAO punteggioRepository;

    @Autowired
    private BadgeDAO badgeRepository;

    @Autowired
    private UtenteBadgeDAO utenteBadgeRepository;

    @Autowired
    private PartitaDAO partitaDAO;

    @Transactional
    public void verificaPronosticiEaggiornaPunteggi(Long idPartita) {
        Partita partita = partitaDAO.findById(idPartita).orElseThrow(() -> new RuntimeException("Partita non trovata"));

        String risultatoReale;
        if (partita.getGoal_casa() > partita.getGoal_ospite()) {
            risultatoReale = "1";
        } else if (partita.getGoal_casa().equals(partita.getGoal_ospite())) {
            risultatoReale = "X";
        } else {
            risultatoReale = "2";
        }

        List<Pronostico> pronostici = pronosticoRepository.findByPartita(partita);

        for (Pronostico p : pronostici) {
            p.setEsito(risultatoReale);

            int punteggioAttuale = 0;
            if (p.getScelta().equals(risultatoReale)) {
                punteggioAttuale = 3;
            } else {
                punteggioAttuale = -2;
            }
            p.setPunteggio_ottenuto(punteggioAttuale);
            pronosticoRepository.save(p);

            Utente utente = p.getUtente();
            Punteggio punteggio = punteggioRepository.findByUtente(utente);
            if (punteggio == null) {
                punteggio = new Punteggio();
                punteggio.setUtente(utente);
                punteggio.setPunteggio_totale(0);
            }
            int nuovoTotale = Math.max(0, punteggio.getPunteggio_totale() + punteggioAttuale);
            punteggio.setPunteggio_totale(nuovoTotale);
            punteggioRepository.save(punteggio);

            List<Badge> badgeSbloccabili = badgeRepository.findAllBadgeSbloccati(nuovoTotale);
            for (Badge badge : badgeSbloccabili) {
                if (!utenteBadgeRepository.existsByUtenteAndBadge(utente, badge)) {
                    Utente_Badge nuovoBadge = new Utente_Badge(utente, badge, LocalDateTime.now());
                    utenteBadgeRepository.save(nuovoBadge);
                }
            }
        }
    }
}
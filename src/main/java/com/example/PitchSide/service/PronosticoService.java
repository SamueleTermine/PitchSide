package com.example.PitchSide.service;

import com.example.PitchSide.Dao.PartitaDAO;
import com.example.PitchSide.Dao.PronosticoDAO;
import com.example.PitchSide.Dao.PunteggioDAO;
import com.example.PitchSide.Dao.BadgeDAO;
import com.example.PitchSide.Dao.UtenteBadgeDAO;
import com.example.PitchSide.model.Partita;
import com.example.PitchSide.model.Pronostico;
import com.example.PitchSide.model.Punteggio;
import com.example.PitchSide.model.Badge;
import com.example.PitchSide.model.Utente;
import com.example.PitchSide.model.Utente_Badge;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PronosticoService {

    @Autowired
    private PronosticoDAO pronosticoRepository;

    @Autowired
    private PartitaDAO partitaRepository;

    @Autowired
    private PunteggioDAO punteggioRepository;

    @Autowired
    private BadgeDAO badgeRepository;

    @Autowired
    private UtenteBadgeDAO utenteBadgeRepository;

    public static final int PUNTI_CORRETTO = 3;
    public static final int PUNTI_SCONTO = -2;

    public void verificaPronosticiEaggiornaPunteggi() {
        List<Partita> concluse = partitaRepository.findByStatoIn(List.of("FT"));
        for (Partita partita : concluse) {
            aggiornaPronosticiPartita(partita);
        }
    }

    public void verificaPronosticiEaggiornaPunteggi(Long idPartita) {
        Partita partita = partitaRepository.findByIdPartita(idPartita).orElse(null);
        if (partita != null && "FT".equalsIgnoreCase(partita.getStato())) {
            aggiornaPronosticiPartita(partita);
        }
    }

    public void aggiornaPronosticiPartita(Partita partita) {
        String esitoReale = calcolaEsito(partita);
        List<Pronostico> pronostici = pronosticoRepository.findByPartita(partita);

        for (Pronostico p : pronostici) {
            if (p.getEsito() == null) {  // evitiamo ricalcoli già fatti
                boolean corretto = esitoReale.equals(p.getScelta());
                int delta = corretto ? PUNTI_CORRETTO : PUNTI_SCONTO;

                p.setEsito(esitoReale);
                p.setPunteggio_ottenuto(delta);
                pronosticoRepository.save(p);

                Punteggio punteggio = aggiornaPunteggioUtente(p.getUtente(), delta);

                aggiornaBadgeUtente(p.getUtente(), punteggio.getPunteggio_totale());
            }
        }
    }

    public String calcolaEsito(Partita partita) {
        if (partita.getGoal_casa() == null || partita.getGoal_ospite() == null) {
            return "ND";
        }
        if (partita.getGoal_casa() > partita.getGoal_ospite()) return "1";
        if (partita.getGoal_casa() < partita.getGoal_ospite()) return "2";
        return "X";
    }

    public Punteggio aggiornaPunteggioUtente(Utente utente, int delta) {
        Punteggio punteggio = punteggioRepository.findByUtente(utente)
                .orElseGet(() -> new Punteggio(null, utente, 0));

        int nuovoTotale = Math.max(0, punteggio.getPunteggio_totale() + delta);
        punteggio.setPunteggio_totale(nuovoTotale);
        return punteggioRepository.save(punteggio);
    }

    public void aggiornaBadgeUtente(Utente utente, int punteggioTotale) {
        List<Badge> badgeSbloccabili = badgeRepository.findBadgeSbloccati(punteggioTotale);

        for (Badge badge : badgeSbloccabili) {
            boolean giaAssegnato = utenteBadgeRepository.existsByUtenteAndBadge(utente, badge);
            if (!giaAssegnato) {
                Utente_Badge nuovoBadge = new Utente_Badge(utente, badge, LocalDateTime.now());
                utenteBadgeRepository.save(nuovoBadge);
            }
        }
    }
}

package com.example.PitchSide.controller;

import com.example.PitchSide.DTO.NuovoPronosticoDTO;
import com.example.PitchSide.DTO.PronosticoDTO;
import com.example.PitchSide.Dao.PartitaDAO;
import com.example.PitchSide.Dao.PronosticoDAO;
import com.example.PitchSide.model.Partita;
import com.example.PitchSide.model.Pronostico;
import com.example.PitchSide.model.Utente;
import com.example.PitchSide.service.PronosticoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pronostici")
public class PronosticoController {

    @Autowired
    private PronosticoDAO pronosticoRepository;

    @Autowired
    private PartitaDAO partitaRepository;

    @Autowired
    private PronosticoService pronosticoService;

    private static final int LIMITE_PRONOSTICI_GIORNO = 5;

    @PostMapping("/nuovo-pronostico")
    public ResponseEntity<PronosticoDTO> creaPronostico(
            @RequestBody NuovoPronosticoDTO nuovoPronosticoDTO,
            HttpSession session) {

        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LocalDate oggi = LocalDate.now();
        LocalDateTime inizioGiorno = oggi.atStartOfDay();
        LocalDateTime fineGiorno = oggi.plusDays(1).atStartOfDay();
        int pronosticiOggi = pronosticoRepository.conteggioPronostici(utente, inizioGiorno, fineGiorno);
        if (pronosticiOggi >= LIMITE_PRONOSTICI_GIORNO) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Partita partita = partitaRepository.findByIdPartita(nuovoPronosticoDTO.getIdPartita())
                .orElse(null);
        if (partita == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Creo pronostico
        Pronostico pronostico = new Pronostico();
        pronostico.setUtente(utente);
        pronostico.setPartita(partita);
        pronostico.setScelta(nuovoPronosticoDTO.getScelta());
        pronostico.setData_pronostico(LocalDateTime.now());

        Pronostico salvato = pronosticoRepository.save(pronostico);

        // Converto in DTO per la risposta
        PronosticoDTO dto = PronosticoDTO.toDTO(salvato);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/storico")
    public ResponseEntity<List<PronosticoDTO>> storicoPronostici(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Pronostico> pronostici = pronosticoRepository.findByUtente(utente);
        List<PronosticoDTO> dtos = pronostici.stream()
                .map(PronosticoDTO::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/partita/{idPartita}")
    public ResponseEntity<List<PronosticoDTO>> pronosticiPerPartita(@PathVariable Long idPartita) {
        Partita partita = partitaRepository.findByIdPartita(idPartita).orElse(null);
        if (partita == null) {
            return ResponseEntity.notFound().build();
        }

        List<Pronostico> pronostici = pronosticoRepository.findByPartita(partita);
        List<PronosticoDTO> dtos = pronostici.stream()
                .map(PronosticoDTO::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/aggiorna")
    public ResponseEntity<String> aggiornaPronostici() {
        pronosticoService.verificaPronosticiEaggiornaPunteggi();
        return ResponseEntity.ok("Pronostici e punteggi aggiornati correttamente.");
    }
}


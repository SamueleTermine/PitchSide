package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.PartitaDAO;
import com.example.PitchSide.model.Partita;
import com.example.PitchSide.service.ApiFootballService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class PartitaController {

    @Autowired
    private PartitaDAO partitaRepository;

    @Autowired
    ApiFootballService apiFootballService;

    @GetMapping
    public ResponseEntity<List<Partita>> getAllPartite() {
        List<Partita> partite = (List<Partita>) partitaRepository.findAll();
        return ResponseEntity.ok(partite);
    }

    @GetMapping("partita/{id_partita}")
    public ResponseEntity<Partita> getPartitaById(@PathVariable Long id_partita) {
        Optional<Partita> partitaOpt = partitaRepository.findById(id_partita);
        return partitaOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/filtra")
    public ResponseEntity<List<Partita>> getPartiteTraDate(
            @RequestParam LocalDateTime inizio,
            @RequestParam LocalDateTime fine) {
        List<Partita> partite = partitaRepository.findByDataPartitaBetween(inizio, fine);
        return ResponseEntity.ok(partite);
    }

    @GetMapping("/partite/campionato/{leagueId}/giornata/{giornata}")
    public ResponseEntity<List<Partita>> getPartiteByCampionatoAndByMatchday(@PathVariable int leagueId,  @PathVariable String giornata ) {
        List<Partita> partite = partitaRepository.findPartitaByCampionatoAndGiornata(leagueId, giornata);
        return ResponseEntity.ok(partite);
    }


}

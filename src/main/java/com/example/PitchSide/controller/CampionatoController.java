package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.CampionatoDAO;
import com.example.PitchSide.model.Campionato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CampionatoController {
    @Autowired
    private CampionatoDAO campionatoRepository;

    @GetMapping("/campionati")
    public ResponseEntity<List<Campionato>> getAllCampionati() {
        List<Campionato> campionati = (List<Campionato>) campionatoRepository.findAll();
        return ResponseEntity.ok(campionati);
    }

    @GetMapping("/{id_campionato}")
    public ResponseEntity<Campionato> getCampionatoById(@PathVariable Long id_campionato) {
        Optional<Campionato> campionato = campionatoRepository.findById(id_campionato);
        return campionato.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

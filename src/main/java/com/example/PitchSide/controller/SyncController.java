package com.example.PitchSide.controller;

import com.example.PitchSide.apiDTO.StandingDTO;
import com.example.PitchSide.apiDTO.StandingGroupDTO;
import com.example.PitchSide.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sync")
public class SyncController {

    @Autowired
    private SyncService syncService;

    // Endpoint per sincronizzare i campionati manualmente
    @PostMapping("/campionati")
    public ResponseEntity<String> syncCampionati() {
        try {
            syncService.syncCampionati();
            return ResponseEntity.ok("Sincronizzazione campionati avviata con successo.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Errore durante la sincronizzazione campionati: " + e.getMessage());
        }
    }

    // Endpoint per sincronizzare tutte le partite supportate per la stagione corrente
    @PostMapping("/fixtures")
    public ResponseEntity<String> syncFixtures() {
        try {
            int season = syncService.getCurrentSeason();
            syncService.syncAllSupportedFixtures(season);
            return ResponseEntity.ok("Sincronizzazione partite avviata per la stagione " + season + ".");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Errore durante la sincronizzazione partite: " + e.getMessage());
        }
    }

    @GetMapping("/standings")
    public ResponseEntity<?> getMainStandings(
            @RequestParam int leagueId,
            @RequestParam int season) {
        try {
            List<StandingGroupDTO> standingGroups = syncService.getStandings(leagueId, season);

            if (standingGroups == null || standingGroups.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Accedi correttamente alla lista: league → standings → primo gruppo
            List<StandingDTO> mainStandingList =
                    standingGroups.get(0).getLeague().getStandings().get(0);

            return ResponseEntity.ok(mainStandingList);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore nel recupero della classifica: " + e.getMessage());
        }
    }
}

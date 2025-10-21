package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.BadgeDAO;
import com.example.PitchSide.model.Badge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/badge")
public class BadgeController {
    @Autowired
    private BadgeDAO badgeRepository;

    @PostMapping("/insertBadgesAuto")
    public ResponseEntity<List<Badge>> insertBadgesAutomatically() {
        List<Badge> badgeList = new ArrayList<>();

        badgeList.add(new Badge("Rookie", "Hai iniziato a fare i tuoi primi pronostici, buon inizio!", 10, "/uploads/Rookie.png"));
        badgeList.add(new Badge("Debut", "Hai fatto il tuo debutto nel mondo dei pronostici, continua a puntare!", 25, "/uploads/Debut.png"));
        badgeList.add(new Badge("Starter", "Primi passi decisi: i tuoi pronostici iniziano a fare la differenza.", 50, "/uploads/Starter.png"));
        badgeList.add(new Badge("Bronze", "Sei diventato un pronosticatore affidabile, il bronzo è tuo!", 100, "/uploads/Bronze.png"));
        badgeList.add(new Badge("Silver", "I tuoi pronostici sono sempre più precisi, hai raggiunto il livello argento.", 150, "/uploads/Silver.png"));
        badgeList.add(new Badge("Gold", "Pronostici d’oro: sei tra i migliori nel prevedere le partite.", 150, "/uploads/Gold.png"));
        badgeList.add(new Badge("Prodigio", "Un vero prodigio dei pronostici, nessuno vede le partite come te.", 200, "/uploads/Prodigio.png"));
        badgeList.add(new Badge("Maestro", "Maestro delle previsioni calcistiche: il tuo intuito è imbattibile.", 275, "/uploads/Maestro.png"));
        badgeList.add(new Badge("Legend", "Sei una leggenda tra i pronosticatori, esempio per tutti gli appassionati.", 350, "/uploads/Legend.png"));
        badgeList.add(new Badge("Immortal", "Pronostici immortali: il mito del calcio predice ogni risultato.", 500, "/uploads/Immortal.png"));

        List<Badge> savedBadges = (List<Badge>) badgeRepository.saveAll(badgeList);
        return ResponseEntity.ok(savedBadges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadgeById(@PathVariable Long id) {
        Optional<Badge> badgeOpt = badgeRepository.findById(id);
        return badgeOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/sbloccabili/{punti}")
    public ResponseEntity<List<Badge>> getBadgeSbloccabili(@PathVariable int punti) {
        List<Badge> badgeList = badgeRepository.findBadgeSbloccati(punti);
        return ResponseEntity.ok(badgeList);
    }

    @GetMapping("/non-sbloccati/{punti}")
    public ResponseEntity<List<Badge>> getBadgeNonSbloccati(@PathVariable int punti) {
        List<Badge> badgeList = badgeRepository.findBadgeNonSbloccati(punti);
        return ResponseEntity.ok(badgeList);
    }
}

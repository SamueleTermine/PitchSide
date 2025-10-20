package com.example.PitchSide.controller;

import com.example.PitchSide.Dao.BadgeDAO;
import com.example.PitchSide.model.Badge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/badge")
public class BadgeController {
    @Autowired
    private BadgeDAO badgeRepository;

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

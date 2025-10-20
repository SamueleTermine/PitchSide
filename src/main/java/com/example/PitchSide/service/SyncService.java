package com.example.PitchSide.service;

import com.example.PitchSide.Dao.CampionatoDAO;
import com.example.PitchSide.Dao.PartitaDAO;
import com.example.PitchSide.apiDTO.FixtureItemDTO;
import com.example.PitchSide.apiDTO.LeagueDTO;
import com.example.PitchSide.apiDTO.LeagueItemDTO;
import com.example.PitchSide.apiDTO.StandingGroupDTO;
import com.example.PitchSide.model.Campionato;
import com.example.PitchSide.model.Partita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class SyncService {

    @Autowired
    private ApiFootballService apiFootballService;

    @Autowired
    private PronosticoService pronosticoService;

    @Autowired
    private CampionatoDAO campionatoDAO;

    @Autowired
    private PartitaDAO partitaDAO;


//    private final List<Integer> supportedLeagueIds = List.of(135, 78, 61, 140, 39, 2);

    public void syncFixtures(int leagueId, int season) {
        List<FixtureItemDTO> fixtures = apiFootballService.getFixturesByLeagueAndSeason(leagueId, season).block();
        if (fixtures == null || fixtures.isEmpty()) {
            System.out.println("No fixtures found for league " + leagueId + " season " + season);
            return;
        }


        for (FixtureItemDTO fixtureItem : fixtures) {
            int apiId = fixtureItem.getFixture().getId();

            Optional<Partita> existingOpt = partitaDAO.findByApiId(apiId);
            Partita partita = existingOpt.orElse(new Partita());

            partita.setApiId(apiId);

            String dateStr = fixtureItem.getFixture().getDate();
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
            partita.setData_partita(dateTime);


            String roundFull = fixtureItem.getLeague().getRound();
            String giornata;

            if (roundFull != null && roundFull.contains(" - ")) {
                giornata = roundFull.split(" - ")[1].trim();
            } else {
                giornata = roundFull;
            }
            partita.setGiornata(giornata);

            partita.setStato(fixtureItem.getFixture().getStatus().getShortStatus());
            partita.setGoal_casa(fixtureItem.getGoals().getHome());
            partita.setGoal_ospite(fixtureItem.getGoals().getAway());

            partita.setSquadra_casa(fixtureItem.getTeams().getHome().getName());
            partita.setSquadra_ospite(fixtureItem.getTeams().getAway().getName());

            Optional<Campionato> campionatoOpt = campionatoDAO.findByApiId(leagueId);
            Campionato campionato;
            if (campionatoOpt.isPresent()) {
                campionato = campionatoOpt.get();
            } else {
                Campionato nuovoCampionato = new Campionato();
                nuovoCampionato.setApiId(leagueId);
                nuovoCampionato.setNome_campionato("Nome sconosciuto");
                nuovoCampionato.setNazione("Nazione sconosciuta");
                campionato = campionatoDAO.save(nuovoCampionato);
            }
            partita.setCampionato(campionato);

            partitaDAO.save(partita);
        }
    }

    public void syncCampionati() {
        List<LeagueItemDTO> leagues = apiFootballService.getLeaguesFiltered().block();
        if (leagues == null) return;

        for (LeagueItemDTO leagueItem : leagues) {
            LeagueDTO league = leagueItem.getLeague();
            Optional<Campionato> existing = campionatoDAO.findByApiId(league.getId());
            Campionato campionato = existing.orElse(new Campionato());

            campionato.setApiId(league.getId());
            campionato.setNome_campionato(league.getName());
            campionato.setNazione(league.getCountry());
            campionato.setLogo(league.getLogo());

            campionatoDAO.save(campionato);
        }
    }

    public void syncAllSupportedFixtures(int season) {
        List<LeagueItemDTO> supportedLeagues = apiFootballService.getLeaguesFiltered().block();
        if (supportedLeagues == null) return;

        for (LeagueItemDTO leagueItem : supportedLeagues) {
            int leagueId = leagueItem.getLeague().getId();
            syncFixtures(leagueId, season);
        }
    }

    public List<StandingGroupDTO> getStandings(int leagueId, int season) {
        return apiFootballService.getStandings(leagueId, season).block();
    }

    public int getCurrentSeason() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        Month month = today.getMonth();

        if (month.getValue() < 7) {
            return year - 1;
        } else {
            return year;
        }
    }
    /*-------------------------------------------METODI SCHEDULATI----------------------------------------------*/

    @Scheduled(cron = "0 0 0/8 * * *") // ogni 8 ore
    public void scheduledSyncFixtures() {
        int currentSeason = getCurrentSeason();
        syncAllSupportedFixtures(currentSeason);
    }

    @Scheduled(cron = "0 0 0 * * *") // ogni giorno a mezzanotte
    public void scheduledSyncLeagues() {
        syncCampionati();
    }

    @Scheduled(cron = "0 0/30 * * * ?") // ogni 30 minuti
    public void aggiornaPartiteFinite() {
        List<Partita> partiteAttive = partitaDAO.findByStatoIn(List.of("in progress","HT", "NS"));

        for (Partita partita : partiteAttive) {
            FixtureItemDTO fixture = apiFootballService.getFixturesByLeagueAndSeason(partita.getCampionato().getApiId(), getCurrentSeason())
                    .block()
                    .stream()
                    .filter(f -> f.getFixture().getId() == partita.getApiId())
                    .findFirst()
                    .orElse(null);

            if (fixture != null) {
                String nuovoStato = fixture.getFixture().getStatus().getShortStatus();

                if ("FT".equalsIgnoreCase(nuovoStato) && !nuovoStato.equalsIgnoreCase(partita.getStato())) {
                    partita.setGoal_casa(fixture.getGoals().getHome());
                    partita.setGoal_ospite(fixture.getGoals().getAway());
                    partita.setStato(nuovoStato);
                    partitaDAO.save(partita);

                    pronosticoService.verificaPronosticiEaggiornaPunteggi(partita.getIdPartita());
                }
            }
        }
    }
}

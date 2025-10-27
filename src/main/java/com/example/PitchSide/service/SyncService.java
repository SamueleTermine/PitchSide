        package com.example.PitchSide.service;

        import com.example.PitchSide.Dao.CampionatoDAO;
        import com.example.PitchSide.Dao.PartitaDAO;
        import com.example.PitchSide.Dao.PronosticoDAO;
        import com.example.PitchSide.apiDTO.FixtureItemDTO;
        import com.example.PitchSide.apiDTO.LeagueDTO;
        import com.example.PitchSide.apiDTO.LeagueItemDTO;
        import com.example.PitchSide.apiDTO.StandingGroupDTO;
        import com.example.PitchSide.model.Campionato;
        import com.example.PitchSide.model.Partita;
        import com.example.PitchSide.model.Pronostico;
        import com.example.PitchSide.model.Punteggio;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.scheduling.annotation.Scheduled;
        import org.springframework.stereotype.Service;
        import org.springframework.web.reactive.function.client.WebClientResponseException;

        import java.time.LocalDate;
        import java.time.LocalDateTime;
        import java.time.Month;
        import java.time.format.DateTimeFormatter;
        import java.util.List;
        import java.util.Map;
        import java.util.Optional;
        import java.util.stream.Collectors;

        @Service
        public class SyncService {

            @Autowired
            private ApiFootballService apiFootballService;

            @Autowired
            private PronosticoService pronosticoService;

            @Autowired
            private CampionatoDAO campionatoDAO;

            @Autowired
            private PronosticoDAO pronosticoRepository;

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

            @Scheduled(cron = "0 0/2 * * * ?") // ogni 2 minuti
            public void aggiornaPartiteFinite() {
                long startTime = System.currentTimeMillis();
                System.out.println("🚀 [aggiornaPartiteFinite] Avvio controllo partite finite: " + LocalDateTime.now());

                // 1️⃣ Recupera le partite ancora attive
                List<Partita> partiteAttive = partitaDAO.findByStatoIn(List.of("in progress", "HT", "NS"));
                if (partiteAttive.isEmpty()) {
                    System.out.println("ℹ️ Nessuna partita attiva trovata. Fine esecuzione.");
                    return;
                }

                // 2️⃣ Raggruppa per campionato per ridurre le chiamate API
                Map<Integer, List<Partita>> partitePerCampionato = partiteAttive.stream()
                        .collect(Collectors.groupingBy(p -> p.getCampionato().getApiId()));

                int campionatiElaborati = 0;
                int partiteAggiornate = 0;

                // 3️⃣ Cicla per ogni campionato
                for (Map.Entry<Integer, List<Partita>> entry : partitePerCampionato.entrySet()) {
                    Integer campionatoId = entry.getKey();
                    List<Partita> partiteDelCampionato = entry.getValue();

                    try {
                        System.out.printf("⚙️ Elaborazione campionato ID %d (%d partite)...%n", campionatoId, partiteDelCampionato.size());

                        // 4️⃣ Chiamata API per campionato
                        List<FixtureItemDTO> fixtures = apiFootballService
                                .getFixturesByLeagueAndSeason(campionatoId, getCurrentSeason())
                                .block();

                        if (fixtures == null || fixtures.isEmpty()) {
                            System.err.printf("⚠️ Nessun fixture ricevuto per campionato %d%n", campionatoId);
                            continue;
                        }

                        // 5️⃣ Confronta le partite locali con quelle dell’API
                        for (Partita partita : partiteDelCampionato) {
                            FixtureItemDTO fixture = fixtures.stream()
                                    .filter(f -> f.getFixture().getId() == partita.getApiId())
                                    .findFirst()
                                    .orElse(null);

                            if (fixture == null) continue;

                            String nuovoStato = fixture.getFixture().getStatus().getShortStatus();

                            // 6️⃣ Se la partita è appena finita (FT)
                            if ("FT".equalsIgnoreCase(nuovoStato) && !nuovoStato.equalsIgnoreCase(partita.getStato())) {
                                partita.setGoal_casa(fixture.getGoals().getHome());
                                partita.setGoal_ospite(fixture.getGoals().getAway());
                                partita.setStato(nuovoStato);
                                partitaDAO.save(partita);

                                System.out.printf("✅ Partita %d aggiornata (FT: %d-%d)%n",
                                        partita.getIdPartita(), partita.getGoal_casa(), partita.getGoal_ospite());

                                // 7️⃣ Calcolo esito reale
                                String esitoReale = pronosticoService.calcolaEsito(partita);

                                // 8️⃣ Aggiorna i pronostici relativi
                                List<Pronostico> pronostici = pronosticoRepository.findByPartita(partita);
                                for (Pronostico p : pronostici) {
                                    if (p.getEsito() == null) { // evita ricalcoli
                                        boolean corretto = esitoReale.equals(p.getScelta());
                                        int delta = corretto ? PronosticoService.PUNTI_CORRETTO : PronosticoService.PUNTI_SCONTO;

                                        p.setEsito(esitoReale);
                                        p.setPunteggio_ottenuto(delta);
                                        pronosticoRepository.save(p);

                                        Punteggio punteggio = pronosticoService.aggiornaPunteggioUtente(p.getUtente(), delta);
                                        pronosticoService.aggiornaBadgeUtente(p.getUtente(), punteggio.getPunteggio_totale());

                                        System.out.printf("📊 Pronostico %d aggiornato per utente %s → %s (%+d punti)%n",
                                                p.getId_pronostico(), p.getUtente().getNickname(), corretto ? "CORRETTO" : "ERRATO", delta);
                                    }
                                }

                                partiteAggiornate++;
                            }
                        }

                        campionatiElaborati++;

                        // 9️⃣ Pausa per evitare il rate limit
                        Thread.sleep(2000);

                    } catch (WebClientResponseException.TooManyRequests e) {
                        System.err.println("⚠️ [429] Troppi tentativi verso l’API Football.");

                        String retryAfter = e.getHeaders() != null
                                ? e.getHeaders().getFirst("Retry-After")
                                : null;

                        long attesa = (retryAfter != null ? Long.parseLong(retryAfter) : 60) * 1000;
                        System.err.printf("⏳ Attendo %d secondi prima di riprovare...%n", attesa / 1000);

                        try {
                            Thread.sleep(attesa);
                            System.err.println("✅ Riprendo l’esecuzione dopo l’attesa.");
                        } catch (InterruptedException ignored) {
                            Thread.currentThread().interrupt();
                        }

                    } catch (Exception e) {
                        System.err.printf("❌ Errore durante l’elaborazione del campionato %d: %s%n", campionatoId, e.getMessage());
                        e.printStackTrace();
                    }
                }

                long duration = (System.currentTimeMillis() - startTime) / 1000;
                System.out.printf("🏁 [aggiornaPartiteFinite] Completato in %d secondi. Campionati elaborati: %d, Partite aggiornate: %d%n",
                        duration, campionatiElaborati, partiteAggiornate);
            }

        }

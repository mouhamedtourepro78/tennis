package com.tennis.app.service.impl;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.tennis.app.domain.AvgStat;
import com.tennis.app.domain.Match;
import com.tennis.app.domain.Player;
import com.tennis.app.domain.Stat;
import com.tennis.app.domain.Tournament;
import com.tennis.app.repository.AvgStatRepository;
import com.tennis.app.repository.MatchRepository;
import com.tennis.app.repository.PlayerRepository;
import com.tennis.app.repository.StatRepository;
import com.tennis.app.repository.TournamentRepository;
import com.tennis.app.service.AvgStatService;
import com.tennis.app.service.DataSavingService;
import com.tennis.app.service.MatchService;
import com.tennis.app.service.PlayerService;
import com.tennis.app.service.StatService;
import com.tennis.app.service.TournamentService;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataSavingServiceImpl implements DataSavingService {

    private final StatService statService;
    private final MatchService matchService;
    private final MatchRepository matchRepository;
    private final StatRepository statRepository;
    private final PlayerRepository playerRepository;
    private final AvgStatService avgStatService;
    private final AvgStatRepository avgStatRepository;

    public DataSavingServiceImpl(
        final MatchRepository matchRepository,
        final MatchService matchService,
        final StatService statService,
        final StatRepository statRepository,
        final AvgStatService avgStatService,
        final AvgStatRepository avgStatRepository,
        final PlayerRepository playerRepository
    ) {
        this.statRepository = statRepository;
        this.statService = statService;
        this.matchService = matchService;
        this.matchRepository = matchRepository;
        this.avgStatService = avgStatService;
        this.avgStatRepository = avgStatRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    @Override
    public void parseCsvToMatchs(String fileName) throws IOException, CsvException, NumberFormatException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(fileName, StandardCharsets.UTF_8))
            .withCSVParser(csvParser) // custom CSV parser
            .withSkipLines(0) // skip the first line, header info
            .build();

        List<String[]> records = csvReader.readAll();

        for (String[] dataLine : records) {
            Match match = new Match();
            Player winner = new Player();
            Player loser = new Player();
            Stat winnerStat = new Stat();
            Stat loserStat = new Stat();
            Tournament tournament = new Tournament();
            AvgStat winnerAvgStat = new AvgStat();
            AvgStat loserAvgStat = new AvgStat();

            match.setId(Long.valueOf(dataLine[0]));

            tournament.setId(Long.valueOf(dataLine[1]));
            tournament.setName(dataLine[2]);
            tournament.setSurface(dataLine[3]);
            tournament.setDrawSize(Integer.valueOf(dataLine[4]));
            tournament.setLevel(dataLine[5]);
            String target = dataLine[6];
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate ld = LocalDate.parse(target, f);
            tournament.setDate(ld);

            match.setMatchNumber(Long.valueOf(dataLine[7]));

            winner.setId(Long.valueOf(dataLine[8]));

            winnerStat.setId(Long.valueOf(dataLine[0] + dataLine[8]));

            winner.setName(dataLine[11]);
            winner.setHand(dataLine[12]);

            Long winnerHeight = dataLine[13].equals("") ? 0 : Long.valueOf(dataLine[13]);
            winner.setHeight(winnerHeight);

            winner.setNationality(dataLine[14]);
            winner.setAge(Double.valueOf(dataLine[15]));

            Long winnerAces = dataLine[28].equals("") ? 0 : Long.valueOf(dataLine[28]);
            winnerStat.setAces(winnerAces);

            Long winnerDoubleFaults = dataLine[29].equals("") ? 0 : Long.valueOf(dataLine[29]);
            winnerStat.setDoubleFaults(winnerDoubleFaults);

            Long winnerServicePoints = dataLine[30].equals("") ? 0 : Long.valueOf(dataLine[30]);
            winnerStat.setServicePoints(winnerServicePoints);

            winnerStat.setFirstServeIn(dataLine[31].equals("") ? 0 : Long.valueOf(dataLine[31]));

            winnerStat.setFirstServeWon(dataLine[32].equals("") ? 0 : Long.valueOf(dataLine[32]));

            winnerStat.setSecondServeWon(dataLine[33].equals("") ? 0 : Long.valueOf(dataLine[33]));

            winnerStat.setServiceGames(dataLine[34].equals("") ? 0 : Long.valueOf(dataLine[34]));

            winnerStat.setSavedBreakPoints(dataLine[35].equals("") ? 0 : Long.valueOf(dataLine[35]));

            winnerStat.setFacedBreakPoints(dataLine[36].equals("") ? 0 : Long.valueOf(dataLine[36]));

            loser.setId(Long.valueOf(dataLine[16]));

            loserStat.setId(Long.valueOf(dataLine[0] + dataLine[16]));

            loser.setName(dataLine[19]);
            loser.setHand(dataLine[20]);

            Long loserHeight = dataLine[21].equals("") ? 0 : Long.valueOf(dataLine[21]);
            loser.setHeight(loserHeight);

            loser.setNationality(dataLine[22]);

            loser.setAge(dataLine[23].equals("") ? 0 : Double.valueOf(dataLine[23]));

            if (playerRepository.findById(winner.getId()) == null) loser.setWonMatchs(
                matchService.findAllWonMatchsByPlayerId(loser.getId())
            );
            if (playerRepository.findById(loser.getId()) == null) loser.setLostMatchs(
                matchService.findAllLostMatchsByPlayerId(loser.getId())
            );

            Long loserAces = dataLine[37].equals("") ? 0 : Long.valueOf(dataLine[37]);
            loserStat.setAces(loserAces);

            Long loserDoubleFaults = dataLine[38].equals("") ? 0 : Long.valueOf(dataLine[38]);
            loserStat.setDoubleFaults(loserDoubleFaults);

            Long loserServicePoints = dataLine[39].equals("") ? 0 : Long.valueOf(dataLine[39]);
            loserStat.setServicePoints(loserServicePoints);

            loserStat.setFirstServeIn(dataLine[40].equals("") ? 0 : Long.valueOf(dataLine[40]));

            loserStat.setFirstServeWon(dataLine[41].equals("") ? 0 : Long.valueOf(dataLine[41]));

            loserStat.setSecondServeWon(dataLine[42].equals("") ? 0 : Long.valueOf(dataLine[42]));

            loserStat.setServiceGames(dataLine[43].equals("") ? 0 : Long.valueOf(dataLine[43]));

            loserStat.setSavedBreakPoints(dataLine[44].equals("") ? 0 : Long.valueOf(dataLine[44]));

            loserStat.setFacedBreakPoints(dataLine[45].equals("") ? 0 : Long.valueOf(dataLine[45]));

            match.setWinner(winner);
            match.setLoser(loser);

            match.setTournament(tournament);

            match.setScore(dataLine[24]);
            match.setBestOf(Integer.valueOf(dataLine[25]));
            match.setMatchRound(dataLine[26]);
            match.setMinutes(dataLine[27].equals("") ? 0 : Long.valueOf(dataLine[27]));

            if (playerRepository.findById(winner.getId()) == null) winner.setStats(statService.findPlayerStatsByPlayerId(winner.getId()));

            if (playerRepository.findById(loser.getId()) == null) loser.setStats(statService.findPlayerStatsByPlayerId(loser.getId()));

            winnerStat.setPlayer(winner);
            loserStat.setPlayer(loser);

            statRepository.save(winnerStat);
            statRepository.save(loserStat);

            winnerAvgStat = avgStatService.computeAvgStatsByPlayer(winner);

            loserAvgStat = avgStatService.computeAvgStatsByPlayer(loser);

            winnerAvgStat.setPlayer(winner);
            loserAvgStat.setPlayer(loser);

            winner.setAvgStats(winnerAvgStat);
            loser.setAvgStats(loserAvgStat);

            avgStatRepository.save(winnerAvgStat);
            avgStatRepository.save(loserAvgStat);

            matchRepository.save(match);
        }
    }
}

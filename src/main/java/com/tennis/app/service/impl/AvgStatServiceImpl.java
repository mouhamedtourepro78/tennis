package com.tennis.app.service.impl;

import com.tennis.app.domain.AvgStat;
import com.tennis.app.domain.Player;
import com.tennis.app.domain.Stat;
import com.tennis.app.repository.AvgStatRepository;
import com.tennis.app.service.AvgStatService;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AvgStat}.
 */
@Service
@Transactional
public class AvgStatServiceImpl implements AvgStatService {

    private final Logger log = LoggerFactory.getLogger(AvgStatServiceImpl.class);

    private final AvgStatRepository avgStatRepository;

    public AvgStatServiceImpl(AvgStatRepository avgStatRepository) {
        this.avgStatRepository = avgStatRepository;
    }

    @Override
    public AvgStat save(AvgStat avgStat) {
        log.debug("Request to save AvgStat : {}", avgStat);
        return avgStatRepository.save(avgStat);
    }

    @Override
    public AvgStat update(AvgStat avgStat) {
        log.debug("Request to update AvgStat : {}", avgStat);
        return avgStatRepository.save(avgStat);
    }

    @Override
    public Optional<AvgStat> partialUpdate(AvgStat avgStat) {
        log.debug("Request to partially update AvgStat : {}", avgStat);

        return avgStatRepository
            .findById(avgStat.getId())
            .map(existingAvgStat -> {
                existingAvgStat.setAvgAces(avgStat.getAvgAces());

                existingAvgStat.setAvgDoubleFaults(avgStat.getAvgDoubleFaults());

                existingAvgStat.setAvgServicePoints(avgStat.getAvgServicePoints());

                existingAvgStat.setAvgFirstServeIn(avgStat.getAvgFirstServeIn());

                existingAvgStat.setAvgFirstServeWon(avgStat.getAvgFirstServeWon());

                existingAvgStat.setAvgSecondServeWon(avgStat.getAvgSecondServeWon());

                existingAvgStat.setAvgServiceGames(avgStat.getAvgServiceGames());

                existingAvgStat.setAvgSavedBreakPoints(avgStat.getAvgSavedBreakPoints());

                existingAvgStat.setAvgFacedBreakPoints(avgStat.getAvgFacedBreakPoints());

                existingAvgStat.setPlayer(avgStat.getPlayer());
                return existingAvgStat;
            })
            .map(avgStatRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvgStat> findAll(Pageable pageable) {
        log.debug("Request to get all AvgStats");
        return avgStatRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AvgStat> findOne(Long id) {
        log.debug("Request to get AvgStat : {}", id);
        return avgStatRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AvgStat : {}", id);
        avgStatRepository.deleteById(id);
    }

    private double computeAverage(List<Long> marks) {
        DoubleSummaryStatistics iss = marks.stream().mapToDouble(a -> a).summaryStatistics();

        return (double) Math.round(iss.getAverage() * 10) / 10;
    }

    private double calculateAverage(List<Long> marks) {
        Long sum = 0L;
        int k = 0;
        for (Long l : marks) {
            sum += l;
            if (l == 0) {
                k++;
            }
        }
        if (marks.isEmpty()) {
            return 0;
        } else {
            double t = marks.size() - k;
            return (double) Math.round((double) (sum / t) * 10) / 10;
        }
    }

    @Override
    public AvgStat computeAvgStatsByPlayer(Player player) {
        AvgStat avgStat = new AvgStat();
        Set<Stat> stats = player.getStats();

        List<Long> acesList = new ArrayList<>();
        List<Long> doubleFaultsList = new ArrayList<>();
        List<Long> servicePointsList = new ArrayList<>();
        List<Long> firstServeInList = new ArrayList<>();
        List<Long> firstServeWonList = new ArrayList<>();
        List<Long> secondServeWonList = new ArrayList<>();
        List<Long> serviceGamesList = new ArrayList<>();
        List<Long> savedBreakPointsList = new ArrayList<>();
        List<Long> facedBreakPointsList = new ArrayList<>();

        for (Stat stat : stats) {
            acesList.add(stat.getAces());
            doubleFaultsList.add(stat.getDoubleFaults());
            servicePointsList.add(stat.getServicePoints());
            firstServeInList.add(stat.getFirstServeIn());
            firstServeWonList.add(stat.getFirstServeWon());
            secondServeWonList.add(stat.getFirstServeWon());
            serviceGamesList.add(stat.getServiceGames());
            savedBreakPointsList.add(stat.getSavedBreakPoints());
            facedBreakPointsList.add(stat.getFacedBreakPoints());
        }

        double avgAces = calculateAverage(acesList);
        double avgDoubleFaults = calculateAverage(doubleFaultsList);
        double avgServicePoints = calculateAverage(servicePointsList);
        double avgFirstServeIn = calculateAverage(firstServeInList);
        double avgFirstServeWon = calculateAverage(firstServeWonList);
        double avgSecondServeWon = calculateAverage(secondServeWonList);
        double avgServiceGames = calculateAverage(serviceGamesList);
        double avgSavedBreakPoints = calculateAverage(savedBreakPointsList);
        double avgFacedBreakPoints = calculateAverage(facedBreakPointsList);

        avgStat.setId(player.getId());
        avgStat.setAvgAces(avgAces);
        avgStat.setAvgDoubleFaults(avgDoubleFaults);
        avgStat.setAvgServicePoints(avgServicePoints);
        avgStat.setAvgFirstServeIn(avgFirstServeIn);
        avgStat.setAvgFirstServeWon(avgFirstServeWon);
        avgStat.setAvgSecondServeWon(avgSecondServeWon);
        avgStat.setAvgServiceGames(avgServiceGames);
        avgStat.setAvgSavedBreakPoints(avgSavedBreakPoints);
        avgStat.setAvgFacedBreakPoints(avgFacedBreakPoints);
        avgStat.setPlayer(player);

        return avgStat;
    }
}

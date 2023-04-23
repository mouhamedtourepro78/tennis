package com.tennis.app.service.impl;

import com.tennis.app.domain.AvgStat;
import com.tennis.app.repository.AvgStatRepository;
import com.tennis.app.service.AvgStatService;
import java.util.Optional;
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
                if (avgStat.getAvgAces() != null) {
                    existingAvgStat.setAvgAces(avgStat.getAvgAces());
                }
                if (avgStat.getAvgDoubleFaults() != null) {
                    existingAvgStat.setAvgDoubleFaults(avgStat.getAvgDoubleFaults());
                }
                if (avgStat.getAvgServicePoints() != null) {
                    existingAvgStat.setAvgServicePoints(avgStat.getAvgServicePoints());
                }
                if (avgStat.getAvgFirstServeIn() != null) {
                    existingAvgStat.setAvgFirstServeIn(avgStat.getAvgFirstServeIn());
                }
                if (avgStat.getAvgFirstServeWon() != null) {
                    existingAvgStat.setAvgFirstServeWon(avgStat.getAvgFirstServeWon());
                }
                if (avgStat.getAvgSecondServeWon() != null) {
                    existingAvgStat.setAvgSecondServeWon(avgStat.getAvgSecondServeWon());
                }
                if (avgStat.getAvgServiceGames() != null) {
                    existingAvgStat.setAvgServiceGames(avgStat.getAvgServiceGames());
                }
                if (avgStat.getAvgSavedBreakPoints() != null) {
                    existingAvgStat.setAvgSavedBreakPoints(avgStat.getAvgSavedBreakPoints());
                }
                if (avgStat.getAvgFacedBreakPoints() != null) {
                    existingAvgStat.setAvgFacedBreakPoints(avgStat.getAvgFacedBreakPoints());
                }

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
}

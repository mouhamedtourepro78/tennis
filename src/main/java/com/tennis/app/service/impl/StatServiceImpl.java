package com.tennis.app.service.impl;

import com.tennis.app.domain.Stat;
import com.tennis.app.repository.StatRepository;
import com.tennis.app.service.StatService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Stat}.
 */
@Service
@Transactional
public class StatServiceImpl implements StatService {

    private final Logger log = LoggerFactory.getLogger(StatServiceImpl.class);

    private final StatRepository statRepository;

    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public Stat save(Stat stat) {
        log.debug("Request to save Stat : {}", stat);
        return statRepository.save(stat);
    }

    @Override
    public Stat update(Stat stat) {
        log.debug("Request to update Stat : {}", stat);
        return statRepository.save(stat);
    }

    @Override
    public Optional<Stat> partialUpdate(Stat stat) {
        log.debug("Request to partially update Stat : {}", stat);

        return statRepository
            .findById(stat.getId())
            .map(existingStat -> {
                if (stat.getAces() != null) {
                    existingStat.setAces(stat.getAces());
                }
                if (stat.getDoubleFaults() != null) {
                    existingStat.setDoubleFaults(stat.getDoubleFaults());
                }
                if (stat.getServicePoints() != null) {
                    existingStat.setServicePoints(stat.getServicePoints());
                }
                if (stat.getFirstServeIn() != null) {
                    existingStat.setFirstServeIn(stat.getFirstServeIn());
                }
                if (stat.getFirstServeWon() != null) {
                    existingStat.setFirstServeWon(stat.getFirstServeWon());
                }
                if (stat.getSecondServeWon() != null) {
                    existingStat.setSecondServeWon(stat.getSecondServeWon());
                }
                if (stat.getServiceGames() != null) {
                    existingStat.setServiceGames(stat.getServiceGames());
                }
                if (stat.getSavedBreakPoints() != null) {
                    existingStat.setSavedBreakPoints(stat.getSavedBreakPoints());
                }
                if (stat.getFacedBreakPoints() != null) {
                    existingStat.setFacedBreakPoints(stat.getFacedBreakPoints());
                }

                return existingStat;
            })
            .map(statRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Stat> findAll(Pageable pageable) {
        log.debug("Request to get all Stats");
        return statRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Stat> findOne(Long id) {
        log.debug("Request to get Stat : {}", id);
        return statRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stat : {}", id);
        statRepository.deleteById(id);
    }
}

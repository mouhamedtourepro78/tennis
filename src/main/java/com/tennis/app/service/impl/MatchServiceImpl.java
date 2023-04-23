package com.tennis.app.service.impl;

import com.tennis.app.domain.Match;
import com.tennis.app.repository.MatchRepository;
import com.tennis.app.service.MatchService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Match}.
 */
@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    private final Logger log = LoggerFactory.getLogger(MatchServiceImpl.class);

    private final MatchRepository matchRepository;

    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public Match save(Match match) {
        log.debug("Request to save Match : {}", match);
        return matchRepository.save(match);
    }

    @Override
    public Match update(Match match) {
        log.debug("Request to update Match : {}", match);
        return matchRepository.save(match);
    }

    @Override
    public Optional<Match> partialUpdate(Match match) {
        log.debug("Request to partially update Match : {}", match);

        return matchRepository
            .findById(match.getId())
            .map(existingMatch -> {
                if (match.getMatchNumber() != null) {
                    existingMatch.setMatchNumber(match.getMatchNumber());
                }
                if (match.getScore() != null) {
                    existingMatch.setScore(match.getScore());
                }
                if (match.getBestOf() != null) {
                    existingMatch.setBestOf(match.getBestOf());
                }
                if (match.getMatchRound() != null) {
                    existingMatch.setMatchRound(match.getMatchRound());
                }
                if (match.getMinutes() != null) {
                    existingMatch.setMinutes(match.getMinutes());
                }

                return existingMatch;
            })
            .map(matchRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Match> findAll(Pageable pageable) {
        log.debug("Request to get all Matches");
        return matchRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Match> findOne(Long id) {
        log.debug("Request to get Match : {}", id);
        return matchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Match : {}", id);
        matchRepository.deleteById(id);
    }
}

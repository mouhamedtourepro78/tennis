package com.tennis.app.service.impl;

import com.tennis.app.domain.Tournament;
import com.tennis.app.repository.TournamentRepository;
import com.tennis.app.service.TournamentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tournament}.
 */
@Service
@Transactional
public class TournamentServiceImpl implements TournamentService {

    private final Logger log = LoggerFactory.getLogger(TournamentServiceImpl.class);

    private final TournamentRepository tournamentRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public Tournament save(Tournament tournament) {
        log.debug("Request to save Tournament : {}", tournament);
        return tournamentRepository.save(tournament);
    }

    @Override
    public Tournament update(Tournament tournament) {
        log.debug("Request to update Tournament : {}", tournament);
        return tournamentRepository.save(tournament);
    }

    @Override
    public Optional<Tournament> partialUpdate(Tournament tournament) {
        log.debug("Request to partially update Tournament : {}", tournament);

        return tournamentRepository
            .findById(tournament.getId())
            .map(existingTournament -> {
                if (tournament.getName() != null) {
                    existingTournament.setName(tournament.getName());
                }
                if (tournament.getSurface() != null) {
                    existingTournament.setSurface(tournament.getSurface());
                }
                if (tournament.getDrawSize() != null) {
                    existingTournament.setDrawSize(tournament.getDrawSize());
                }
                if (tournament.getLevel() != null) {
                    existingTournament.setLevel(tournament.getLevel());
                }
                if (tournament.getDate() != null) {
                    existingTournament.setDate(tournament.getDate());
                }

                return existingTournament;
            })
            .map(tournamentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tournament> findAll() {
        log.debug("Request to get all Tournaments");
        return tournamentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tournament> findOne(Long id) {
        log.debug("Request to get Tournament : {}", id);
        return tournamentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tournament : {}", id);
        tournamentRepository.deleteById(id);
    }
}

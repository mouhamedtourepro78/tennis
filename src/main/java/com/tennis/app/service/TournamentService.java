package com.tennis.app.service;

import com.tennis.app.domain.Tournament;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tournament}.
 */
public interface TournamentService {
    /**
     * Save a tournament.
     *
     * @param tournament the entity to save.
     * @return the persisted entity.
     */
    Tournament save(Tournament tournament);

    /**
     * Updates a tournament.
     *
     * @param tournament the entity to update.
     * @return the persisted entity.
     */
    Tournament update(Tournament tournament);

    /**
     * Partially updates a tournament.
     *
     * @param tournament the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tournament> partialUpdate(Tournament tournament);

    /**
     * Get all the tournaments.
     *
     * @return the list of entities.
     */
    List<Tournament> findAll();

    /**
     * Get the "id" tournament.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tournament> findOne(Long id);

    /**
     * Delete the "id" tournament.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package com.tennis.app.service;

import com.tennis.app.domain.Stat;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Stat}.
 */
public interface StatService {
    /**
     * Save a stat.
     *
     * @param stat the entity to save.
     * @return the persisted entity.
     */
    Stat save(Stat stat);

    /**
     * Updates a stat.
     *
     * @param stat the entity to update.
     * @return the persisted entity.
     */
    Stat update(Stat stat);

    /**
     * Partially updates a stat.
     *
     * @param stat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Stat> partialUpdate(Stat stat);

    /**
     * Get all the stats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Stat> findAll(Pageable pageable);

    /**
     * Get the "id" stat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Stat> findOne(Long id);

    /**
     * Delete the "id" stat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

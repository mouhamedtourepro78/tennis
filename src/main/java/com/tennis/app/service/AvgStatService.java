package com.tennis.app.service;

import com.tennis.app.domain.AvgStat;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AvgStat}.
 */
public interface AvgStatService {
    /**
     * Save a avgStat.
     *
     * @param avgStat the entity to save.
     * @return the persisted entity.
     */
    AvgStat save(AvgStat avgStat);

    /**
     * Updates a avgStat.
     *
     * @param avgStat the entity to update.
     * @return the persisted entity.
     */
    AvgStat update(AvgStat avgStat);

    /**
     * Partially updates a avgStat.
     *
     * @param avgStat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AvgStat> partialUpdate(AvgStat avgStat);

    /**
     * Get all the avgStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvgStat> findAll(Pageable pageable);

    /**
     * Get the "id" avgStat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvgStat> findOne(Long id);

    /**
     * Delete the "id" avgStat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

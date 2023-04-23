package com.tennis.app.service;

import com.tennis.app.domain.Player;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Player}.
 */
public interface PlayerService {
    /**
     * Save a player.
     *
     * @param player the entity to save.
     * @return the persisted entity.
     */
    Player save(Player player);

    /**
     * Updates a player.
     *
     * @param player the entity to update.
     * @return the persisted entity.
     */
    Player update(Player player);

    /**
     * Partially updates a player.
     *
     * @param player the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Player> partialUpdate(Player player);

    /**
     * Get all the players.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Player> findAll(Pageable pageable);
    /**
     * Get all the Player where AvgStats is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Player> findAllWhereAvgStatsIsNull();

    /**
     * Get the "id" player.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Player> findOne(Long id);

    /**
     * Delete the "id" player.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

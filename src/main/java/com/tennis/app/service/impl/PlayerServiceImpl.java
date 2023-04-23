package com.tennis.app.service.impl;

import com.tennis.app.domain.Player;
import com.tennis.app.repository.PlayerRepository;
import com.tennis.app.service.PlayerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Player}.
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player save(Player player) {
        log.debug("Request to save Player : {}", player);
        return playerRepository.save(player);
    }

    @Override
    public Player update(Player player) {
        log.debug("Request to update Player : {}", player);
        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> partialUpdate(Player player) {
        log.debug("Request to partially update Player : {}", player);

        return playerRepository
            .findById(player.getId())
            .map(existingPlayer -> {
                if (player.getName() != null) {
                    existingPlayer.setName(player.getName());
                }
                if (player.getHand() != null) {
                    existingPlayer.setHand(player.getHand());
                }
                if (player.getHeight() != null) {
                    existingPlayer.setHeight(player.getHeight());
                }
                if (player.getNationality() != null) {
                    existingPlayer.setNationality(player.getNationality());
                }
                if (player.getAge() != null) {
                    existingPlayer.setAge(player.getAge());
                }

                return existingPlayer;
            })
            .map(playerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Player> findAll(Pageable pageable) {
        log.debug("Request to get all Players");
        return playerRepository.findAll(pageable);
    }

    /**
     *  Get all the players where AvgStats is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Player> findAllWhereAvgStatsIsNull() {
        log.debug("Request to get all players where AvgStats is null");
        return StreamSupport
            .stream(playerRepository.findAll().spliterator(), false)
            .filter(player -> player.getAvgStats() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Player> findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        return playerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.deleteById(id);
    }
}

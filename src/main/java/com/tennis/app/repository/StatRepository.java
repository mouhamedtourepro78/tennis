package com.tennis.app.repository;

import com.tennis.app.domain.Stat;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    @Query("select s from Stat s where s.player.id = :playerId")
    Set<Stat> findPlayerStatsByPlayerId(@Param("playerId") Long playerId);
}

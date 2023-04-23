package com.tennis.app.repository;

import com.tennis.app.domain.Match;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Match entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("select m from Match m where m.winner.id = :playerId")
    Set<Match> findAllWonMatchsByPlayerId(@Param("playerId") Long playerId);

    @Query("select m from Match m where m.loser.id = :playerId")
    Set<Match> findAllLostMatchsByPlayerId(@Param("playerId") Long playerId);
}

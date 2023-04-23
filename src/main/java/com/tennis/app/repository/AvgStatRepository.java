package com.tennis.app.repository;

import com.tennis.app.domain.AvgStat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AvgStat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvgStatRepository extends JpaRepository<AvgStat, Long> {}

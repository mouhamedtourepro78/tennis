package com.tennis.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AvgStat.
 */
@Entity
@Table(name = "avg_stat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AvgStat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "avg_aces")
    private Long avgAces;

    @Column(name = "avg_double_faults")
    private Long avgDoubleFaults;

    @Column(name = "avg_service_points")
    private Long avgServicePoints;

    @Column(name = "avg_first_serve_in")
    private Long avgFirstServeIn;

    @Column(name = "avg_first_serve_won")
    private Long avgFirstServeWon;

    @Column(name = "avg_second_serve_won")
    private Long avgSecondServeWon;

    @Column(name = "avg_service_games")
    private Long avgServiceGames;

    @Column(name = "avg_saved_break_points")
    private Long avgSavedBreakPoints;

    @Column(name = "avg_faced_break_points")
    private Long avgFacedBreakPoints;

    @JsonIgnoreProperties(value = { "avgStats", "wonMatchs", "lostMatchs", "stats" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AvgStat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAvgAces() {
        return this.avgAces;
    }

    public AvgStat avgAces(Long avgAces) {
        this.setAvgAces(avgAces);
        return this;
    }

    public void setAvgAces(Long avgAces) {
        this.avgAces = avgAces;
    }

    public Long getAvgDoubleFaults() {
        return this.avgDoubleFaults;
    }

    public AvgStat avgDoubleFaults(Long avgDoubleFaults) {
        this.setAvgDoubleFaults(avgDoubleFaults);
        return this;
    }

    public void setAvgDoubleFaults(Long avgDoubleFaults) {
        this.avgDoubleFaults = avgDoubleFaults;
    }

    public Long getAvgServicePoints() {
        return this.avgServicePoints;
    }

    public AvgStat avgServicePoints(Long avgServicePoints) {
        this.setAvgServicePoints(avgServicePoints);
        return this;
    }

    public void setAvgServicePoints(Long avgServicePoints) {
        this.avgServicePoints = avgServicePoints;
    }

    public Long getAvgFirstServeIn() {
        return this.avgFirstServeIn;
    }

    public AvgStat avgFirstServeIn(Long avgFirstServeIn) {
        this.setAvgFirstServeIn(avgFirstServeIn);
        return this;
    }

    public void setAvgFirstServeIn(Long avgFirstServeIn) {
        this.avgFirstServeIn = avgFirstServeIn;
    }

    public Long getAvgFirstServeWon() {
        return this.avgFirstServeWon;
    }

    public AvgStat avgFirstServeWon(Long avgFirstServeWon) {
        this.setAvgFirstServeWon(avgFirstServeWon);
        return this;
    }

    public void setAvgFirstServeWon(Long avgFirstServeWon) {
        this.avgFirstServeWon = avgFirstServeWon;
    }

    public Long getAvgSecondServeWon() {
        return this.avgSecondServeWon;
    }

    public AvgStat avgSecondServeWon(Long avgSecondServeWon) {
        this.setAvgSecondServeWon(avgSecondServeWon);
        return this;
    }

    public void setAvgSecondServeWon(Long avgSecondServeWon) {
        this.avgSecondServeWon = avgSecondServeWon;
    }

    public Long getAvgServiceGames() {
        return this.avgServiceGames;
    }

    public AvgStat avgServiceGames(Long avgServiceGames) {
        this.setAvgServiceGames(avgServiceGames);
        return this;
    }

    public void setAvgServiceGames(Long avgServiceGames) {
        this.avgServiceGames = avgServiceGames;
    }

    public Long getAvgSavedBreakPoints() {
        return this.avgSavedBreakPoints;
    }

    public AvgStat avgSavedBreakPoints(Long avgSavedBreakPoints) {
        this.setAvgSavedBreakPoints(avgSavedBreakPoints);
        return this;
    }

    public void setAvgSavedBreakPoints(Long avgSavedBreakPoints) {
        this.avgSavedBreakPoints = avgSavedBreakPoints;
    }

    public Long getAvgFacedBreakPoints() {
        return this.avgFacedBreakPoints;
    }

    public AvgStat avgFacedBreakPoints(Long avgFacedBreakPoints) {
        this.setAvgFacedBreakPoints(avgFacedBreakPoints);
        return this;
    }

    public void setAvgFacedBreakPoints(Long avgFacedBreakPoints) {
        this.avgFacedBreakPoints = avgFacedBreakPoints;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public AvgStat player(Player player) {
        this.setPlayer(player);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvgStat)) {
            return false;
        }
        return id != null && id.equals(((AvgStat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvgStat{" +
            "id=" + getId() +
            ", avgAces=" + getAvgAces() +
            ", avgDoubleFaults=" + getAvgDoubleFaults() +
            ", avgServicePoints=" + getAvgServicePoints() +
            ", avgFirstServeIn=" + getAvgFirstServeIn() +
            ", avgFirstServeWon=" + getAvgFirstServeWon() +
            ", avgSecondServeWon=" + getAvgSecondServeWon() +
            ", avgServiceGames=" + getAvgServiceGames() +
            ", avgSavedBreakPoints=" + getAvgSavedBreakPoints() +
            ", avgFacedBreakPoints=" + getAvgFacedBreakPoints() +
            "}";
    }
}

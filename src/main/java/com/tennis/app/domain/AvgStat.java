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

    private static final double serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "avg_aces")
    private double avgAces;

    @Column(name = "avg_double_faults")
    private double avgDoubleFaults;

    @Column(name = "avg_service_points")
    private double avgServicePoints;

    @Column(name = "avg_first_serve_in")
    private double avgFirstServeIn;

    @Column(name = "avg_first_serve_won")
    private double avgFirstServeWon;

    @Column(name = "avg_second_serve_won")
    private double avgSecondServeWon;

    @Column(name = "avg_service_games")
    private double avgServiceGames;

    @Column(name = "avg_saved_break_points")
    private double avgSavedBreakPoints;

    @Column(name = "avg_faced_break_points")
    private double avgFacedBreakPoints;

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

    public double getAvgAces() {
        return this.avgAces;
    }

    public AvgStat avgAces(double avgAces) {
        this.setAvgAces(avgAces);
        return this;
    }

    public void setAvgAces(double avgAces) {
        this.avgAces = avgAces;
    }

    public double getAvgDoubleFaults() {
        return this.avgDoubleFaults;
    }

    public AvgStat avgDoubleFaults(double avgDoubleFaults) {
        this.setAvgDoubleFaults(avgDoubleFaults);
        return this;
    }

    public void setAvgDoubleFaults(double avgDoubleFaults) {
        this.avgDoubleFaults = avgDoubleFaults;
    }

    public double getAvgServicePoints() {
        return this.avgServicePoints;
    }

    public AvgStat avgServicePoints(double avgServicePoints) {
        this.setAvgServicePoints(avgServicePoints);
        return this;
    }

    public void setAvgServicePoints(double avgServicePoints) {
        this.avgServicePoints = avgServicePoints;
    }

    public double getAvgFirstServeIn() {
        return this.avgFirstServeIn;
    }

    public AvgStat avgFirstServeIn(double avgFirstServeIn) {
        this.setAvgFirstServeIn(avgFirstServeIn);
        return this;
    }

    public void setAvgFirstServeIn(double avgFirstServeIn) {
        this.avgFirstServeIn = avgFirstServeIn;
    }

    public double getAvgFirstServeWon() {
        return this.avgFirstServeWon;
    }

    public AvgStat avgFirstServeWon(double avgFirstServeWon) {
        this.setAvgFirstServeWon(avgFirstServeWon);
        return this;
    }

    public void setAvgFirstServeWon(double avgFirstServeWon) {
        this.avgFirstServeWon = avgFirstServeWon;
    }

    public double getAvgSecondServeWon() {
        return this.avgSecondServeWon;
    }

    public AvgStat avgSecondServeWon(double avgSecondServeWon) {
        this.setAvgSecondServeWon(avgSecondServeWon);
        return this;
    }

    public void setAvgSecondServeWon(double avgSecondServeWon) {
        this.avgSecondServeWon = avgSecondServeWon;
    }

    public double getAvgServiceGames() {
        return this.avgServiceGames;
    }

    public AvgStat avgServiceGames(double avgServiceGames) {
        this.setAvgServiceGames(avgServiceGames);
        return this;
    }

    public void setAvgServiceGames(double avgServiceGames) {
        this.avgServiceGames = avgServiceGames;
    }

    public double getAvgSavedBreakPoints() {
        return this.avgSavedBreakPoints;
    }

    public AvgStat avgSavedBreakPoints(double avgSavedBreakPoints) {
        this.setAvgSavedBreakPoints(avgSavedBreakPoints);
        return this;
    }

    public void setAvgSavedBreakPoints(double avgSavedBreakPoints) {
        this.avgSavedBreakPoints = avgSavedBreakPoints;
    }

    public double getAvgFacedBreakPoints() {
        return this.avgFacedBreakPoints;
    }

    public AvgStat avgFacedBreakPoints(double avgFacedBreakPoints) {
        this.setAvgFacedBreakPoints(avgFacedBreakPoints);
        return this;
    }

    public void setAvgFacedBreakPoints(double avgFacedBreakPoints) {
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

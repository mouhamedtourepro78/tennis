package com.tennis.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Stat.
 */
@Entity
@Table(name = "stat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Stat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "aces")
    private Long aces;

    @Column(name = "double_faults")
    private Long doubleFaults;

    @Column(name = "service_points")
    private Long servicePoints;

    @Column(name = "first_serve_in")
    private Long firstServeIn;

    @Column(name = "first_serve_won")
    private Long firstServeWon;

    @Column(name = "second_serve_won")
    private Long secondServeWon;

    @Column(name = "service_games")
    private Long serviceGames;

    @Column(name = "saved_break_points")
    private Long savedBreakPoints;

    @Column(name = "faced_break_points")
    private Long facedBreakPoints;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "avgStats", "wonMatchs", "lostMatchs", "stats" }, allowSetters = true)
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Stat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAces() {
        return this.aces;
    }

    public Stat aces(Long aces) {
        this.setAces(aces);
        return this;
    }

    public void setAces(Long aces) {
        this.aces = aces;
    }

    public Long getDoubleFaults() {
        return this.doubleFaults;
    }

    public Stat doubleFaults(Long doubleFaults) {
        this.setDoubleFaults(doubleFaults);
        return this;
    }

    public void setDoubleFaults(Long doubleFaults) {
        this.doubleFaults = doubleFaults;
    }

    public Long getServicePoints() {
        return this.servicePoints;
    }

    public Stat servicePoints(Long servicePoints) {
        this.setServicePoints(servicePoints);
        return this;
    }

    public void setServicePoints(Long servicePoints) {
        this.servicePoints = servicePoints;
    }

    public Long getFirstServeIn() {
        return this.firstServeIn;
    }

    public Stat firstServeIn(Long firstServeIn) {
        this.setFirstServeIn(firstServeIn);
        return this;
    }

    public void setFirstServeIn(Long firstServeIn) {
        this.firstServeIn = firstServeIn;
    }

    public Long getFirstServeWon() {
        return this.firstServeWon;
    }

    public Stat firstServeWon(Long firstServeWon) {
        this.setFirstServeWon(firstServeWon);
        return this;
    }

    public void setFirstServeWon(Long firstServeWon) {
        this.firstServeWon = firstServeWon;
    }

    public Long getSecondServeWon() {
        return this.secondServeWon;
    }

    public Stat secondServeWon(Long secondServeWon) {
        this.setSecondServeWon(secondServeWon);
        return this;
    }

    public void setSecondServeWon(Long secondServeWon) {
        this.secondServeWon = secondServeWon;
    }

    public Long getServiceGames() {
        return this.serviceGames;
    }

    public Stat serviceGames(Long serviceGames) {
        this.setServiceGames(serviceGames);
        return this;
    }

    public void setServiceGames(Long serviceGames) {
        this.serviceGames = serviceGames;
    }

    public Long getSavedBreakPoints() {
        return this.savedBreakPoints;
    }

    public Stat savedBreakPoints(Long savedBreakPoints) {
        this.setSavedBreakPoints(savedBreakPoints);
        return this;
    }

    public void setSavedBreakPoints(Long savedBreakPoints) {
        this.savedBreakPoints = savedBreakPoints;
    }

    public Long getFacedBreakPoints() {
        return this.facedBreakPoints;
    }

    public Stat facedBreakPoints(Long facedBreakPoints) {
        this.setFacedBreakPoints(facedBreakPoints);
        return this;
    }

    public void setFacedBreakPoints(Long facedBreakPoints) {
        this.facedBreakPoints = facedBreakPoints;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stat player(Player player) {
        this.setPlayer(player);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stat)) {
            return false;
        }
        return id != null && id.equals(((Stat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stat{" +
            "id=" + getId() +
            ", aces=" + getAces() +
            ", doubleFaults=" + getDoubleFaults() +
            ", servicePoints=" + getServicePoints() +
            ", firstServeIn=" + getFirstServeIn() +
            ", firstServeWon=" + getFirstServeWon() +
            ", secondServeWon=" + getSecondServeWon() +
            ", serviceGames=" + getServiceGames() +
            ", savedBreakPoints=" + getSavedBreakPoints() +
            ", facedBreakPoints=" + getFacedBreakPoints() +
            "}";
    }
}

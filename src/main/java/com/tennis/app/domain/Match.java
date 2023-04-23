package com.tennis.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Match.
 */
@Entity
@Table(name = "match")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "match_number")
    private Long matchNumber;

    @Column(name = "score")
    private String score;

    @Column(name = "best_of")
    private Integer bestOf;

    @Column(name = "match_round")
    private String matchRound;

    @Column(name = "minutes")
    private Long minutes;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "avgStats", "wonMatchs", "lostMatchs", "stats" }, allowSetters = true)
    private Player winner;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "avgStats", "wonMatchs", "lostMatchs", "stats" }, allowSetters = true)
    private Player loser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "matchs" }, allowSetters = true)
    private Tournament tournament;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Match id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchNumber() {
        return this.matchNumber;
    }

    public Match matchNumber(Long matchNumber) {
        this.setMatchNumber(matchNumber);
        return this;
    }

    public void setMatchNumber(Long matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getScore() {
        return this.score;
    }

    public Match score(String score) {
        this.setScore(score);
        return this;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getBestOf() {
        return this.bestOf;
    }

    public Match bestOf(Integer bestOf) {
        this.setBestOf(bestOf);
        return this;
    }

    public void setBestOf(Integer bestOf) {
        this.bestOf = bestOf;
    }

    public String getMatchRound() {
        return this.matchRound;
    }

    public Match matchRound(String matchRound) {
        this.setMatchRound(matchRound);
        return this;
    }

    public void setMatchRound(String matchRound) {
        this.matchRound = matchRound;
    }

    public Long getMinutes() {
        return this.minutes;
    }

    public Match minutes(Long minutes) {
        this.setMinutes(minutes);
        return this;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public Player getWinner() {
        return this.winner;
    }

    public void setWinner(Player player) {
        this.winner = player;
    }

    public Match winner(Player player) {
        this.setWinner(player);
        return this;
    }

    public Player getLoser() {
        return this.loser;
    }

    public void setLoser(Player player) {
        this.loser = player;
    }

    public Match loser(Player player) {
        this.setLoser(player);
        return this;
    }

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Match tournament(Tournament tournament) {
        this.setTournament(tournament);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        return id != null && id.equals(((Match) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", matchNumber=" + getMatchNumber() +
            ", score='" + getScore() + "'" +
            ", bestOf=" + getBestOf() +
            ", matchRound='" + getMatchRound() + "'" +
            ", minutes=" + getMinutes() +
            "}";
    }
}

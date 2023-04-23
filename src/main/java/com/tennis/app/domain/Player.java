package com.tennis.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hand")
    private String hand;

    @Column(name = "height")
    private Long height;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "age")
    private Double age;

    @JsonIgnoreProperties(value = { "player" }, allowSetters = true)
    @OneToOne(mappedBy = "player")
    private AvgStat avgStats;

    @OneToMany(mappedBy = "winner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "winner", "loser", "tournament" }, allowSetters = true)
    private Set<Match> wonMatchs = new HashSet<>();

    @OneToMany(mappedBy = "loser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "winner", "loser", "tournament" }, allowSetters = true)
    private Set<Match> lostMatchs = new HashSet<>();

    @OneToMany(mappedBy = "player")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "player" }, allowSetters = true)
    private Set<Stat> stats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Player id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Player name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHand() {
        return this.hand;
    }

    public Player hand(String hand) {
        this.setHand(hand);
        return this;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public Long getHeight() {
        return this.height;
    }

    public Player height(Long height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Player nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Double getAge() {
        return this.age;
    }

    public Player age(Double age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public AvgStat getAvgStats() {
        return this.avgStats;
    }

    public void setAvgStats(AvgStat avgStat) {
        if (this.avgStats != null) {
            this.avgStats.setPlayer(null);
        }
        if (avgStat != null) {
            avgStat.setPlayer(this);
        }
        this.avgStats = avgStat;
    }

    public Player avgStats(AvgStat avgStat) {
        this.setAvgStats(avgStat);
        return this;
    }

    public Set<Match> getWonMatchs() {
        return this.wonMatchs;
    }

    public void setWonMatchs(Set<Match> matches) {
        if (this.wonMatchs != null) {
            this.wonMatchs.forEach(i -> i.setWinner(null));
        }
        if (matches != null) {
            matches.forEach(i -> i.setWinner(this));
        }
        this.wonMatchs = matches;
    }

    public Player wonMatchs(Set<Match> matches) {
        this.setWonMatchs(matches);
        return this;
    }

    public Player addWonMatchs(Match match) {
        this.wonMatchs.add(match);
        match.setWinner(this);
        return this;
    }

    public Player removeWonMatchs(Match match) {
        this.wonMatchs.remove(match);
        match.setWinner(null);
        return this;
    }

    public Set<Match> getLostMatchs() {
        return this.lostMatchs;
    }

    public void setLostMatchs(Set<Match> matches) {
        if (this.lostMatchs != null) {
            this.lostMatchs.forEach(i -> i.setLoser(null));
        }
        if (matches != null) {
            matches.forEach(i -> i.setLoser(this));
        }
        this.lostMatchs = matches;
    }

    public Player lostMatchs(Set<Match> matches) {
        this.setLostMatchs(matches);
        return this;
    }

    public Player addLostMatchs(Match match) {
        this.lostMatchs.add(match);
        match.setLoser(this);
        return this;
    }

    public Player removeLostMatchs(Match match) {
        this.lostMatchs.remove(match);
        match.setLoser(null);
        return this;
    }

    public Set<Stat> getStats() {
        return this.stats;
    }

    public void setStats(Set<Stat> stats) {
        if (this.stats != null) {
            this.stats.forEach(i -> i.setPlayer(null));
        }
        if (stats != null) {
            stats.forEach(i -> i.setPlayer(this));
        }
        this.stats = stats;
    }

    public Player stats(Set<Stat> stats) {
        this.setStats(stats);
        return this;
    }

    public Player addStats(Stat stat) {
        this.stats.add(stat);
        stat.setPlayer(this);
        return this;
    }

    public Player removeStats(Stat stat) {
        this.stats.remove(stat);
        stat.setPlayer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hand='" + getHand() + "'" +
            ", height=" + getHeight() +
            ", nationality='" + getNationality() + "'" +
            ", age=" + getAge() +
            "}";
    }
}

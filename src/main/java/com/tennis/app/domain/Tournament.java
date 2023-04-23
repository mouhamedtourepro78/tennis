package com.tennis.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tournament.
 */
@Entity
@Table(name = "tournament")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surface")
    private String surface;

    @Column(name = "draw_size")
    private Integer drawSize;

    @Column(name = "level")
    private String level;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "tournament")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "winner", "loser", "tournament" }, allowSetters = true)
    private Set<Match> matchs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tournament id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Tournament name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurface() {
        return this.surface;
    }

    public Tournament surface(String surface) {
        this.setSurface(surface);
        return this;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public Integer getDrawSize() {
        return this.drawSize;
    }

    public Tournament drawSize(Integer drawSize) {
        this.setDrawSize(drawSize);
        return this;
    }

    public void setDrawSize(Integer drawSize) {
        this.drawSize = drawSize;
    }

    public String getLevel() {
        return this.level;
    }

    public Tournament level(String level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Tournament date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Match> getMatchs() {
        return this.matchs;
    }

    public void setMatchs(Set<Match> matches) {
        if (this.matchs != null) {
            this.matchs.forEach(i -> i.setTournament(null));
        }
        if (matches != null) {
            matches.forEach(i -> i.setTournament(this));
        }
        this.matchs = matches;
    }

    public Tournament matchs(Set<Match> matches) {
        this.setMatchs(matches);
        return this;
    }

    public Tournament addMatchs(Match match) {
        this.matchs.add(match);
        match.setTournament(this);
        return this;
    }

    public Tournament removeMatchs(Match match) {
        this.matchs.remove(match);
        match.setTournament(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tournament)) {
            return false;
        }
        return id != null && id.equals(((Tournament) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surface='" + getSurface() + "'" +
            ", drawSize=" + getDrawSize() +
            ", level='" + getLevel() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}

package com.bricola.cocovoit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Path.
 */
@Entity
@Table(name = "path")
public class Path implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "number_of_passengers", nullable = false)
    private Integer numberOfPassengers;

    @NotNull
    @Column(name = "departure_place", nullable = false)
    private String departurePlace;

    @NotNull
    @Column(name = "arrival_place", nullable = false)
    private String arrivalPlace;

    @OneToMany(mappedBy = "path")
    @JsonIgnoreProperties(value = { "member", "path" }, allowSetters = true)
    private Set<Registration> registrations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "paths", "registrations" }, allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Path id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDate() {
        return this.date;
    }

    public Path date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getNumberOfPassengers() {
        return this.numberOfPassengers;
    }

    public Path numberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
        return this;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getDeparturePlace() {
        return this.departurePlace;
    }

    public Path departurePlace(String departurePlace) {
        this.departurePlace = departurePlace;
        return this;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getArrivalPlace() {
        return this.arrivalPlace;
    }

    public Path arrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
        return this;
    }

    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public Set<Registration> getRegistrations() {
        return this.registrations;
    }

    public Path registrations(Set<Registration> registrations) {
        this.setRegistrations(registrations);
        return this;
    }

    public Path addRegistration(Registration registration) {
        this.registrations.add(registration);
        registration.setPath(this);
        return this;
    }

    public Path removeRegistration(Registration registration) {
        this.registrations.remove(registration);
        registration.setPath(null);
        return this;
    }

    public void setRegistrations(Set<Registration> registrations) {
        if (this.registrations != null) {
            this.registrations.forEach(i -> i.setPath(null));
        }
        if (registrations != null) {
            registrations.forEach(i -> i.setPath(this));
        }
        this.registrations = registrations;
    }

    public Member getMember() {
        return this.member;
    }

    public Path member(Member member) {
        this.setMember(member);
        return this;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Path)) {
            return false;
        }
        return id != null && id.equals(((Path) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Path{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", numberOfPassengers=" + getNumberOfPassengers() +
            ", departurePlace='" + getDeparturePlace() + "'" +
            ", arrivalPlace='" + getArrivalPlace() + "'" +
            "}";
    }
}

package com.bricola.cocovoit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Registration.
 */
@Entity
@Table(name = "registration")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "paths", "registrations" }, allowSetters = true)
    private Member member;

    @ManyToOne
    @JsonIgnoreProperties(value = { "registrations", "member" }, allowSetters = true)
    private Path path;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Registration id(Long id) {
        this.id = id;
        return this;
    }

    public Member getMember() {
        return this.member;
    }

    public Registration member(Member member) {
        this.setMember(member);
        return this;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Path getPath() {
        return this.path;
    }

    public Registration path(Path path) {
        this.setPath(path);
        return this;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Registration)) {
            return false;
        }
        return id != null && id.equals(((Registration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Registration{" +
            "id=" + getId() +
            "}";
    }
}

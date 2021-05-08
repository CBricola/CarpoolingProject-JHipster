package com.bricola.cocovoit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "professional_email", nullable = false)
    private String professionalEmail;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "member")
    @JsonIgnoreProperties(value = { "registrations", "member" }, allowSetters = true)
    private Set<Path> paths = new HashSet<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnoreProperties(value = { "member", "path" }, allowSetters = true)
    private Set<Registration> registrations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member id(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Member firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Member lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfessionalEmail() {
        return this.professionalEmail;
    }

    public Member professionalEmail(String professionalEmail) {
        this.professionalEmail = professionalEmail;
        return this;
    }

    public void setProfessionalEmail(String professionalEmail) {
        this.professionalEmail = professionalEmail;
    }

    public String getPhone() {
        return this.phone;
    }

    public Member phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Path> getPaths() {
        return this.paths;
    }

    public Member paths(Set<Path> paths) {
        this.setPaths(paths);
        return this;
    }

    public Member addPath(Path path) {
        this.paths.add(path);
        path.setMember(this);
        return this;
    }

    public Member removePath(Path path) {
        this.paths.remove(path);
        path.setMember(null);
        return this;
    }

    public void setPaths(Set<Path> paths) {
        if (this.paths != null) {
            this.paths.forEach(i -> i.setMember(null));
        }
        if (paths != null) {
            paths.forEach(i -> i.setMember(this));
        }
        this.paths = paths;
    }

    public Set<Registration> getRegistrations() {
        return this.registrations;
    }

    public Member registrations(Set<Registration> registrations) {
        this.setRegistrations(registrations);
        return this;
    }

    public Member addRegistration(Registration registration) {
        this.registrations.add(registration);
        registration.setMember(this);
        return this;
    }

    public Member removeRegistration(Registration registration) {
        this.registrations.remove(registration);
        registration.setMember(null);
        return this;
    }

    public void setRegistrations(Set<Registration> registrations) {
        if (this.registrations != null) {
            this.registrations.forEach(i -> i.setMember(null));
        }
        if (registrations != null) {
            registrations.forEach(i -> i.setMember(this));
        }
        this.registrations = registrations;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return id != null && id.equals(((Member) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", professionalEmail='" + getProfessionalEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}

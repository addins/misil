package org.addin.misil.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Organizer.
 */
@Entity
@Table(name = "organizer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organizer extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private People pic;

    @OneToMany(mappedBy = "organizedBy")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Seminar> seminarsOrganizeds = new HashSet<>();

    @OneToMany(mappedBy = "organizedBy")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Place> places = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Organizer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public People getPic() {
        return pic;
    }

    public Organizer pic(People people) {
        this.pic = people;
        return this;
    }

    public void setPic(People people) {
        this.pic = people;
    }

    public Set<Seminar> getSeminarsOrganizeds() {
        return seminarsOrganizeds;
    }

    public Organizer seminarsOrganizeds(Set<Seminar> seminars) {
        this.seminarsOrganizeds = seminars;
        return this;
    }

    public Organizer addSeminarsOrganized(Seminar seminar) {
        this.seminarsOrganizeds.add(seminar);
        seminar.setOrganizedBy(this);
        return this;
    }

    public Organizer removeSeminarsOrganized(Seminar seminar) {
        this.seminarsOrganizeds.remove(seminar);
        seminar.setOrganizedBy(null);
        return this;
    }

    public void setSeminarsOrganizeds(Set<Seminar> seminars) {
        this.seminarsOrganizeds = seminars;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public Organizer places(Set<Place> places) {
        this.places = places;
        return this;
    }

    public Organizer addPlaces(Place place) {
        this.places.add(place);
        place.setOrganizedBy(this);
        return this;
    }

    public Organizer removePlaces(Place place) {
        this.places.remove(place);
        place.setOrganizedBy(null);
        return this;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Organizer organizer = (Organizer) o;
        if (organizer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organizer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organizer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

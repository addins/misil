package org.addin.misil.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A People.
 */
@Entity
@Table(name = "people")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class People extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "phone")
    private String phone;

    @OneToOne(optional = true)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "presenter")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Seminar> seminarsPresenteds = new HashSet<>();

    @ManyToMany(mappedBy = "attendees")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Seminar> seminarsAttendeds = new HashSet<>();

    @ManyToMany(mappedBy = "specialGuests")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Seminar> specialGuestAts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public People externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPhone() {
        return phone;
    }

    public People phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public People user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Seminar> getSeminarsPresenteds() {
        return seminarsPresenteds;
    }

    public People seminarsPresenteds(Set<Seminar> seminars) {
        this.seminarsPresenteds = seminars;
        return this;
    }

    public People addSeminarsPresented(Seminar seminar) {
        this.seminarsPresenteds.add(seminar);
        seminar.setPresenter(this);
        return this;
    }

    public People removeSeminarsPresented(Seminar seminar) {
        this.seminarsPresenteds.remove(seminar);
        seminar.setPresenter(null);
        return this;
    }

    public void setSeminarsPresenteds(Set<Seminar> seminars) {
        this.seminarsPresenteds = seminars;
    }

    public Set<Seminar> getSeminarsAttendeds() {
        return seminarsAttendeds;
    }

    public People seminarsAttendeds(Set<Seminar> seminars) {
        this.seminarsAttendeds = seminars;
        return this;
    }

    public People addSeminarsAttended(Seminar seminar) {
        this.seminarsAttendeds.add(seminar);
        seminar.getAttendees().add(this);
        return this;
    }

    public People removeSeminarsAttended(Seminar seminar) {
        this.seminarsAttendeds.remove(seminar);
        seminar.getAttendees().remove(this);
        return this;
    }

    public void setSeminarsAttendeds(Set<Seminar> seminars) {
        this.seminarsAttendeds = seminars;
    }

    public Set<Seminar> getSpecialGuestAts() {
        return specialGuestAts;
    }

    public People specialGuestAts(Set<Seminar> seminars) {
        this.specialGuestAts = seminars;
        return this;
    }

    public People addSpecialGuestAt(Seminar seminar) {
        this.specialGuestAts.add(seminar);
        seminar.getSpecialGuests().add(this);
        return this;
    }

    public People removeSpecialGuestAt(Seminar seminar) {
        this.specialGuestAts.remove(seminar);
        seminar.getSpecialGuests().remove(this);
        return this;
    }

    public void setSpecialGuestAts(Set<Seminar> seminars) {
        this.specialGuestAts = seminars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        People people = (People) o;
        if (people.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), people.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "People{" +
            "id=" + getId() +
            ", externalId='" + getExternalId() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}

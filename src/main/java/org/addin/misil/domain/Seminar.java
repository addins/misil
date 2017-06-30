package org.addin.misil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Seminar.
 */
@Entity
@Table(name = "seminar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Seminar extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @Column(name = "canceled")
    private Boolean canceled;

    @Column(name = "published")
    private Boolean published;

    @ManyToOne
    private Place place;

    @ManyToOne
    private Organizer organizedBy;

    @ManyToOne
    private People presenter;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "seminar_attendees",
               joinColumns = @JoinColumn(name="seminars_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="attendees_id", referencedColumnName="id"))
    private Set<People> attendees = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "seminar_special_guests",
               joinColumns = @JoinColumn(name="seminars_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="special_guests_id", referencedColumnName="id"))
    private Set<People> specialGuests = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "seminar_tags",
               joinColumns = @JoinColumn(name="seminars_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Seminar title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Seminar startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public Seminar endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean isCanceled() {
        return canceled;
    }

    public Seminar canceled(Boolean canceled) {
        this.canceled = canceled;
        return this;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Boolean isPublished() {
        return published;
    }

    public Seminar published(Boolean published) {
        this.published = published;
        return this;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Place getPlace() {
        return place;
    }

    public Seminar place(Place place) {
        this.place = place;
        return this;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Organizer getOrganizedBy() {
        return organizedBy;
    }

    public Seminar organizedBy(Organizer organizer) {
        this.organizedBy = organizer;
        return this;
    }

    public void setOrganizedBy(Organizer organizer) {
        this.organizedBy = organizer;
    }

    public People getPresenter() {
        return presenter;
    }

    public Seminar presenter(People people) {
        this.presenter = people;
        return this;
    }

    public void setPresenter(People people) {
        this.presenter = people;
    }

    public Set<People> getAttendees() {
        return attendees;
    }

    public Seminar attendees(Set<People> people) {
        this.attendees = people;
        return this;
    }

    public Seminar addAttendees(People people) {
        this.attendees.add(people);
        people.getSeminarsAttendeds().add(this);
        return this;
    }

    public Seminar removeAttendees(People people) {
        this.attendees.remove(people);
        people.getSeminarsAttendeds().remove(this);
        return this;
    }

    public void setAttendees(Set<People> people) {
        this.attendees = people;
    }

    public Set<People> getSpecialGuests() {
        return specialGuests;
    }

    public Seminar specialGuests(Set<People> people) {
        this.specialGuests = people;
        return this;
    }

    public Seminar addSpecialGuests(People people) {
        this.specialGuests.add(people);
        people.getSpecialGuestAts().add(this);
        return this;
    }

    public Seminar removeSpecialGuests(People people) {
        this.specialGuests.remove(people);
        people.getSpecialGuestAts().remove(this);
        return this;
    }

    public void setSpecialGuests(Set<People> people) {
        this.specialGuests = people;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Seminar tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Seminar addTags(Tag tag) {
        this.tags.add(tag);
        tag.getSeminars().add(this);
        return this;
    }

    public Seminar removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.getSeminars().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seminar seminar = (Seminar) o;
        if (seminar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seminar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seminar{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", canceled='" + isCanceled() + "'" +
            ", published='" + isPublished() + "'" +
            "}";
    }
}

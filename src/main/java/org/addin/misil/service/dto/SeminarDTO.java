package org.addin.misil.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Seminar entity.
 */
public class SeminarDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private ZonedDateTime startTime;

    @NotNull
    private ZonedDateTime endTime;

    private Boolean canceled;

    private Boolean published;

    private Long placeId;

    private String placeName;

    private Long organizedById;

    private String organizerName;

    private Long presenterId;

    private String presenterName;

    private Set<PeopleDTO> attendees = new HashSet<>();

    private Set<PeopleDTO> specialGuests = new HashSet<>();

    private Set<TagDTO> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Boolean isPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Long getOrganizedById() {
        return organizedById;
    }

    public void setOrganizedById(Long organizerId) {
        this.organizedById = organizerId;
    }

    public Long getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(Long peopleId) {
        this.presenterId = peopleId;
    }

    public Set<PeopleDTO> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<PeopleDTO> people) {
        this.attendees = people;
    }

    public Set<PeopleDTO> getSpecialGuests() {
        return specialGuests;
    }

    public void setSpecialGuests(Set<PeopleDTO> people) {
        this.specialGuests = people;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeminarDTO seminarDTO = (SeminarDTO) o;
        if(seminarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seminarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeminarDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", canceled='" + isCanceled() + "'" +
            ", published='" + isPublished() + "'" +
            "}";
    }
}

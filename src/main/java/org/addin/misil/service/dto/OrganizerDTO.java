package org.addin.misil.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Organizer entity.
 */
public class OrganizerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long picId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long peopleId) {
        this.picId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganizerDTO organizerDTO = (OrganizerDTO) o;
        if(organizerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organizerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganizerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

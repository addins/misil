package org.addin.misil.service.mapper;

import org.addin.misil.domain.*;
import org.addin.misil.service.dto.PeopleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity People and its DTO PeopleDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface PeopleMapper extends EntityMapper <PeopleDTO, People> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    PeopleDTO toDto(People people);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "seminarsPresenteds", ignore = true)
    @Mapping(target = "seminarsAttendeds", ignore = true)
    @Mapping(target = "specialGuestAts", ignore = true)
    People toEntity(PeopleDTO peopleDTO);
    default People fromId(Long id) {
        if (id == null) {
            return null;
        }
        People people = new People();
        people.setId(id);
        return people;
    }

    default String peopleName(People people) {
        String name = "";
        if (people == null) {
            return null;
        }
        if (people.getUser() == null) {
            return null;
        }
        String firstName = people.getUser().getFirstName();
        if (firstName != null) {
            name += firstName;
        }
        String lastName = people.getUser().getLastName();
        if (lastName != null) {
            name += " " + lastName;
        }
        return name;
    }
}

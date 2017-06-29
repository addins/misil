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
}

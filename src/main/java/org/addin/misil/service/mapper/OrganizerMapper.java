package org.addin.misil.service.mapper;

import org.addin.misil.domain.*;
import org.addin.misil.service.dto.OrganizerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organizer and its DTO OrganizerDTO.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, })
public interface OrganizerMapper extends EntityMapper <OrganizerDTO, Organizer> {

    @Mapping(source = "pic.id", target = "picId")
    @Mapping(target = "picName", expression = "java(peopleMapper.peopleName(organizer.getPic()))")
    OrganizerDTO toDto(Organizer organizer);

    @Mapping(source = "picId", target = "pic")
    @Mapping(target = "seminarsOrganizeds", ignore = true)
    @Mapping(target = "places", ignore = true)
    Organizer toEntity(OrganizerDTO organizerDTO);
    default Organizer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organizer organizer = new Organizer();
        organizer.setId(id);
        return organizer;
    }
}

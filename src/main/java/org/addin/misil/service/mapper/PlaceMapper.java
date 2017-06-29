package org.addin.misil.service.mapper;

import org.addin.misil.domain.*;
import org.addin.misil.service.dto.PlaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Place and its DTO PlaceDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizerMapper.class, })
public interface PlaceMapper extends EntityMapper <PlaceDTO, Place> {

    @Mapping(source = "organizedBy.id", target = "organizedById")
    PlaceDTO toDto(Place place); 

    @Mapping(source = "organizedById", target = "organizedBy")
    @Mapping(target = "events", ignore = true)
    Place toEntity(PlaceDTO placeDTO); 
    default Place fromId(Long id) {
        if (id == null) {
            return null;
        }
        Place place = new Place();
        place.setId(id);
        return place;
    }
}

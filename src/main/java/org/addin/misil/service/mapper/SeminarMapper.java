package org.addin.misil.service.mapper;

import org.addin.misil.domain.*;
import org.addin.misil.service.dto.SeminarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seminar and its DTO SeminarDTO.
 */
@Mapper(componentModel = "spring", uses = {PlaceMapper.class, OrganizerMapper.class, PeopleMapper.class, TagMapper.class, })
public interface SeminarMapper extends EntityMapper <SeminarDTO, Seminar> {

    @Mapping(source = "place.id", target = "placeId")
    @Mapping(source = "place.name", target = "placeName")
    @Mapping(source = "organizedBy.id", target = "organizedById")
    @Mapping(source = "organizedBy.name", target = "organizerName")
    @Mapping(source = "presenter.id", target = "presenterId")
    @Mapping(target = "presenterName", expression = "java(peopleMapper.peopleName(seminar.getPresenter()))")
    SeminarDTO toDto(Seminar seminar);

    @Mapping(source = "placeId", target = "place")

    @Mapping(source = "organizedById", target = "organizedBy")

    @Mapping(source = "presenterId", target = "presenter")
    Seminar toEntity(SeminarDTO seminarDTO);
    default Seminar fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seminar seminar = new Seminar();
        seminar.setId(id);
        return seminar;
    }
}

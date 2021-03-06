package org.addin.misil.service;

import org.addin.misil.service.dto.PeopleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing People.
 */
public interface PeopleService {

    /**
     * Save a people.
     *
     * @param peopleDTO the entity to save
     * @return the persisted entity
     */
    PeopleDTO save(PeopleDTO peopleDTO);

    /**
     *  Get all the people.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PeopleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" people.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PeopleDTO findOne(Long id);

    /**
     *  Delete the "id" people.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    PeopleDTO createPeople(PeopleDTO people, Long userId);

    PeopleDTO findOneByUserId(Long userId);

    void deleteByUserId(Long userId);

    void nullingPeopleUser(Long userId);
}

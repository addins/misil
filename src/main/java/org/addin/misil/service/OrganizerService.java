package org.addin.misil.service;

import org.addin.misil.service.dto.OrganizerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Organizer.
 */
public interface OrganizerService {

    /**
     * Save a organizer.
     *
     * @param organizerDTO the entity to save
     * @return the persisted entity
     */
    OrganizerDTO save(OrganizerDTO organizerDTO);

    /**
     *  Get all the organizers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrganizerDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" organizer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrganizerDTO findOne(Long id);

    /**
     *  Delete the "id" organizer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

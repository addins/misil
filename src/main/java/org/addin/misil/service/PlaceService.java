package org.addin.misil.service;

import org.addin.misil.service.dto.PlaceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Place.
 */
public interface PlaceService {

    /**
     * Save a place.
     *
     * @param placeDTO the entity to save
     * @return the persisted entity
     */
    PlaceDTO save(PlaceDTO placeDTO);

    /**
     *  Get all the places.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PlaceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" place.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PlaceDTO findOne(Long id);

    /**
     *  Delete the "id" place.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

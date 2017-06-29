package org.addin.misil.service.impl;

import org.addin.misil.service.OrganizerService;
import org.addin.misil.domain.Organizer;
import org.addin.misil.repository.OrganizerRepository;
import org.addin.misil.service.dto.OrganizerDTO;
import org.addin.misil.service.mapper.OrganizerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Organizer.
 */
@Service
@Transactional
public class OrganizerServiceImpl implements OrganizerService{

    private final Logger log = LoggerFactory.getLogger(OrganizerServiceImpl.class);

    private final OrganizerRepository organizerRepository;

    private final OrganizerMapper organizerMapper;

    public OrganizerServiceImpl(OrganizerRepository organizerRepository, OrganizerMapper organizerMapper) {
        this.organizerRepository = organizerRepository;
        this.organizerMapper = organizerMapper;
    }

    /**
     * Save a organizer.
     *
     * @param organizerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganizerDTO save(OrganizerDTO organizerDTO) {
        log.debug("Request to save Organizer : {}", organizerDTO);
        Organizer organizer = organizerMapper.toEntity(organizerDTO);
        organizer = organizerRepository.save(organizer);
        return organizerMapper.toDto(organizer);
    }

    /**
     *  Get all the organizers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrganizerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organizers");
        return organizerRepository.findAll(pageable)
            .map(organizerMapper::toDto);
    }

    /**
     *  Get one organizer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizerDTO findOne(Long id) {
        log.debug("Request to get Organizer : {}", id);
        Organizer organizer = organizerRepository.findOne(id);
        return organizerMapper.toDto(organizer);
    }

    /**
     *  Delete the  organizer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organizer : {}", id);
        organizerRepository.delete(id);
    }
}

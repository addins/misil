package org.addin.misil.service.impl;

import org.addin.misil.domain.People;
import org.addin.misil.domain.User;
import org.addin.misil.repository.PeopleRepository;
import org.addin.misil.service.PeopleService;
import org.addin.misil.service.dto.PeopleDTO;
import org.addin.misil.service.mapper.PeopleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing People.
 */
@Service
@Transactional
public class PeopleServiceImpl implements PeopleService{

    private final Logger log = LoggerFactory.getLogger(PeopleServiceImpl.class);

    private final PeopleRepository peopleRepository;

    private final PeopleMapper peopleMapper;

    public PeopleServiceImpl(PeopleRepository peopleRepository, PeopleMapper peopleMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
    }

    /**
     * Save a people.
     *
     * @param peopleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PeopleDTO save(PeopleDTO peopleDTO) {
        log.debug("Request to save People : {}", peopleDTO);
        People people = peopleMapper.toEntity(peopleDTO);
        people = peopleRepository.save(people);
        return peopleMapper.toDto(people);
    }

    /**
     *  Get all the people.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PeopleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all People");
        return peopleRepository.findAll(pageable)
            .map(peopleMapper::toDto);
    }

    /**
     *  Get one people by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PeopleDTO findOne(Long id) {
        log.debug("Request to get People : {}", id);
        People people = peopleRepository.findOne(id);
        return peopleMapper.toDto(people);
    }

    /**
     *  Delete the  people by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete People : {}", id);
        peopleRepository.delete(id);
    }

    @Override
    public PeopleDTO createPeople(PeopleDTO people, Long userId) {
        User findUser = new User();
        findUser.setId(userId);
        Optional<People> existPeople = peopleRepository.findOneByUser(findUser);
        if (existPeople.isPresent()) {
            throw new IllegalArgumentException("People for userId " + userId + " already exist");
        }
        People entity = peopleMapper.toEntity(people);
        entity.setUser(new User());
        entity.getUser().setId(userId);
        peopleRepository.save(entity);
        log.debug("Created Information for People: {}", entity);
        return peopleMapper.toDto(entity);
    }

    @Override
    public PeopleDTO findOneByUserId(Long userId) {
        log.debug("Request to get People by userId {}", userId);
        User user = new User();
        user.setId(userId);
        Optional<People> oneByUser = peopleRepository.findOneByUser(user);
        if (oneByUser.isPresent()) {
            return peopleMapper.toDto(oneByUser.get());
        }
        return null;
    }

    @Override
    public void deleteByUserId(Long userId) {
        log.debug("Request to delete People by userId {}", userId);
        User user = new User();
        user.setId(userId);
        Optional<People> oneByUser = peopleRepository.findOneByUser(user);
        oneByUser.ifPresent(p -> {
                peopleRepository.delete(p.getId());
            }
        );
    }

    @Override
    public void nullingPeopleUser(Long userId) {
        log.debug("Request to set null user to People by userId {}", userId);
        User user = new User();
        user.setId(userId);
        Optional<People> oneByUser = peopleRepository.findOneByUser(user);
        oneByUser.ifPresent(p -> {
            p.setUser(null);
            peopleRepository.save(p);
        });
    }
}

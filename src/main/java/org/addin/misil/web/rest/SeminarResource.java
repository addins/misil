package org.addin.misil.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.addin.misil.domain.Seminar;

import org.addin.misil.repository.SeminarRepository;
import org.addin.misil.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Seminar.
 */
@RestController
@RequestMapping("/api")
public class SeminarResource {

    private final Logger log = LoggerFactory.getLogger(SeminarResource.class);

    private static final String ENTITY_NAME = "seminar";

    private final SeminarRepository seminarRepository;

    public SeminarResource(SeminarRepository seminarRepository) {
        this.seminarRepository = seminarRepository;
    }

    /**
     * POST  /seminars : Create a new seminar.
     *
     * @param seminar the seminar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seminar, or with status 400 (Bad Request) if the seminar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seminars")
    @Timed
    public ResponseEntity<Seminar> createSeminar(@Valid @RequestBody Seminar seminar) throws URISyntaxException {
        log.debug("REST request to save Seminar : {}", seminar);
        if (seminar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new seminar cannot already have an ID")).body(null);
        }
        Seminar result = seminarRepository.save(seminar);
        return ResponseEntity.created(new URI("/api/seminars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seminars : Updates an existing seminar.
     *
     * @param seminar the seminar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seminar,
     * or with status 400 (Bad Request) if the seminar is not valid,
     * or with status 500 (Internal Server Error) if the seminar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seminars")
    @Timed
    public ResponseEntity<Seminar> updateSeminar(@Valid @RequestBody Seminar seminar) throws URISyntaxException {
        log.debug("REST request to update Seminar : {}", seminar);
        if (seminar.getId() == null) {
            return createSeminar(seminar);
        }
        Seminar result = seminarRepository.save(seminar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seminar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seminars : get all the seminars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seminars in body
     */
    @GetMapping("/seminars")
    @Timed
    public List<Seminar> getAllSeminars() {
        log.debug("REST request to get all Seminars");
        return seminarRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /seminars/:id : get the "id" seminar.
     *
     * @param id the id of the seminar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seminar, or with status 404 (Not Found)
     */
    @GetMapping("/seminars/{id}")
    @Timed
    public ResponseEntity<Seminar> getSeminar(@PathVariable Long id) {
        log.debug("REST request to get Seminar : {}", id);
        Seminar seminar = seminarRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seminar));
    }

    /**
     * DELETE  /seminars/:id : delete the "id" seminar.
     *
     * @param id the id of the seminar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seminars/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeminar(@PathVariable Long id) {
        log.debug("REST request to delete Seminar : {}", id);
        seminarRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

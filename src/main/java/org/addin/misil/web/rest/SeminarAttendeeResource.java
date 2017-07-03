package org.addin.misil.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.addin.misil.service.SeminarService;
import org.addin.misil.service.dto.PeopleDTO;
import org.addin.misil.service.dto.SeminarDTO;
import org.addin.misil.web.rest.errors.CustomParameterizedException;
import org.addin.misil.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing attendees of a seminar.
 */
@RestController
@RequestMapping("/api/seminars/{seminarId}")
public class SeminarAttendeeResource {

    private final Logger log = LoggerFactory.getLogger(SeminarAttendeeResource.class);

    private static final String ENTITY_NAME = "seminar";

    private final SeminarService seminarService;

    public SeminarAttendeeResource(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    /**
     * PUT /seminars/{seminarId}/attendees : Join people to a seminar
     *
     * @param seminarId the seminar id the people to add to.
     * @return the ResponseEntity with status 200 (OK) and with body the updated seminarDTO,
     * or with status 400 (bad request) if the seminarId is not valid or the people is not exist,
     * or with status 500 (Internal Server Error) if the seminar couldn't be updated
     */
    @PutMapping("/attendees")
    @Timed
    public ResponseEntity<SeminarDTO> addAttendee(@PathVariable("seminarId") Long seminarId, @Valid @RequestBody PeopleDTO people) {
        log.debug("REST request to add people {} to seminar {}", people.getId(), seminarId);
        SeminarDTO seminar = seminarService.findOne(seminarId);
        if (seminar == null) {
            throw new CustomParameterizedException("invalid seminarId");
        }
        seminar.getAttendees().add(people);
        try {
            SeminarDTO result = seminarService.save(seminar);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seminarId.toString()))
                .body(result);
        } catch (JpaObjectRetrievalFailureException e) {
            throw new CustomParameterizedException(e.getRootCause().getMessage());
        }
    }

    /**
     * DELETE /seminars/{seminarId}/attendees/{peopleId} : Remove people from a seminar
     *
     * @param seminarId the id of the seminar to remove people from
     * @param peopleId the id of the people to be removed from a seminar
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendees/{peopleId}")
    @Timed
    public ResponseEntity<Void> removeAttendee(@PathVariable("seminarId") Long seminarId, @PathVariable("peopleId") Long peopleId) {
        log.debug("REST request to remove people {} from seminar {}", peopleId, seminarId);
        SeminarDTO seminar = seminarService.findOne(seminarId);
        if (seminar == null) {
            throw new CustomParameterizedException("invalid seminarId");
        }
        Set<PeopleDTO> peoples = seminar.getAttendees().stream().filter(p -> !p.getId().equals(peopleId))
            .collect(Collectors.toSet());
        seminar.setAttendees(peoples);
        seminarService.save(seminar);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("seminar.remove.people", peopleId.toString())).build();
    }
}

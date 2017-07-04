package org.addin.misil.web.rest;

import org.addin.misil.MisilApp;
import org.addin.misil.domain.People;
import org.addin.misil.domain.Seminar;
import org.addin.misil.repository.PeopleRepository;
import org.addin.misil.repository.SeminarRepository;
import org.addin.misil.service.SeminarService;
import org.addin.misil.service.mapper.SeminarMapper;
import org.addin.misil.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the SeminarAttendeeResource REST controller.
 *
 * @see SeminarAttendeeResource
 * Created by addin on 7/4/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MisilApp.class)
public class SeminarAttendeeResourceIntTest {

    @Autowired
    private SeminarRepository seminarRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeminarAttendeeMockMvc;

    private Seminar seminar;

    private People people;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeminarAttendeeResource seminarAttendeeResource = new SeminarAttendeeResource(seminarService);
        this.restSeminarAttendeeMockMvc = MockMvcBuilders.standaloneSetup(seminarAttendeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Before
    public void initTest() {
        seminar = SeminarResourceIntTest.createEntity(em);
        em.persist(seminar);
        em.flush();
        people = PeopleResourceIntTest.createEntity(em);
    }

    @Test
    @Transactional
    public void addAttendee() throws Exception {
        em.persist(people);
        // Validate initial seminar attendees
        Seminar one = seminarRepository.findOne(seminar.getId());
        assertThat(one.getAttendees().size()).isZero();

        restSeminarAttendeeMockMvc.perform(put("/api/seminars/" + seminar.getId() + "/attendees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people))
        ).andExpect(status().isOk());

        // Validate the attendees of the Seminar in the database
        Seminar seminar1 = seminarRepository.findOne(seminar.getId());
        assertThat(seminar1.getAttendees().size()).isEqualTo(1);
        assertThat(seminar1.getAttendees().iterator().next().getId()).isEqualTo(people.getId());
    }

    @Test
    @Transactional
    public void removeAttendee() throws Exception {
        // Set initial db
        em.persist(people);
        seminar.getAttendees().add(people);
        em.persist(seminar);

        // Validate initial seminar attendees
        Seminar one = seminarRepository.findOne(seminar.getId());
        assertThat(one.getAttendees().size()).isEqualTo(1);
        assertThat(one.getAttendees().iterator().next().getId()).isEqualTo(people.getId());

        restSeminarAttendeeMockMvc.perform(delete("/api/seminars/" + seminar.getId() + "/attendees/" + people.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());

        // Validate the attendees is deleted
        Seminar seminar1 = seminarRepository.findOne(seminar.getId());
        assertThat(seminar1.getAttendees().size()).isZero();
    }

    @Test
    @Transactional
    public void addAttendeeToNonExistSeminar() throws Exception {
        // Set initial db
        em.persist(people);

        // Validate initial seminar attendees
        Seminar one = seminarRepository.findOne(Long.MAX_VALUE);
        assertThat(one).isNull();

        restSeminarAttendeeMockMvc.perform(put("/api/seminars/" + Long.MAX_VALUE + "/attendees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void removeAttendeeFromNonExistSeminar() throws Exception {
        // Set initial db
        em.persist(people);

        // Validate initial seminar attendees
        Seminar one = seminarRepository.findOne(Long.MAX_VALUE);
        assertThat(one).isNull();

        restSeminarAttendeeMockMvc.perform(delete("/api/seminars/" + Long.MAX_VALUE + "/attendees/" + people.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void addNonExistPeopleToSeminar() throws Exception {
        // Set initial db

        // Validate initial people
        People one = peopleRepository.findOne(Long.MAX_VALUE);
        assertThat(one).isNull();
        // Validate initial seminar
        Seminar s1 = seminarRepository.findOne(seminar.getId());
        assertThat(s1.getAttendees().size()).isZero();

        people = new People();
        people.setId(Long.MAX_VALUE);
        restSeminarAttendeeMockMvc.perform(put("/api/seminars/" + seminar.getId() + "/attendees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people))
        ).andExpect(status().isBadRequest());

        // Validate the attendees of the Seminar in the database
        Seminar seminar1 = seminarRepository.findOne(seminar.getId());
        assertThat(seminar1.getAttendees().size()).isEqualTo(0);
    }
}

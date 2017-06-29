package org.addin.misil.web.rest;

import org.addin.misil.MisilApp;

import org.addin.misil.domain.Seminar;
import org.addin.misil.repository.SeminarRepository;
import org.addin.misil.service.SeminarService;
import org.addin.misil.service.dto.SeminarDTO;
import org.addin.misil.service.mapper.SeminarMapper;
import org.addin.misil.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.addin.misil.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeminarResource REST controller.
 *
 * @see SeminarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MisilApp.class)
public class SeminarResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_CANCELED = false;
    private static final Boolean UPDATED_CANCELED = true;

    private static final Boolean DEFAULT_PUBLISHED = false;
    private static final Boolean UPDATED_PUBLISHED = true;

    @Autowired
    private SeminarRepository seminarRepository;

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

    private MockMvc restSeminarMockMvc;

    private Seminar seminar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeminarResource seminarResource = new SeminarResource(seminarService);
        this.restSeminarMockMvc = MockMvcBuilders.standaloneSetup(seminarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seminar createEntity(EntityManager em) {
        Seminar seminar = new Seminar()
            .title(DEFAULT_TITLE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .canceled(DEFAULT_CANCELED)
            .published(DEFAULT_PUBLISHED);
        return seminar;
    }

    @Before
    public void initTest() {
        seminar = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeminar() throws Exception {
        int databaseSizeBeforeCreate = seminarRepository.findAll().size();

        // Create the Seminar
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);
        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isCreated());

        // Validate the Seminar in the database
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeCreate + 1);
        Seminar testSeminar = seminarList.get(seminarList.size() - 1);
        assertThat(testSeminar.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSeminar.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSeminar.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSeminar.isCanceled()).isEqualTo(DEFAULT_CANCELED);
        assertThat(testSeminar.isPublished()).isEqualTo(DEFAULT_PUBLISHED);
    }

    @Test
    @Transactional
    public void createSeminarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seminarRepository.findAll().size();

        // Create the Seminar with an existing ID
        seminar.setId(1L);
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = seminarRepository.findAll().size();
        // set the field null
        seminar.setTitle(null);

        // Create the Seminar, which fails.
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isBadRequest());

        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seminarRepository.findAll().size();
        // set the field null
        seminar.setStartTime(null);

        // Create the Seminar, which fails.
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isBadRequest());

        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seminarRepository.findAll().size();
        // set the field null
        seminar.setEndTime(null);

        // Create the Seminar, which fails.
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isBadRequest());

        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeminars() throws Exception {
        // Initialize the database
        seminarRepository.saveAndFlush(seminar);

        // Get all the seminarList
        restSeminarMockMvc.perform(get("/api/seminars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seminar.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].canceled").value(hasItem(DEFAULT_CANCELED.booleanValue())))
            .andExpect(jsonPath("$.[*].published").value(hasItem(DEFAULT_PUBLISHED.booleanValue())));
    }

    @Test
    @Transactional
    public void getSeminar() throws Exception {
        // Initialize the database
        seminarRepository.saveAndFlush(seminar);

        // Get the seminar
        restSeminarMockMvc.perform(get("/api/seminars/{id}", seminar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seminar.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.canceled").value(DEFAULT_CANCELED.booleanValue()))
            .andExpect(jsonPath("$.published").value(DEFAULT_PUBLISHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSeminar() throws Exception {
        // Get the seminar
        restSeminarMockMvc.perform(get("/api/seminars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeminar() throws Exception {
        // Initialize the database
        seminarRepository.saveAndFlush(seminar);
        int databaseSizeBeforeUpdate = seminarRepository.findAll().size();

        // Update the seminar
        Seminar updatedSeminar = seminarRepository.findOne(seminar.getId());
        updatedSeminar
            .title(UPDATED_TITLE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .canceled(UPDATED_CANCELED)
            .published(UPDATED_PUBLISHED);
        SeminarDTO seminarDTO = seminarMapper.toDto(updatedSeminar);

        restSeminarMockMvc.perform(put("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isOk());

        // Validate the Seminar in the database
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeUpdate);
        Seminar testSeminar = seminarList.get(seminarList.size() - 1);
        assertThat(testSeminar.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSeminar.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSeminar.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSeminar.isCanceled()).isEqualTo(UPDATED_CANCELED);
        assertThat(testSeminar.isPublished()).isEqualTo(UPDATED_PUBLISHED);
    }

    @Test
    @Transactional
    public void updateNonExistingSeminar() throws Exception {
        int databaseSizeBeforeUpdate = seminarRepository.findAll().size();

        // Create the Seminar
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeminarMockMvc.perform(put("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isCreated());

        // Validate the Seminar in the database
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeminar() throws Exception {
        // Initialize the database
        seminarRepository.saveAndFlush(seminar);
        int databaseSizeBeforeDelete = seminarRepository.findAll().size();

        // Get the seminar
        restSeminarMockMvc.perform(delete("/api/seminars/{id}", seminar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seminar.class);
        Seminar seminar1 = new Seminar();
        seminar1.setId(1L);
        Seminar seminar2 = new Seminar();
        seminar2.setId(seminar1.getId());
        assertThat(seminar1).isEqualTo(seminar2);
        seminar2.setId(2L);
        assertThat(seminar1).isNotEqualTo(seminar2);
        seminar1.setId(null);
        assertThat(seminar1).isNotEqualTo(seminar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeminarDTO.class);
        SeminarDTO seminarDTO1 = new SeminarDTO();
        seminarDTO1.setId(1L);
        SeminarDTO seminarDTO2 = new SeminarDTO();
        assertThat(seminarDTO1).isNotEqualTo(seminarDTO2);
        seminarDTO2.setId(seminarDTO1.getId());
        assertThat(seminarDTO1).isEqualTo(seminarDTO2);
        seminarDTO2.setId(2L);
        assertThat(seminarDTO1).isNotEqualTo(seminarDTO2);
        seminarDTO1.setId(null);
        assertThat(seminarDTO1).isNotEqualTo(seminarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seminarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seminarMapper.fromId(null)).isNull();
    }
}

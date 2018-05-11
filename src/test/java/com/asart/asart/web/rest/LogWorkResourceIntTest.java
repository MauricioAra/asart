package com.asart.asart.web.rest;

import com.asart.asart.AsartApp;

import com.asart.asart.domain.LogWork;
import com.asart.asart.repository.LogWorkRepository;
import com.asart.asart.service.LogWorkService;
import com.asart.asart.service.dto.LogWorkDTO;
import com.asart.asart.service.mapper.LogWorkMapper;
import com.asart.asart.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.asart.asart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LogWorkResource REST controller.
 *
 * @see LogWorkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsartApp.class)
public class LogWorkResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_HOUR = 1;
    private static final Integer UPDATED_HOUR = 2;

    private static final Integer DEFAULT_MINUTE = 1;
    private static final Integer UPDATED_MINUTE = 2;

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private LogWorkRepository logWorkRepository;

    @Autowired
    private LogWorkMapper logWorkMapper;

    @Autowired
    private LogWorkService logWorkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogWorkMockMvc;

    private LogWork logWork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogWorkResource logWorkResource = new LogWorkResource(logWorkService);
        this.restLogWorkMockMvc = MockMvcBuilders.standaloneSetup(logWorkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogWork createEntity(EntityManager em) {
        LogWork logWork = new LogWork()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .hour(DEFAULT_HOUR)
            .minute(DEFAULT_MINUTE)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS);
        return logWork;
    }

    @Before
    public void initTest() {
        logWork = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogWork() throws Exception {
        int databaseSizeBeforeCreate = logWorkRepository.findAll().size();

        // Create the LogWork
        LogWorkDTO logWorkDTO = logWorkMapper.toDto(logWork);
        restLogWorkMockMvc.perform(post("/api/log-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logWorkDTO)))
            .andExpect(status().isCreated());

        // Validate the LogWork in the database
        List<LogWork> logWorkList = logWorkRepository.findAll();
        assertThat(logWorkList).hasSize(databaseSizeBeforeCreate + 1);
        LogWork testLogWork = logWorkList.get(logWorkList.size() - 1);
        assertThat(testLogWork.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLogWork.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLogWork.getHour()).isEqualTo(DEFAULT_HOUR);
        assertThat(testLogWork.getMinute()).isEqualTo(DEFAULT_MINUTE);
        assertThat(testLogWork.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLogWork.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createLogWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logWorkRepository.findAll().size();

        // Create the LogWork with an existing ID
        logWork.setId(1L);
        LogWorkDTO logWorkDTO = logWorkMapper.toDto(logWork);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogWorkMockMvc.perform(post("/api/log-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logWorkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogWork in the database
        List<LogWork> logWorkList = logWorkRepository.findAll();
        assertThat(logWorkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogWorks() throws Exception {
        // Initialize the database
        logWorkRepository.saveAndFlush(logWork);

        // Get all the logWorkList
        restLogWorkMockMvc.perform(get("/api/log-works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logWork.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR)))
            .andExpect(jsonPath("$.[*].minute").value(hasItem(DEFAULT_MINUTE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getLogWork() throws Exception {
        // Initialize the database
        logWorkRepository.saveAndFlush(logWork);

        // Get the logWork
        restLogWorkMockMvc.perform(get("/api/log-works/{id}", logWork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logWork.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.hour").value(DEFAULT_HOUR))
            .andExpect(jsonPath("$.minute").value(DEFAULT_MINUTE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogWork() throws Exception {
        // Get the logWork
        restLogWorkMockMvc.perform(get("/api/log-works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogWork() throws Exception {
        // Initialize the database
        logWorkRepository.saveAndFlush(logWork);
        int databaseSizeBeforeUpdate = logWorkRepository.findAll().size();

        // Update the logWork
        LogWork updatedLogWork = logWorkRepository.findOne(logWork.getId());
        // Disconnect from session so that the updates on updatedLogWork are not directly saved in db
        em.detach(updatedLogWork);
        updatedLogWork
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hour(UPDATED_HOUR)
            .minute(UPDATED_MINUTE)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS);
        LogWorkDTO logWorkDTO = logWorkMapper.toDto(updatedLogWork);

        restLogWorkMockMvc.perform(put("/api/log-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logWorkDTO)))
            .andExpect(status().isOk());

        // Validate the LogWork in the database
        List<LogWork> logWorkList = logWorkRepository.findAll();
        assertThat(logWorkList).hasSize(databaseSizeBeforeUpdate);
        LogWork testLogWork = logWorkList.get(logWorkList.size() - 1);
        assertThat(testLogWork.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLogWork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLogWork.getHour()).isEqualTo(UPDATED_HOUR);
        assertThat(testLogWork.getMinute()).isEqualTo(UPDATED_MINUTE);
        assertThat(testLogWork.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLogWork.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingLogWork() throws Exception {
        int databaseSizeBeforeUpdate = logWorkRepository.findAll().size();

        // Create the LogWork
        LogWorkDTO logWorkDTO = logWorkMapper.toDto(logWork);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogWorkMockMvc.perform(put("/api/log-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logWorkDTO)))
            .andExpect(status().isCreated());

        // Validate the LogWork in the database
        List<LogWork> logWorkList = logWorkRepository.findAll();
        assertThat(logWorkList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogWork() throws Exception {
        // Initialize the database
        logWorkRepository.saveAndFlush(logWork);
        int databaseSizeBeforeDelete = logWorkRepository.findAll().size();

        // Get the logWork
        restLogWorkMockMvc.perform(delete("/api/log-works/{id}", logWork.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LogWork> logWorkList = logWorkRepository.findAll();
        assertThat(logWorkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogWork.class);
        LogWork logWork1 = new LogWork();
        logWork1.setId(1L);
        LogWork logWork2 = new LogWork();
        logWork2.setId(logWork1.getId());
        assertThat(logWork1).isEqualTo(logWork2);
        logWork2.setId(2L);
        assertThat(logWork1).isNotEqualTo(logWork2);
        logWork1.setId(null);
        assertThat(logWork1).isNotEqualTo(logWork2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogWorkDTO.class);
        LogWorkDTO logWorkDTO1 = new LogWorkDTO();
        logWorkDTO1.setId(1L);
        LogWorkDTO logWorkDTO2 = new LogWorkDTO();
        assertThat(logWorkDTO1).isNotEqualTo(logWorkDTO2);
        logWorkDTO2.setId(logWorkDTO1.getId());
        assertThat(logWorkDTO1).isEqualTo(logWorkDTO2);
        logWorkDTO2.setId(2L);
        assertThat(logWorkDTO1).isNotEqualTo(logWorkDTO2);
        logWorkDTO1.setId(null);
        assertThat(logWorkDTO1).isNotEqualTo(logWorkDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(logWorkMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(logWorkMapper.fromId(null)).isNull();
    }
}

package com.asart.asart.web.rest;

import com.asart.asart.AsartApp;

import com.asart.asart.domain.Collaborator;
import com.asart.asart.repository.CollaboratorRepository;
import com.asart.asart.service.CollaboratorService;
import com.asart.asart.service.dto.CollaboratorDTO;
import com.asart.asart.service.mapper.CollaboratorMapper;
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
 * Test class for the CollaboratorResource REST controller.
 *
 * @see CollaboratorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsartApp.class)
public class CollaboratorResourceIntTest {

    private static final String DEFAULT_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private CollaboratorMapper collaboratorMapper;

    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollaboratorMockMvc;

    private Collaborator collaborator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollaboratorResource collaboratorResource = new CollaboratorResource(collaboratorService);
        this.restCollaboratorMockMvc = MockMvcBuilders.standaloneSetup(collaboratorResource)
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
    public static Collaborator createEntity(EntityManager em) {
        Collaborator collaborator = new Collaborator()
            .identification(DEFAULT_IDENTIFICATION)
            .name(DEFAULT_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .gender(DEFAULT_GENDER)
            .cellPhone(DEFAULT_CELL_PHONE)
            .address(DEFAULT_ADDRESS)
            .status(DEFAULT_STATUS);
        return collaborator;
    }

    @Before
    public void initTest() {
        collaborator = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollaborator() throws Exception {
        int databaseSizeBeforeCreate = collaboratorRepository.findAll().size();

        // Create the Collaborator
        CollaboratorDTO collaboratorDTO = collaboratorMapper.toDto(collaborator);
        restCollaboratorMockMvc.perform(post("/api/collaborators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaboratorDTO)))
            .andExpect(status().isCreated());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeCreate + 1);
        Collaborator testCollaborator = collaboratorList.get(collaboratorList.size() - 1);
        assertThat(testCollaborator.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testCollaborator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollaborator.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCollaborator.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCollaborator.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testCollaborator.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCollaborator.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testCollaborator.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCollaborator.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCollaboratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collaboratorRepository.findAll().size();

        // Create the Collaborator with an existing ID
        collaborator.setId(1L);
        CollaboratorDTO collaboratorDTO = collaboratorMapper.toDto(collaborator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollaboratorMockMvc.perform(post("/api/collaborators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaboratorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollaborators() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get all the collaboratorList
        restCollaboratorMockMvc.perform(get("/api/collaborators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaborator.getId().intValue())))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getCollaborator() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);

        // Get the collaborator
        restCollaboratorMockMvc.perform(get("/api/collaborators/{id}", collaborator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collaborator.getId().intValue()))
            .andExpect(jsonPath("$.identification").value(DEFAULT_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCollaborator() throws Exception {
        // Get the collaborator
        restCollaboratorMockMvc.perform(get("/api/collaborators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollaborator() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);
        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();

        // Update the collaborator
        Collaborator updatedCollaborator = collaboratorRepository.findOne(collaborator.getId());
        // Disconnect from session so that the updates on updatedCollaborator are not directly saved in db
        em.detach(updatedCollaborator);
        updatedCollaborator
            .identification(UPDATED_IDENTIFICATION)
            .name(UPDATED_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .cellPhone(UPDATED_CELL_PHONE)
            .address(UPDATED_ADDRESS)
            .status(UPDATED_STATUS);
        CollaboratorDTO collaboratorDTO = collaboratorMapper.toDto(updatedCollaborator);

        restCollaboratorMockMvc.perform(put("/api/collaborators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaboratorDTO)))
            .andExpect(status().isOk());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate);
        Collaborator testCollaborator = collaboratorList.get(collaboratorList.size() - 1);
        assertThat(testCollaborator.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testCollaborator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollaborator.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCollaborator.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCollaborator.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testCollaborator.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCollaborator.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testCollaborator.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCollaborator.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCollaborator() throws Exception {
        int databaseSizeBeforeUpdate = collaboratorRepository.findAll().size();

        // Create the Collaborator
        CollaboratorDTO collaboratorDTO = collaboratorMapper.toDto(collaborator);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollaboratorMockMvc.perform(put("/api/collaborators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collaboratorDTO)))
            .andExpect(status().isCreated());

        // Validate the Collaborator in the database
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollaborator() throws Exception {
        // Initialize the database
        collaboratorRepository.saveAndFlush(collaborator);
        int databaseSizeBeforeDelete = collaboratorRepository.findAll().size();

        // Get the collaborator
        restCollaboratorMockMvc.perform(delete("/api/collaborators/{id}", collaborator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Collaborator> collaboratorList = collaboratorRepository.findAll();
        assertThat(collaboratorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collaborator.class);
        Collaborator collaborator1 = new Collaborator();
        collaborator1.setId(1L);
        Collaborator collaborator2 = new Collaborator();
        collaborator2.setId(collaborator1.getId());
        assertThat(collaborator1).isEqualTo(collaborator2);
        collaborator2.setId(2L);
        assertThat(collaborator1).isNotEqualTo(collaborator2);
        collaborator1.setId(null);
        assertThat(collaborator1).isNotEqualTo(collaborator2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollaboratorDTO.class);
        CollaboratorDTO collaboratorDTO1 = new CollaboratorDTO();
        collaboratorDTO1.setId(1L);
        CollaboratorDTO collaboratorDTO2 = new CollaboratorDTO();
        assertThat(collaboratorDTO1).isNotEqualTo(collaboratorDTO2);
        collaboratorDTO2.setId(collaboratorDTO1.getId());
        assertThat(collaboratorDTO1).isEqualTo(collaboratorDTO2);
        collaboratorDTO2.setId(2L);
        assertThat(collaboratorDTO1).isNotEqualTo(collaboratorDTO2);
        collaboratorDTO1.setId(null);
        assertThat(collaboratorDTO1).isNotEqualTo(collaboratorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collaboratorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collaboratorMapper.fromId(null)).isNull();
    }
}

package com.asart.asart.web.rest;

import com.asart.asart.AsartApp;

import com.asart.asart.domain.LinkAuth;
import com.asart.asart.repository.LinkAuthRepository;
import com.asart.asart.service.LinkAuthService;
import com.asart.asart.service.dto.LinkAuthDTO;
import com.asart.asart.service.mapper.LinkAuthMapper;
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
 * Test class for the LinkAuthResource REST controller.
 *
 * @see LinkAuthResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsartApp.class)
public class LinkAuthResourceIntTest {

    private static final Long DEFAULT_ID_SESSION = 1L;
    private static final Long UPDATED_ID_SESSION = 2L;

    private static final Long DEFAULT_ID_COLLABORATOR = 1L;
    private static final Long UPDATED_ID_COLLABORATOR = 2L;

    @Autowired
    private LinkAuthRepository linkAuthRepository;

    @Autowired
    private LinkAuthMapper linkAuthMapper;

    @Autowired
    private LinkAuthService linkAuthService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLinkAuthMockMvc;

    private LinkAuth linkAuth;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LinkAuthResource linkAuthResource = new LinkAuthResource(linkAuthService);
        this.restLinkAuthMockMvc = MockMvcBuilders.standaloneSetup(linkAuthResource)
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
    public static LinkAuth createEntity(EntityManager em) {
        LinkAuth linkAuth = new LinkAuth()
            .idSession(DEFAULT_ID_SESSION)
            .idCollaborator(DEFAULT_ID_COLLABORATOR);
        return linkAuth;
    }

    @Before
    public void initTest() {
        linkAuth = createEntity(em);
    }

    @Test
    @Transactional
    public void createLinkAuth() throws Exception {
        int databaseSizeBeforeCreate = linkAuthRepository.findAll().size();

        // Create the LinkAuth
        LinkAuthDTO linkAuthDTO = linkAuthMapper.toDto(linkAuth);
        restLinkAuthMockMvc.perform(post("/api/link-auths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linkAuthDTO)))
            .andExpect(status().isCreated());

        // Validate the LinkAuth in the database
        List<LinkAuth> linkAuthList = linkAuthRepository.findAll();
        assertThat(linkAuthList).hasSize(databaseSizeBeforeCreate + 1);
        LinkAuth testLinkAuth = linkAuthList.get(linkAuthList.size() - 1);
        assertThat(testLinkAuth.getIdSession()).isEqualTo(DEFAULT_ID_SESSION);
        assertThat(testLinkAuth.getIdCollaborator()).isEqualTo(DEFAULT_ID_COLLABORATOR);
    }

    @Test
    @Transactional
    public void createLinkAuthWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = linkAuthRepository.findAll().size();

        // Create the LinkAuth with an existing ID
        linkAuth.setId(1L);
        LinkAuthDTO linkAuthDTO = linkAuthMapper.toDto(linkAuth);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkAuthMockMvc.perform(post("/api/link-auths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linkAuthDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LinkAuth in the database
        List<LinkAuth> linkAuthList = linkAuthRepository.findAll();
        assertThat(linkAuthList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLinkAuths() throws Exception {
        // Initialize the database
        linkAuthRepository.saveAndFlush(linkAuth);

        // Get all the linkAuthList
        restLinkAuthMockMvc.perform(get("/api/link-auths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linkAuth.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSession").value(hasItem(DEFAULT_ID_SESSION.intValue())))
            .andExpect(jsonPath("$.[*].idCollaborator").value(hasItem(DEFAULT_ID_COLLABORATOR.intValue())));
    }

    @Test
    @Transactional
    public void getLinkAuth() throws Exception {
        // Initialize the database
        linkAuthRepository.saveAndFlush(linkAuth);

        // Get the linkAuth
        restLinkAuthMockMvc.perform(get("/api/link-auths/{id}", linkAuth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(linkAuth.getId().intValue()))
            .andExpect(jsonPath("$.idSession").value(DEFAULT_ID_SESSION.intValue()))
            .andExpect(jsonPath("$.idCollaborator").value(DEFAULT_ID_COLLABORATOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLinkAuth() throws Exception {
        // Get the linkAuth
        restLinkAuthMockMvc.perform(get("/api/link-auths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLinkAuth() throws Exception {
        // Initialize the database
        linkAuthRepository.saveAndFlush(linkAuth);
        int databaseSizeBeforeUpdate = linkAuthRepository.findAll().size();

        // Update the linkAuth
        LinkAuth updatedLinkAuth = linkAuthRepository.findOne(linkAuth.getId());
        // Disconnect from session so that the updates on updatedLinkAuth are not directly saved in db
        em.detach(updatedLinkAuth);
        updatedLinkAuth
            .idSession(UPDATED_ID_SESSION)
            .idCollaborator(UPDATED_ID_COLLABORATOR);
        LinkAuthDTO linkAuthDTO = linkAuthMapper.toDto(updatedLinkAuth);

        restLinkAuthMockMvc.perform(put("/api/link-auths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linkAuthDTO)))
            .andExpect(status().isOk());

        // Validate the LinkAuth in the database
        List<LinkAuth> linkAuthList = linkAuthRepository.findAll();
        assertThat(linkAuthList).hasSize(databaseSizeBeforeUpdate);
        LinkAuth testLinkAuth = linkAuthList.get(linkAuthList.size() - 1);
        assertThat(testLinkAuth.getIdSession()).isEqualTo(UPDATED_ID_SESSION);
        assertThat(testLinkAuth.getIdCollaborator()).isEqualTo(UPDATED_ID_COLLABORATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingLinkAuth() throws Exception {
        int databaseSizeBeforeUpdate = linkAuthRepository.findAll().size();

        // Create the LinkAuth
        LinkAuthDTO linkAuthDTO = linkAuthMapper.toDto(linkAuth);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLinkAuthMockMvc.perform(put("/api/link-auths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linkAuthDTO)))
            .andExpect(status().isCreated());

        // Validate the LinkAuth in the database
        List<LinkAuth> linkAuthList = linkAuthRepository.findAll();
        assertThat(linkAuthList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLinkAuth() throws Exception {
        // Initialize the database
        linkAuthRepository.saveAndFlush(linkAuth);
        int databaseSizeBeforeDelete = linkAuthRepository.findAll().size();

        // Get the linkAuth
        restLinkAuthMockMvc.perform(delete("/api/link-auths/{id}", linkAuth.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LinkAuth> linkAuthList = linkAuthRepository.findAll();
        assertThat(linkAuthList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkAuth.class);
        LinkAuth linkAuth1 = new LinkAuth();
        linkAuth1.setId(1L);
        LinkAuth linkAuth2 = new LinkAuth();
        linkAuth2.setId(linkAuth1.getId());
        assertThat(linkAuth1).isEqualTo(linkAuth2);
        linkAuth2.setId(2L);
        assertThat(linkAuth1).isNotEqualTo(linkAuth2);
        linkAuth1.setId(null);
        assertThat(linkAuth1).isNotEqualTo(linkAuth2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkAuthDTO.class);
        LinkAuthDTO linkAuthDTO1 = new LinkAuthDTO();
        linkAuthDTO1.setId(1L);
        LinkAuthDTO linkAuthDTO2 = new LinkAuthDTO();
        assertThat(linkAuthDTO1).isNotEqualTo(linkAuthDTO2);
        linkAuthDTO2.setId(linkAuthDTO1.getId());
        assertThat(linkAuthDTO1).isEqualTo(linkAuthDTO2);
        linkAuthDTO2.setId(2L);
        assertThat(linkAuthDTO1).isNotEqualTo(linkAuthDTO2);
        linkAuthDTO1.setId(null);
        assertThat(linkAuthDTO1).isNotEqualTo(linkAuthDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(linkAuthMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(linkAuthMapper.fromId(null)).isNull();
    }
}

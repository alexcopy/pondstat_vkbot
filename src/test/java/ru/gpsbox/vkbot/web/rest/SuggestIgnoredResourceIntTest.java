package ru.gpsbox.vkbot.web.rest;

import ru.gpsbox.vkbot.VkbotApp;

import ru.gpsbox.vkbot.domain.SuggestIgnored;
import ru.gpsbox.vkbot.repository.SuggestIgnoredRepository;
import ru.gpsbox.vkbot.service.SuggestIgnoredService;
import ru.gpsbox.vkbot.repository.search.SuggestIgnoredSearchRepository;
import ru.gpsbox.vkbot.service.dto.SuggestIgnoredDTO;
import ru.gpsbox.vkbot.service.mapper.SuggestIgnoredMapper;
import ru.gpsbox.vkbot.web.rest.errors.ExceptionTranslator;

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

import java.util.List;

import static ru.gpsbox.vkbot.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SuggestIgnoredResource REST controller.
 *
 * @see SuggestIgnoredResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class SuggestIgnoredResourceIntTest {

    private static final Integer DEFAULT_PICTID = 1;
    private static final Integer UPDATED_PICTID = 2;

    private static final Long DEFAULT_MODELID = 1L;
    private static final Long UPDATED_MODELID = 2L;

    private static final Long DEFAULT_RESULTID = 1L;
    private static final Long UPDATED_RESULTID = 2L;

    @Autowired
    private SuggestIgnoredRepository suggestIgnoredRepository;

    @Autowired
    private SuggestIgnoredMapper suggestIgnoredMapper;

    @Autowired
    private SuggestIgnoredService suggestIgnoredService;

    @Autowired
    private SuggestIgnoredSearchRepository suggestIgnoredSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSuggestIgnoredMockMvc;

    private SuggestIgnored suggestIgnored;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuggestIgnoredResource suggestIgnoredResource = new SuggestIgnoredResource(suggestIgnoredService);
        this.restSuggestIgnoredMockMvc = MockMvcBuilders.standaloneSetup(suggestIgnoredResource)
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
    public static SuggestIgnored createEntity() {
        SuggestIgnored suggestIgnored = new SuggestIgnored()
            .pictid(DEFAULT_PICTID)
            .modelid(DEFAULT_MODELID)
            .resultid(DEFAULT_RESULTID);
        return suggestIgnored;
    }

    @Before
    public void initTest() {
        suggestIgnoredRepository.deleteAll();
        suggestIgnoredSearchRepository.deleteAll();
        suggestIgnored = createEntity();
    }

    @Test
    public void createSuggestIgnored() throws Exception {
        int databaseSizeBeforeCreate = suggestIgnoredRepository.findAll().size();

        // Create the SuggestIgnored
        SuggestIgnoredDTO suggestIgnoredDTO = suggestIgnoredMapper.toDto(suggestIgnored);
        restSuggestIgnoredMockMvc.perform(post("/api/suggest-ignoreds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestIgnoredDTO)))
            .andExpect(status().isCreated());

        // Validate the SuggestIgnored in the database
        List<SuggestIgnored> suggestIgnoredList = suggestIgnoredRepository.findAll();
        assertThat(suggestIgnoredList).hasSize(databaseSizeBeforeCreate + 1);
        SuggestIgnored testSuggestIgnored = suggestIgnoredList.get(suggestIgnoredList.size() - 1);
        assertThat(testSuggestIgnored.getPictid()).isEqualTo(DEFAULT_PICTID);
        assertThat(testSuggestIgnored.getModelid()).isEqualTo(DEFAULT_MODELID);
        assertThat(testSuggestIgnored.getResultid()).isEqualTo(DEFAULT_RESULTID);

        // Validate the SuggestIgnored in Elasticsearch
        SuggestIgnored suggestIgnoredEs = suggestIgnoredSearchRepository.findOne(testSuggestIgnored.getId());
        assertThat(suggestIgnoredEs).isEqualToIgnoringGivenFields(testSuggestIgnored);
    }

    @Test
    public void createSuggestIgnoredWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suggestIgnoredRepository.findAll().size();

        // Create the SuggestIgnored with an existing ID
        suggestIgnored.setId("existing_id");
        SuggestIgnoredDTO suggestIgnoredDTO = suggestIgnoredMapper.toDto(suggestIgnored);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuggestIgnoredMockMvc.perform(post("/api/suggest-ignoreds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestIgnoredDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SuggestIgnored in the database
        List<SuggestIgnored> suggestIgnoredList = suggestIgnoredRepository.findAll();
        assertThat(suggestIgnoredList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllSuggestIgnoreds() throws Exception {
        // Initialize the database
        suggestIgnoredRepository.save(suggestIgnored);

        // Get all the suggestIgnoredList
        restSuggestIgnoredMockMvc.perform(get("/api/suggest-ignoreds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suggestIgnored.getId())))
            .andExpect(jsonPath("$.[*].pictid").value(hasItem(DEFAULT_PICTID)))
            .andExpect(jsonPath("$.[*].modelid").value(hasItem(DEFAULT_MODELID.intValue())))
            .andExpect(jsonPath("$.[*].resultid").value(hasItem(DEFAULT_RESULTID.intValue())));
    }

    @Test
    public void getSuggestIgnored() throws Exception {
        // Initialize the database
        suggestIgnoredRepository.save(suggestIgnored);

        // Get the suggestIgnored
        restSuggestIgnoredMockMvc.perform(get("/api/suggest-ignoreds/{id}", suggestIgnored.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suggestIgnored.getId()))
            .andExpect(jsonPath("$.pictid").value(DEFAULT_PICTID))
            .andExpect(jsonPath("$.modelid").value(DEFAULT_MODELID.intValue()))
            .andExpect(jsonPath("$.resultid").value(DEFAULT_RESULTID.intValue()));
    }

    @Test
    public void getNonExistingSuggestIgnored() throws Exception {
        // Get the suggestIgnored
        restSuggestIgnoredMockMvc.perform(get("/api/suggest-ignoreds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSuggestIgnored() throws Exception {
        // Initialize the database
        suggestIgnoredRepository.save(suggestIgnored);
        suggestIgnoredSearchRepository.save(suggestIgnored);
        int databaseSizeBeforeUpdate = suggestIgnoredRepository.findAll().size();

        // Update the suggestIgnored
        SuggestIgnored updatedSuggestIgnored = suggestIgnoredRepository.findOne(suggestIgnored.getId());
        updatedSuggestIgnored
            .pictid(UPDATED_PICTID)
            .modelid(UPDATED_MODELID)
            .resultid(UPDATED_RESULTID);
        SuggestIgnoredDTO suggestIgnoredDTO = suggestIgnoredMapper.toDto(updatedSuggestIgnored);

        restSuggestIgnoredMockMvc.perform(put("/api/suggest-ignoreds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestIgnoredDTO)))
            .andExpect(status().isOk());

        // Validate the SuggestIgnored in the database
        List<SuggestIgnored> suggestIgnoredList = suggestIgnoredRepository.findAll();
        assertThat(suggestIgnoredList).hasSize(databaseSizeBeforeUpdate);
        SuggestIgnored testSuggestIgnored = suggestIgnoredList.get(suggestIgnoredList.size() - 1);
        assertThat(testSuggestIgnored.getPictid()).isEqualTo(UPDATED_PICTID);
        assertThat(testSuggestIgnored.getModelid()).isEqualTo(UPDATED_MODELID);
        assertThat(testSuggestIgnored.getResultid()).isEqualTo(UPDATED_RESULTID);

        // Validate the SuggestIgnored in Elasticsearch
        SuggestIgnored suggestIgnoredEs = suggestIgnoredSearchRepository.findOne(testSuggestIgnored.getId());
        assertThat(suggestIgnoredEs).isEqualToIgnoringGivenFields(testSuggestIgnored);
    }

    @Test
    public void updateNonExistingSuggestIgnored() throws Exception {
        int databaseSizeBeforeUpdate = suggestIgnoredRepository.findAll().size();

        // Create the SuggestIgnored
        SuggestIgnoredDTO suggestIgnoredDTO = suggestIgnoredMapper.toDto(suggestIgnored);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSuggestIgnoredMockMvc.perform(put("/api/suggest-ignoreds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suggestIgnoredDTO)))
            .andExpect(status().isCreated());

        // Validate the SuggestIgnored in the database
        List<SuggestIgnored> suggestIgnoredList = suggestIgnoredRepository.findAll();
        assertThat(suggestIgnoredList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteSuggestIgnored() throws Exception {
        // Initialize the database
        suggestIgnoredRepository.save(suggestIgnored);
        suggestIgnoredSearchRepository.save(suggestIgnored);
        int databaseSizeBeforeDelete = suggestIgnoredRepository.findAll().size();

        // Get the suggestIgnored
        restSuggestIgnoredMockMvc.perform(delete("/api/suggest-ignoreds/{id}", suggestIgnored.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean suggestIgnoredExistsInEs = suggestIgnoredSearchRepository.exists(suggestIgnored.getId());
        assertThat(suggestIgnoredExistsInEs).isFalse();

        // Validate the database is empty
        List<SuggestIgnored> suggestIgnoredList = suggestIgnoredRepository.findAll();
        assertThat(suggestIgnoredList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchSuggestIgnored() throws Exception {
        // Initialize the database
        suggestIgnoredRepository.save(suggestIgnored);
        suggestIgnoredSearchRepository.save(suggestIgnored);

        // Search the suggestIgnored
        restSuggestIgnoredMockMvc.perform(get("/api/_search/suggest-ignoreds?query=id:" + suggestIgnored.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suggestIgnored.getId())))
            .andExpect(jsonPath("$.[*].pictid").value(hasItem(DEFAULT_PICTID)))
            .andExpect(jsonPath("$.[*].modelid").value(hasItem(DEFAULT_MODELID.intValue())))
            .andExpect(jsonPath("$.[*].resultid").value(hasItem(DEFAULT_RESULTID.intValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuggestIgnored.class);
        SuggestIgnored suggestIgnored1 = new SuggestIgnored();
        suggestIgnored1.setId("id1");
        SuggestIgnored suggestIgnored2 = new SuggestIgnored();
        suggestIgnored2.setId(suggestIgnored1.getId());
        assertThat(suggestIgnored1).isEqualTo(suggestIgnored2);
        suggestIgnored2.setId("id2");
        assertThat(suggestIgnored1).isNotEqualTo(suggestIgnored2);
        suggestIgnored1.setId(null);
        assertThat(suggestIgnored1).isNotEqualTo(suggestIgnored2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuggestIgnoredDTO.class);
        SuggestIgnoredDTO suggestIgnoredDTO1 = new SuggestIgnoredDTO();
        suggestIgnoredDTO1.setId("id1");
        SuggestIgnoredDTO suggestIgnoredDTO2 = new SuggestIgnoredDTO();
        assertThat(suggestIgnoredDTO1).isNotEqualTo(suggestIgnoredDTO2);
        suggestIgnoredDTO2.setId(suggestIgnoredDTO1.getId());
        assertThat(suggestIgnoredDTO1).isEqualTo(suggestIgnoredDTO2);
        suggestIgnoredDTO2.setId("id2");
        assertThat(suggestIgnoredDTO1).isNotEqualTo(suggestIgnoredDTO2);
        suggestIgnoredDTO1.setId(null);
        assertThat(suggestIgnoredDTO1).isNotEqualTo(suggestIgnoredDTO2);
    }
}

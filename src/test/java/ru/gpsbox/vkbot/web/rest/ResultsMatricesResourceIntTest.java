package ru.gpsbox.vkbot.web.rest;

import ru.gpsbox.vkbot.VkbotApp;

import ru.gpsbox.vkbot.domain.ResultsMatrices;
import ru.gpsbox.vkbot.repository.ResultsMatricesRepository;
import ru.gpsbox.vkbot.service.ResultsMatricesService;
import ru.gpsbox.vkbot.repository.search.ResultsMatricesSearchRepository;
import ru.gpsbox.vkbot.service.dto.ResultsMatricesDTO;
import ru.gpsbox.vkbot.service.mapper.ResultsMatricesMapper;
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
 * Test class for the ResultsMatricesResource REST controller.
 *
 * @see ResultsMatricesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class ResultsMatricesResourceIntTest {

    private static final Long DEFAULT_RESID = 1L;
    private static final Long UPDATED_RESID = 2L;

    private static final Integer DEFAULT_RESULT = 1;
    private static final Integer UPDATED_RESULT = 2;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_MATRIX = 1;
    private static final Integer UPDATED_MATRIX = 2;

    @Autowired
    private ResultsMatricesRepository resultsMatricesRepository;

    @Autowired
    private ResultsMatricesMapper resultsMatricesMapper;

    @Autowired
    private ResultsMatricesService resultsMatricesService;

    @Autowired
    private ResultsMatricesSearchRepository resultsMatricesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restResultsMatricesMockMvc;

    private ResultsMatrices resultsMatrices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultsMatricesResource resultsMatricesResource = new ResultsMatricesResource(resultsMatricesService);
        this.restResultsMatricesMockMvc = MockMvcBuilders.standaloneSetup(resultsMatricesResource)
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
    public static ResultsMatrices createEntity() {
        ResultsMatrices resultsMatrices = new ResultsMatrices()
            .resid(DEFAULT_RESID)
            .result(DEFAULT_RESULT)
            .type(DEFAULT_TYPE)
            .matrix(DEFAULT_MATRIX);
        return resultsMatrices;
    }

    @Before
    public void initTest() {
        resultsMatricesRepository.deleteAll();
        resultsMatricesSearchRepository.deleteAll();
        resultsMatrices = createEntity();
    }

    @Test
    public void createResultsMatrices() throws Exception {
        int databaseSizeBeforeCreate = resultsMatricesRepository.findAll().size();

        // Create the ResultsMatrices
        ResultsMatricesDTO resultsMatricesDTO = resultsMatricesMapper.toDto(resultsMatrices);
        restResultsMatricesMockMvc.perform(post("/api/results-matrices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsMatricesDTO)))
            .andExpect(status().isCreated());

        // Validate the ResultsMatrices in the database
        List<ResultsMatrices> resultsMatricesList = resultsMatricesRepository.findAll();
        assertThat(resultsMatricesList).hasSize(databaseSizeBeforeCreate + 1);
        ResultsMatrices testResultsMatrices = resultsMatricesList.get(resultsMatricesList.size() - 1);
        assertThat(testResultsMatrices.getResid()).isEqualTo(DEFAULT_RESID);
        assertThat(testResultsMatrices.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testResultsMatrices.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testResultsMatrices.getMatrix()).isEqualTo(DEFAULT_MATRIX);

        // Validate the ResultsMatrices in Elasticsearch
        ResultsMatrices resultsMatricesEs = resultsMatricesSearchRepository.findOne(testResultsMatrices.getId());
        assertThat(resultsMatricesEs).isEqualToIgnoringGivenFields(testResultsMatrices);
    }

    @Test
    public void createResultsMatricesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultsMatricesRepository.findAll().size();

        // Create the ResultsMatrices with an existing ID
        resultsMatrices.setId("existing_id");
        ResultsMatricesDTO resultsMatricesDTO = resultsMatricesMapper.toDto(resultsMatrices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultsMatricesMockMvc.perform(post("/api/results-matrices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsMatricesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResultsMatrices in the database
        List<ResultsMatrices> resultsMatricesList = resultsMatricesRepository.findAll();
        assertThat(resultsMatricesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllResultsMatrices() throws Exception {
        // Initialize the database
        resultsMatricesRepository.save(resultsMatrices);

        // Get all the resultsMatricesList
        restResultsMatricesMockMvc.perform(get("/api/results-matrices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultsMatrices.getId())))
            .andExpect(jsonPath("$.[*].resid").value(hasItem(DEFAULT_RESID.intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].matrix").value(hasItem(DEFAULT_MATRIX)));
    }

    @Test
    public void getResultsMatrices() throws Exception {
        // Initialize the database
        resultsMatricesRepository.save(resultsMatrices);

        // Get the resultsMatrices
        restResultsMatricesMockMvc.perform(get("/api/results-matrices/{id}", resultsMatrices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultsMatrices.getId()))
            .andExpect(jsonPath("$.resid").value(DEFAULT_RESID.intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.matrix").value(DEFAULT_MATRIX));
    }

    @Test
    public void getNonExistingResultsMatrices() throws Exception {
        // Get the resultsMatrices
        restResultsMatricesMockMvc.perform(get("/api/results-matrices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateResultsMatrices() throws Exception {
        // Initialize the database
        resultsMatricesRepository.save(resultsMatrices);
        resultsMatricesSearchRepository.save(resultsMatrices);
        int databaseSizeBeforeUpdate = resultsMatricesRepository.findAll().size();

        // Update the resultsMatrices
        ResultsMatrices updatedResultsMatrices = resultsMatricesRepository.findOne(resultsMatrices.getId());
        updatedResultsMatrices
            .resid(UPDATED_RESID)
            .result(UPDATED_RESULT)
            .type(UPDATED_TYPE)
            .matrix(UPDATED_MATRIX);
        ResultsMatricesDTO resultsMatricesDTO = resultsMatricesMapper.toDto(updatedResultsMatrices);

        restResultsMatricesMockMvc.perform(put("/api/results-matrices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsMatricesDTO)))
            .andExpect(status().isOk());

        // Validate the ResultsMatrices in the database
        List<ResultsMatrices> resultsMatricesList = resultsMatricesRepository.findAll();
        assertThat(resultsMatricesList).hasSize(databaseSizeBeforeUpdate);
        ResultsMatrices testResultsMatrices = resultsMatricesList.get(resultsMatricesList.size() - 1);
        assertThat(testResultsMatrices.getResid()).isEqualTo(UPDATED_RESID);
        assertThat(testResultsMatrices.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testResultsMatrices.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testResultsMatrices.getMatrix()).isEqualTo(UPDATED_MATRIX);

        // Validate the ResultsMatrices in Elasticsearch
        ResultsMatrices resultsMatricesEs = resultsMatricesSearchRepository.findOne(testResultsMatrices.getId());
        assertThat(resultsMatricesEs).isEqualToIgnoringGivenFields(testResultsMatrices);
    }

    @Test
    public void updateNonExistingResultsMatrices() throws Exception {
        int databaseSizeBeforeUpdate = resultsMatricesRepository.findAll().size();

        // Create the ResultsMatrices
        ResultsMatricesDTO resultsMatricesDTO = resultsMatricesMapper.toDto(resultsMatrices);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResultsMatricesMockMvc.perform(put("/api/results-matrices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsMatricesDTO)))
            .andExpect(status().isCreated());

        // Validate the ResultsMatrices in the database
        List<ResultsMatrices> resultsMatricesList = resultsMatricesRepository.findAll();
        assertThat(resultsMatricesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteResultsMatrices() throws Exception {
        // Initialize the database
        resultsMatricesRepository.save(resultsMatrices);
        resultsMatricesSearchRepository.save(resultsMatrices);
        int databaseSizeBeforeDelete = resultsMatricesRepository.findAll().size();

        // Get the resultsMatrices
        restResultsMatricesMockMvc.perform(delete("/api/results-matrices/{id}", resultsMatrices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean resultsMatricesExistsInEs = resultsMatricesSearchRepository.exists(resultsMatrices.getId());
        assertThat(resultsMatricesExistsInEs).isFalse();

        // Validate the database is empty
        List<ResultsMatrices> resultsMatricesList = resultsMatricesRepository.findAll();
        assertThat(resultsMatricesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchResultsMatrices() throws Exception {
        // Initialize the database
        resultsMatricesRepository.save(resultsMatrices);
        resultsMatricesSearchRepository.save(resultsMatrices);

        // Search the resultsMatrices
        restResultsMatricesMockMvc.perform(get("/api/_search/results-matrices?query=id:" + resultsMatrices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultsMatrices.getId())))
            .andExpect(jsonPath("$.[*].resid").value(hasItem(DEFAULT_RESID.intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].matrix").value(hasItem(DEFAULT_MATRIX)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultsMatrices.class);
        ResultsMatrices resultsMatrices1 = new ResultsMatrices();
        resultsMatrices1.setId("id1");
        ResultsMatrices resultsMatrices2 = new ResultsMatrices();
        resultsMatrices2.setId(resultsMatrices1.getId());
        assertThat(resultsMatrices1).isEqualTo(resultsMatrices2);
        resultsMatrices2.setId("id2");
        assertThat(resultsMatrices1).isNotEqualTo(resultsMatrices2);
        resultsMatrices1.setId(null);
        assertThat(resultsMatrices1).isNotEqualTo(resultsMatrices2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultsMatricesDTO.class);
        ResultsMatricesDTO resultsMatricesDTO1 = new ResultsMatricesDTO();
        resultsMatricesDTO1.setId("id1");
        ResultsMatricesDTO resultsMatricesDTO2 = new ResultsMatricesDTO();
        assertThat(resultsMatricesDTO1).isNotEqualTo(resultsMatricesDTO2);
        resultsMatricesDTO2.setId(resultsMatricesDTO1.getId());
        assertThat(resultsMatricesDTO1).isEqualTo(resultsMatricesDTO2);
        resultsMatricesDTO2.setId("id2");
        assertThat(resultsMatricesDTO1).isNotEqualTo(resultsMatricesDTO2);
        resultsMatricesDTO1.setId(null);
        assertThat(resultsMatricesDTO1).isNotEqualTo(resultsMatricesDTO2);
    }
}

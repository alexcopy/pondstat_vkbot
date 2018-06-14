package ru.gpsbox.natmob.web.rest;

import ru.gpsbox.natmob.VkbotApp;

import ru.gpsbox.natmob.domain.ModelTraining;
import ru.gpsbox.natmob.repository.ModelTrainingRepository;
import ru.gpsbox.natmob.service.ModelTrainingService;
import ru.gpsbox.natmob.repository.search.ModelTrainingSearchRepository;
import ru.gpsbox.natmob.service.dto.ModelTrainingDTO;
import ru.gpsbox.natmob.service.mapper.ModelTrainingMapper;
import ru.gpsbox.natmob.web.rest.errors.ExceptionTranslator;

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

import static ru.gpsbox.natmob.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ModelTrainingResource REST controller.
 *
 * @see ModelTrainingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class ModelTrainingResourceIntTest {

    private static final Integer DEFAULT_PICTID = 1;
    private static final Integer UPDATED_PICTID = 2;

    private static final Long DEFAULT_MODELID = 1L;
    private static final Long UPDATED_MODELID = 2L;

    private static final Integer DEFAULT_PROCID = 1;
    private static final Integer UPDATED_PROCID = 2;

    private static final Integer DEFAULT_RESULT = 1;
    private static final Integer UPDATED_RESULT = 2;

    @Autowired
    private ModelTrainingRepository modelTrainingRepository;

    @Autowired
    private ModelTrainingMapper modelTrainingMapper;

    @Autowired
    private ModelTrainingService modelTrainingService;

    @Autowired
    private ModelTrainingSearchRepository modelTrainingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restModelTrainingMockMvc;

    private ModelTraining modelTraining;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModelTrainingResource modelTrainingResource = new ModelTrainingResource(modelTrainingService);
        this.restModelTrainingMockMvc = MockMvcBuilders.standaloneSetup(modelTrainingResource)
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
    public static ModelTraining createEntity() {
        ModelTraining modelTraining = new ModelTraining()
            .pictid(DEFAULT_PICTID)
            .modelid(DEFAULT_MODELID)
            .procid(DEFAULT_PROCID)
            .result(DEFAULT_RESULT);
        return modelTraining;
    }

    @Before
    public void initTest() {
        modelTrainingRepository.deleteAll();
        modelTrainingSearchRepository.deleteAll();
        modelTraining = createEntity();
    }

    @Test
    public void createModelTraining() throws Exception {
        int databaseSizeBeforeCreate = modelTrainingRepository.findAll().size();

        // Create the ModelTraining
        ModelTrainingDTO modelTrainingDTO = modelTrainingMapper.toDto(modelTraining);
        restModelTrainingMockMvc.perform(post("/api/model-trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelTrainingDTO)))
            .andExpect(status().isCreated());

        // Validate the ModelTraining in the database
        List<ModelTraining> modelTrainingList = modelTrainingRepository.findAll();
        assertThat(modelTrainingList).hasSize(databaseSizeBeforeCreate + 1);
        ModelTraining testModelTraining = modelTrainingList.get(modelTrainingList.size() - 1);
        assertThat(testModelTraining.getPictid()).isEqualTo(DEFAULT_PICTID);
        assertThat(testModelTraining.getModelid()).isEqualTo(DEFAULT_MODELID);
        assertThat(testModelTraining.getProcid()).isEqualTo(DEFAULT_PROCID);
        assertThat(testModelTraining.getResult()).isEqualTo(DEFAULT_RESULT);

        // Validate the ModelTraining in Elasticsearch
        ModelTraining modelTrainingEs = modelTrainingSearchRepository.findOne(testModelTraining.getId());
        assertThat(modelTrainingEs).isEqualToIgnoringGivenFields(testModelTraining);
    }

    @Test
    public void createModelTrainingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelTrainingRepository.findAll().size();

        // Create the ModelTraining with an existing ID
        modelTraining.setId("existing_id");
        ModelTrainingDTO modelTrainingDTO = modelTrainingMapper.toDto(modelTraining);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelTrainingMockMvc.perform(post("/api/model-trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelTrainingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModelTraining in the database
        List<ModelTraining> modelTrainingList = modelTrainingRepository.findAll();
        assertThat(modelTrainingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllModelTrainings() throws Exception {
        // Initialize the database
        modelTrainingRepository.save(modelTraining);

        // Get all the modelTrainingList
        restModelTrainingMockMvc.perform(get("/api/model-trainings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelTraining.getId())))
            .andExpect(jsonPath("$.[*].pictid").value(hasItem(DEFAULT_PICTID)))
            .andExpect(jsonPath("$.[*].modelid").value(hasItem(DEFAULT_MODELID.intValue())))
            .andExpect(jsonPath("$.[*].procid").value(hasItem(DEFAULT_PROCID)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));
    }

    @Test
    public void getModelTraining() throws Exception {
        // Initialize the database
        modelTrainingRepository.save(modelTraining);

        // Get the modelTraining
        restModelTrainingMockMvc.perform(get("/api/model-trainings/{id}", modelTraining.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelTraining.getId()))
            .andExpect(jsonPath("$.pictid").value(DEFAULT_PICTID))
            .andExpect(jsonPath("$.modelid").value(DEFAULT_MODELID.intValue()))
            .andExpect(jsonPath("$.procid").value(DEFAULT_PROCID))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT));
    }

    @Test
    public void getNonExistingModelTraining() throws Exception {
        // Get the modelTraining
        restModelTrainingMockMvc.perform(get("/api/model-trainings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateModelTraining() throws Exception {
        // Initialize the database
        modelTrainingRepository.save(modelTraining);
        modelTrainingSearchRepository.save(modelTraining);
        int databaseSizeBeforeUpdate = modelTrainingRepository.findAll().size();

        // Update the modelTraining
        ModelTraining updatedModelTraining = modelTrainingRepository.findOne(modelTraining.getId());
        updatedModelTraining
            .pictid(UPDATED_PICTID)
            .modelid(UPDATED_MODELID)
            .procid(UPDATED_PROCID)
            .result(UPDATED_RESULT);
        ModelTrainingDTO modelTrainingDTO = modelTrainingMapper.toDto(updatedModelTraining);

        restModelTrainingMockMvc.perform(put("/api/model-trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelTrainingDTO)))
            .andExpect(status().isOk());

        // Validate the ModelTraining in the database
        List<ModelTraining> modelTrainingList = modelTrainingRepository.findAll();
        assertThat(modelTrainingList).hasSize(databaseSizeBeforeUpdate);
        ModelTraining testModelTraining = modelTrainingList.get(modelTrainingList.size() - 1);
        assertThat(testModelTraining.getPictid()).isEqualTo(UPDATED_PICTID);
        assertThat(testModelTraining.getModelid()).isEqualTo(UPDATED_MODELID);
        assertThat(testModelTraining.getProcid()).isEqualTo(UPDATED_PROCID);
        assertThat(testModelTraining.getResult()).isEqualTo(UPDATED_RESULT);

        // Validate the ModelTraining in Elasticsearch
        ModelTraining modelTrainingEs = modelTrainingSearchRepository.findOne(testModelTraining.getId());
        assertThat(modelTrainingEs).isEqualToIgnoringGivenFields(testModelTraining);
    }

    @Test
    public void updateNonExistingModelTraining() throws Exception {
        int databaseSizeBeforeUpdate = modelTrainingRepository.findAll().size();

        // Create the ModelTraining
        ModelTrainingDTO modelTrainingDTO = modelTrainingMapper.toDto(modelTraining);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModelTrainingMockMvc.perform(put("/api/model-trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelTrainingDTO)))
            .andExpect(status().isCreated());

        // Validate the ModelTraining in the database
        List<ModelTraining> modelTrainingList = modelTrainingRepository.findAll();
        assertThat(modelTrainingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteModelTraining() throws Exception {
        // Initialize the database
        modelTrainingRepository.save(modelTraining);
        modelTrainingSearchRepository.save(modelTraining);
        int databaseSizeBeforeDelete = modelTrainingRepository.findAll().size();

        // Get the modelTraining
        restModelTrainingMockMvc.perform(delete("/api/model-trainings/{id}", modelTraining.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean modelTrainingExistsInEs = modelTrainingSearchRepository.exists(modelTraining.getId());
        assertThat(modelTrainingExistsInEs).isFalse();

        // Validate the database is empty
        List<ModelTraining> modelTrainingList = modelTrainingRepository.findAll();
        assertThat(modelTrainingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchModelTraining() throws Exception {
        // Initialize the database
        modelTrainingRepository.save(modelTraining);
        modelTrainingSearchRepository.save(modelTraining);

        // Search the modelTraining
        restModelTrainingMockMvc.perform(get("/api/_search/model-trainings?query=id:" + modelTraining.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelTraining.getId())))
            .andExpect(jsonPath("$.[*].pictid").value(hasItem(DEFAULT_PICTID)))
            .andExpect(jsonPath("$.[*].modelid").value(hasItem(DEFAULT_MODELID.intValue())))
            .andExpect(jsonPath("$.[*].procid").value(hasItem(DEFAULT_PROCID)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelTraining.class);
        ModelTraining modelTraining1 = new ModelTraining();
        modelTraining1.setId("id1");
        ModelTraining modelTraining2 = new ModelTraining();
        modelTraining2.setId(modelTraining1.getId());
        assertThat(modelTraining1).isEqualTo(modelTraining2);
        modelTraining2.setId("id2");
        assertThat(modelTraining1).isNotEqualTo(modelTraining2);
        modelTraining1.setId(null);
        assertThat(modelTraining1).isNotEqualTo(modelTraining2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelTrainingDTO.class);
        ModelTrainingDTO modelTrainingDTO1 = new ModelTrainingDTO();
        modelTrainingDTO1.setId("id1");
        ModelTrainingDTO modelTrainingDTO2 = new ModelTrainingDTO();
        assertThat(modelTrainingDTO1).isNotEqualTo(modelTrainingDTO2);
        modelTrainingDTO2.setId(modelTrainingDTO1.getId());
        assertThat(modelTrainingDTO1).isEqualTo(modelTrainingDTO2);
        modelTrainingDTO2.setId("id2");
        assertThat(modelTrainingDTO1).isNotEqualTo(modelTrainingDTO2);
        modelTrainingDTO1.setId(null);
        assertThat(modelTrainingDTO1).isNotEqualTo(modelTrainingDTO2);
    }
}

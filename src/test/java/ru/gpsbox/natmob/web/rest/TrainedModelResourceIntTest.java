package ru.gpsbox.natmob.web.rest;

import ru.gpsbox.natmob.VkbotApp;

import ru.gpsbox.natmob.domain.TrainedModel;
import ru.gpsbox.natmob.repository.TrainedModelRepository;
import ru.gpsbox.natmob.service.TrainedModelService;
import ru.gpsbox.natmob.repository.search.TrainedModelSearchRepository;
import ru.gpsbox.natmob.service.dto.TrainedModelDTO;
import ru.gpsbox.natmob.service.mapper.TrainedModelMapper;
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
 * Test class for the TrainedModelResource REST controller.
 *
 * @see TrainedModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class TrainedModelResourceIntTest {

    private static final String DEFAULT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSMATRIX = "AAAAAAAAAA";
    private static final String UPDATED_POSMATRIX = "BBBBBBBBBB";

    private static final String DEFAULT_NEGMATRIX = "AAAAAAAAAA";
    private static final String UPDATED_NEGMATRIX = "BBBBBBBBBB";

    private static final String DEFAULT_NEUTMATRIX = "AAAAAAAAAA";
    private static final String UPDATED_NEUTMATRIX = "BBBBBBBBBB";

    private static final Integer DEFAULT_MODELTYPE = 1;
    private static final Integer UPDATED_MODELTYPE = 2;

    @Autowired
    private TrainedModelRepository trainedModelRepository;

    @Autowired
    private TrainedModelMapper trainedModelMapper;

    @Autowired
    private TrainedModelService trainedModelService;

    @Autowired
    private TrainedModelSearchRepository trainedModelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTrainedModelMockMvc;

    private TrainedModel trainedModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainedModelResource trainedModelResource = new TrainedModelResource(trainedModelService);
        this.restTrainedModelMockMvc = MockMvcBuilders.standaloneSetup(trainedModelResource)
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
    public static TrainedModel createEntity() {
        TrainedModel trainedModel = new TrainedModel()
            .modelName(DEFAULT_MODEL_NAME)
            .posmatrix(DEFAULT_POSMATRIX)
            .negmatrix(DEFAULT_NEGMATRIX)
            .neutmatrix(DEFAULT_NEUTMATRIX)
            .modeltype(DEFAULT_MODELTYPE);
        return trainedModel;
    }

    @Before
    public void initTest() {
        trainedModelRepository.deleteAll();
        trainedModelSearchRepository.deleteAll();
        trainedModel = createEntity();
    }

    @Test
    public void createTrainedModel() throws Exception {
        int databaseSizeBeforeCreate = trainedModelRepository.findAll().size();

        // Create the TrainedModel
        TrainedModelDTO trainedModelDTO = trainedModelMapper.toDto(trainedModel);
        restTrainedModelMockMvc.perform(post("/api/trained-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainedModelDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainedModel in the database
        List<TrainedModel> trainedModelList = trainedModelRepository.findAll();
        assertThat(trainedModelList).hasSize(databaseSizeBeforeCreate + 1);
        TrainedModel testTrainedModel = trainedModelList.get(trainedModelList.size() - 1);
        assertThat(testTrainedModel.getModelName()).isEqualTo(DEFAULT_MODEL_NAME);
        assertThat(testTrainedModel.getPosmatrix()).isEqualTo(DEFAULT_POSMATRIX);
        assertThat(testTrainedModel.getNegmatrix()).isEqualTo(DEFAULT_NEGMATRIX);
        assertThat(testTrainedModel.getNeutmatrix()).isEqualTo(DEFAULT_NEUTMATRIX);
        assertThat(testTrainedModel.getModeltype()).isEqualTo(DEFAULT_MODELTYPE);

        // Validate the TrainedModel in Elasticsearch
        TrainedModel trainedModelEs = trainedModelSearchRepository.findOne(testTrainedModel.getId());
        assertThat(trainedModelEs).isEqualToIgnoringGivenFields(testTrainedModel);
    }

    @Test
    public void createTrainedModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainedModelRepository.findAll().size();

        // Create the TrainedModel with an existing ID
        trainedModel.setId("existing_id");
        TrainedModelDTO trainedModelDTO = trainedModelMapper.toDto(trainedModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainedModelMockMvc.perform(post("/api/trained-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainedModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainedModel in the database
        List<TrainedModel> trainedModelList = trainedModelRepository.findAll();
        assertThat(trainedModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkModelNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainedModelRepository.findAll().size();
        // set the field null
        trainedModel.setModelName(null);

        // Create the TrainedModel, which fails.
        TrainedModelDTO trainedModelDTO = trainedModelMapper.toDto(trainedModel);

        restTrainedModelMockMvc.perform(post("/api/trained-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainedModelDTO)))
            .andExpect(status().isBadRequest());

        List<TrainedModel> trainedModelList = trainedModelRepository.findAll();
        assertThat(trainedModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTrainedModels() throws Exception {
        // Initialize the database
        trainedModelRepository.save(trainedModel);

        // Get all the trainedModelList
        restTrainedModelMockMvc.perform(get("/api/trained-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainedModel.getId())))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].posmatrix").value(hasItem(DEFAULT_POSMATRIX.toString())))
            .andExpect(jsonPath("$.[*].negmatrix").value(hasItem(DEFAULT_NEGMATRIX.toString())))
            .andExpect(jsonPath("$.[*].neutmatrix").value(hasItem(DEFAULT_NEUTMATRIX.toString())))
            .andExpect(jsonPath("$.[*].modeltype").value(hasItem(DEFAULT_MODELTYPE)));
    }

    @Test
    public void getTrainedModel() throws Exception {
        // Initialize the database
        trainedModelRepository.save(trainedModel);

        // Get the trainedModel
        restTrainedModelMockMvc.perform(get("/api/trained-models/{id}", trainedModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trainedModel.getId()))
            .andExpect(jsonPath("$.modelName").value(DEFAULT_MODEL_NAME.toString()))
            .andExpect(jsonPath("$.posmatrix").value(DEFAULT_POSMATRIX.toString()))
            .andExpect(jsonPath("$.negmatrix").value(DEFAULT_NEGMATRIX.toString()))
            .andExpect(jsonPath("$.neutmatrix").value(DEFAULT_NEUTMATRIX.toString()))
            .andExpect(jsonPath("$.modeltype").value(DEFAULT_MODELTYPE));
    }

    @Test
    public void getNonExistingTrainedModel() throws Exception {
        // Get the trainedModel
        restTrainedModelMockMvc.perform(get("/api/trained-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTrainedModel() throws Exception {
        // Initialize the database
        trainedModelRepository.save(trainedModel);
        trainedModelSearchRepository.save(trainedModel);
        int databaseSizeBeforeUpdate = trainedModelRepository.findAll().size();

        // Update the trainedModel
        TrainedModel updatedTrainedModel = trainedModelRepository.findOne(trainedModel.getId());
        updatedTrainedModel
            .modelName(UPDATED_MODEL_NAME)
            .posmatrix(UPDATED_POSMATRIX)
            .negmatrix(UPDATED_NEGMATRIX)
            .neutmatrix(UPDATED_NEUTMATRIX)
            .modeltype(UPDATED_MODELTYPE);
        TrainedModelDTO trainedModelDTO = trainedModelMapper.toDto(updatedTrainedModel);

        restTrainedModelMockMvc.perform(put("/api/trained-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainedModelDTO)))
            .andExpect(status().isOk());

        // Validate the TrainedModel in the database
        List<TrainedModel> trainedModelList = trainedModelRepository.findAll();
        assertThat(trainedModelList).hasSize(databaseSizeBeforeUpdate);
        TrainedModel testTrainedModel = trainedModelList.get(trainedModelList.size() - 1);
        assertThat(testTrainedModel.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testTrainedModel.getPosmatrix()).isEqualTo(UPDATED_POSMATRIX);
        assertThat(testTrainedModel.getNegmatrix()).isEqualTo(UPDATED_NEGMATRIX);
        assertThat(testTrainedModel.getNeutmatrix()).isEqualTo(UPDATED_NEUTMATRIX);
        assertThat(testTrainedModel.getModeltype()).isEqualTo(UPDATED_MODELTYPE);

        // Validate the TrainedModel in Elasticsearch
        TrainedModel trainedModelEs = trainedModelSearchRepository.findOne(testTrainedModel.getId());
        assertThat(trainedModelEs).isEqualToIgnoringGivenFields(testTrainedModel);
    }

    @Test
    public void updateNonExistingTrainedModel() throws Exception {
        int databaseSizeBeforeUpdate = trainedModelRepository.findAll().size();

        // Create the TrainedModel
        TrainedModelDTO trainedModelDTO = trainedModelMapper.toDto(trainedModel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrainedModelMockMvc.perform(put("/api/trained-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainedModelDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainedModel in the database
        List<TrainedModel> trainedModelList = trainedModelRepository.findAll();
        assertThat(trainedModelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTrainedModel() throws Exception {
        // Initialize the database
        trainedModelRepository.save(trainedModel);
        trainedModelSearchRepository.save(trainedModel);
        int databaseSizeBeforeDelete = trainedModelRepository.findAll().size();

        // Get the trainedModel
        restTrainedModelMockMvc.perform(delete("/api/trained-models/{id}", trainedModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean trainedModelExistsInEs = trainedModelSearchRepository.exists(trainedModel.getId());
        assertThat(trainedModelExistsInEs).isFalse();

        // Validate the database is empty
        List<TrainedModel> trainedModelList = trainedModelRepository.findAll();
        assertThat(trainedModelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchTrainedModel() throws Exception {
        // Initialize the database
        trainedModelRepository.save(trainedModel);
        trainedModelSearchRepository.save(trainedModel);

        // Search the trainedModel
        restTrainedModelMockMvc.perform(get("/api/_search/trained-models?query=id:" + trainedModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainedModel.getId())))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].posmatrix").value(hasItem(DEFAULT_POSMATRIX.toString())))
            .andExpect(jsonPath("$.[*].negmatrix").value(hasItem(DEFAULT_NEGMATRIX.toString())))
            .andExpect(jsonPath("$.[*].neutmatrix").value(hasItem(DEFAULT_NEUTMATRIX.toString())))
            .andExpect(jsonPath("$.[*].modeltype").value(hasItem(DEFAULT_MODELTYPE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainedModel.class);
        TrainedModel trainedModel1 = new TrainedModel();
        trainedModel1.setId("id1");
        TrainedModel trainedModel2 = new TrainedModel();
        trainedModel2.setId(trainedModel1.getId());
        assertThat(trainedModel1).isEqualTo(trainedModel2);
        trainedModel2.setId("id2");
        assertThat(trainedModel1).isNotEqualTo(trainedModel2);
        trainedModel1.setId(null);
        assertThat(trainedModel1).isNotEqualTo(trainedModel2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainedModelDTO.class);
        TrainedModelDTO trainedModelDTO1 = new TrainedModelDTO();
        trainedModelDTO1.setId("id1");
        TrainedModelDTO trainedModelDTO2 = new TrainedModelDTO();
        assertThat(trainedModelDTO1).isNotEqualTo(trainedModelDTO2);
        trainedModelDTO2.setId(trainedModelDTO1.getId());
        assertThat(trainedModelDTO1).isEqualTo(trainedModelDTO2);
        trainedModelDTO2.setId("id2");
        assertThat(trainedModelDTO1).isNotEqualTo(trainedModelDTO2);
        trainedModelDTO1.setId(null);
        assertThat(trainedModelDTO1).isNotEqualTo(trainedModelDTO2);
    }
}

package ru.gpsbox.vkbot.web.rest;

import ru.gpsbox.vkbot.VkbotApp;

import ru.gpsbox.vkbot.domain.ClarifaiProcess;
import ru.gpsbox.vkbot.repository.ClarifaiProcessRepository;
import ru.gpsbox.vkbot.service.ClarifaiProcessService;
import ru.gpsbox.vkbot.repository.search.ClarifaiProcessSearchRepository;
import ru.gpsbox.vkbot.service.dto.ClarifaiProcessDTO;
import ru.gpsbox.vkbot.service.mapper.ClarifaiProcessMapper;
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
 * Test class for the ClarifaiProcessResource REST controller.
 *
 * @see ClarifaiProcessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class ClarifaiProcessResourceIntTest {

    private static final Integer DEFAULT_PICTID = 1;
    private static final Integer UPDATED_PICTID = 2;

    private static final Integer DEFAULT_PICTKEY = 1;
    private static final Integer UPDATED_PICTKEY = 2;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FIRSTFIVE = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTFIVE = "BBBBBBBBBB";

    private static final Integer DEFAULT_IGNORE = 1;
    private static final Integer UPDATED_IGNORE = 2;

    private static final Integer DEFAULT_PROC = 1;
    private static final Integer UPDATED_PROC = 2;

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_RAWDATA = "AAAAAAAAAA";
    private static final String UPDATED_RAWDATA = "BBBBBBBBBB";

    @Autowired
    private ClarifaiProcessRepository clarifaiProcessRepository;

    @Autowired
    private ClarifaiProcessMapper clarifaiProcessMapper;

    @Autowired
    private ClarifaiProcessService clarifaiProcessService;

    @Autowired
    private ClarifaiProcessSearchRepository clarifaiProcessSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restClarifaiProcessMockMvc;

    private ClarifaiProcess clarifaiProcess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClarifaiProcessResource clarifaiProcessResource = new ClarifaiProcessResource(clarifaiProcessService);
        this.restClarifaiProcessMockMvc = MockMvcBuilders.standaloneSetup(clarifaiProcessResource)
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
    public static ClarifaiProcess createEntity() {
        ClarifaiProcess clarifaiProcess = new ClarifaiProcess()
            .pictid(DEFAULT_PICTID)
            .pictkey(DEFAULT_PICTKEY)
            .url(DEFAULT_URL)
            .firstfive(DEFAULT_FIRSTFIVE)
            .ignore(DEFAULT_IGNORE)
            .proc(DEFAULT_PROC)
            .model(DEFAULT_MODEL)
            .rawdata(DEFAULT_RAWDATA);
        return clarifaiProcess;
    }

    @Before
    public void initTest() {
        clarifaiProcessRepository.deleteAll();
        clarifaiProcessSearchRepository.deleteAll();
        clarifaiProcess = createEntity();
    }

    @Test
    public void createClarifaiProcess() throws Exception {
        int databaseSizeBeforeCreate = clarifaiProcessRepository.findAll().size();

        // Create the ClarifaiProcess
        ClarifaiProcessDTO clarifaiProcessDTO = clarifaiProcessMapper.toDto(clarifaiProcess);
        restClarifaiProcessMockMvc.perform(post("/api/clarifai-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaiProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the ClarifaiProcess in the database
        List<ClarifaiProcess> clarifaiProcessList = clarifaiProcessRepository.findAll();
        assertThat(clarifaiProcessList).hasSize(databaseSizeBeforeCreate + 1);
        ClarifaiProcess testClarifaiProcess = clarifaiProcessList.get(clarifaiProcessList.size() - 1);
        assertThat(testClarifaiProcess.getPictid()).isEqualTo(DEFAULT_PICTID);
        assertThat(testClarifaiProcess.getPictkey()).isEqualTo(DEFAULT_PICTKEY);
        assertThat(testClarifaiProcess.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testClarifaiProcess.getFirstfive()).isEqualTo(DEFAULT_FIRSTFIVE);
        assertThat(testClarifaiProcess.getIgnore()).isEqualTo(DEFAULT_IGNORE);
        assertThat(testClarifaiProcess.getProc()).isEqualTo(DEFAULT_PROC);
        assertThat(testClarifaiProcess.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testClarifaiProcess.getRawdata()).isEqualTo(DEFAULT_RAWDATA);

        // Validate the ClarifaiProcess in Elasticsearch
        ClarifaiProcess clarifaiProcessEs = clarifaiProcessSearchRepository.findOne(testClarifaiProcess.getId());
        assertThat(clarifaiProcessEs).isEqualToIgnoringGivenFields(testClarifaiProcess);
    }

    @Test
    public void createClarifaiProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clarifaiProcessRepository.findAll().size();

        // Create the ClarifaiProcess with an existing ID
        clarifaiProcess.setId("existing_id");
        ClarifaiProcessDTO clarifaiProcessDTO = clarifaiProcessMapper.toDto(clarifaiProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClarifaiProcessMockMvc.perform(post("/api/clarifai-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaiProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClarifaiProcess in the database
        List<ClarifaiProcess> clarifaiProcessList = clarifaiProcessRepository.findAll();
        assertThat(clarifaiProcessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkPictidIsRequired() throws Exception {
        int databaseSizeBeforeTest = clarifaiProcessRepository.findAll().size();
        // set the field null
        clarifaiProcess.setPictid(null);

        // Create the ClarifaiProcess, which fails.
        ClarifaiProcessDTO clarifaiProcessDTO = clarifaiProcessMapper.toDto(clarifaiProcess);

        restClarifaiProcessMockMvc.perform(post("/api/clarifai-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaiProcessDTO)))
            .andExpect(status().isBadRequest());

        List<ClarifaiProcess> clarifaiProcessList = clarifaiProcessRepository.findAll();
        assertThat(clarifaiProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = clarifaiProcessRepository.findAll().size();
        // set the field null
        clarifaiProcess.setUrl(null);

        // Create the ClarifaiProcess, which fails.
        ClarifaiProcessDTO clarifaiProcessDTO = clarifaiProcessMapper.toDto(clarifaiProcess);

        restClarifaiProcessMockMvc.perform(post("/api/clarifai-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaiProcessDTO)))
            .andExpect(status().isBadRequest());

        List<ClarifaiProcess> clarifaiProcessList = clarifaiProcessRepository.findAll();
        assertThat(clarifaiProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllClarifaiProcesses() throws Exception {
        // Initialize the database
        clarifaiProcessRepository.save(clarifaiProcess);

        // Get all the clarifaiProcessList
        restClarifaiProcessMockMvc.perform(get("/api/clarifai-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clarifaiProcess.getId())))
            .andExpect(jsonPath("$.[*].pictid").value(hasItem(DEFAULT_PICTID)))
            .andExpect(jsonPath("$.[*].pictkey").value(hasItem(DEFAULT_PICTKEY)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].firstfive").value(hasItem(DEFAULT_FIRSTFIVE.toString())))
            .andExpect(jsonPath("$.[*].ignore").value(hasItem(DEFAULT_IGNORE)))
            .andExpect(jsonPath("$.[*].proc").value(hasItem(DEFAULT_PROC)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].rawdata").value(hasItem(DEFAULT_RAWDATA.toString())));
    }

    @Test
    public void getClarifaiProcess() throws Exception {
        // Initialize the database
        clarifaiProcessRepository.save(clarifaiProcess);

        // Get the clarifaiProcess
        restClarifaiProcessMockMvc.perform(get("/api/clarifai-processes/{id}", clarifaiProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clarifaiProcess.getId()))
            .andExpect(jsonPath("$.pictid").value(DEFAULT_PICTID))
            .andExpect(jsonPath("$.pictkey").value(DEFAULT_PICTKEY))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.firstfive").value(DEFAULT_FIRSTFIVE.toString()))
            .andExpect(jsonPath("$.ignore").value(DEFAULT_IGNORE))
            .andExpect(jsonPath("$.proc").value(DEFAULT_PROC))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.rawdata").value(DEFAULT_RAWDATA.toString()));
    }

    @Test
    public void getNonExistingClarifaiProcess() throws Exception {
        // Get the clarifaiProcess
        restClarifaiProcessMockMvc.perform(get("/api/clarifai-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClarifaiProcess() throws Exception {
        // Initialize the database
        clarifaiProcessRepository.save(clarifaiProcess);
        clarifaiProcessSearchRepository.save(clarifaiProcess);
        int databaseSizeBeforeUpdate = clarifaiProcessRepository.findAll().size();

        // Update the clarifaiProcess
        ClarifaiProcess updatedClarifaiProcess = clarifaiProcessRepository.findOne(clarifaiProcess.getId());
        updatedClarifaiProcess
            .pictid(UPDATED_PICTID)
            .pictkey(UPDATED_PICTKEY)
            .url(UPDATED_URL)
            .firstfive(UPDATED_FIRSTFIVE)
            .ignore(UPDATED_IGNORE)
            .proc(UPDATED_PROC)
            .model(UPDATED_MODEL)
            .rawdata(UPDATED_RAWDATA);
        ClarifaiProcessDTO clarifaiProcessDTO = clarifaiProcessMapper.toDto(updatedClarifaiProcess);

        restClarifaiProcessMockMvc.perform(put("/api/clarifai-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaiProcessDTO)))
            .andExpect(status().isOk());

        // Validate the ClarifaiProcess in the database
        List<ClarifaiProcess> clarifaiProcessList = clarifaiProcessRepository.findAll();
        assertThat(clarifaiProcessList).hasSize(databaseSizeBeforeUpdate);
        ClarifaiProcess testClarifaiProcess = clarifaiProcessList.get(clarifaiProcessList.size() - 1);
        assertThat(testClarifaiProcess.getPictid()).isEqualTo(UPDATED_PICTID);
        assertThat(testClarifaiProcess.getPictkey()).isEqualTo(UPDATED_PICTKEY);
        assertThat(testClarifaiProcess.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testClarifaiProcess.getFirstfive()).isEqualTo(UPDATED_FIRSTFIVE);
        assertThat(testClarifaiProcess.getIgnore()).isEqualTo(UPDATED_IGNORE);
        assertThat(testClarifaiProcess.getProc()).isEqualTo(UPDATED_PROC);
        assertThat(testClarifaiProcess.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testClarifaiProcess.getRawdata()).isEqualTo(UPDATED_RAWDATA);

        // Validate the ClarifaiProcess in Elasticsearch
        ClarifaiProcess clarifaiProcessEs = clarifaiProcessSearchRepository.findOne(testClarifaiProcess.getId());
        assertThat(clarifaiProcessEs).isEqualToIgnoringGivenFields(testClarifaiProcess);
    }

    @Test
    public void updateNonExistingClarifaiProcess() throws Exception {
        int databaseSizeBeforeUpdate = clarifaiProcessRepository.findAll().size();

        // Create the ClarifaiProcess
        ClarifaiProcessDTO clarifaiProcessDTO = clarifaiProcessMapper.toDto(clarifaiProcess);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClarifaiProcessMockMvc.perform(put("/api/clarifai-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaiProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the ClarifaiProcess in the database
        List<ClarifaiProcess> clarifaiProcessList = clarifaiProcessRepository.findAll();
        assertThat(clarifaiProcessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteClarifaiProcess() throws Exception {
        // Initialize the database
        clarifaiProcessRepository.save(clarifaiProcess);
        clarifaiProcessSearchRepository.save(clarifaiProcess);
        int databaseSizeBeforeDelete = clarifaiProcessRepository.findAll().size();

        // Get the clarifaiProcess
        restClarifaiProcessMockMvc.perform(delete("/api/clarifai-processes/{id}", clarifaiProcess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean clarifaiProcessExistsInEs = clarifaiProcessSearchRepository.exists(clarifaiProcess.getId());
        assertThat(clarifaiProcessExistsInEs).isFalse();

        // Validate the database is empty
        List<ClarifaiProcess> clarifaiProcessList = clarifaiProcessRepository.findAll();
        assertThat(clarifaiProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchClarifaiProcess() throws Exception {
        // Initialize the database
        clarifaiProcessRepository.save(clarifaiProcess);
        clarifaiProcessSearchRepository.save(clarifaiProcess);

        // Search the clarifaiProcess
        restClarifaiProcessMockMvc.perform(get("/api/_search/clarifai-processes?query=id:" + clarifaiProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clarifaiProcess.getId())))
            .andExpect(jsonPath("$.[*].pictid").value(hasItem(DEFAULT_PICTID)))
            .andExpect(jsonPath("$.[*].pictkey").value(hasItem(DEFAULT_PICTKEY)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].firstfive").value(hasItem(DEFAULT_FIRSTFIVE.toString())))
            .andExpect(jsonPath("$.[*].ignore").value(hasItem(DEFAULT_IGNORE)))
            .andExpect(jsonPath("$.[*].proc").value(hasItem(DEFAULT_PROC)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].rawdata").value(hasItem(DEFAULT_RAWDATA.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClarifaiProcess.class);
        ClarifaiProcess clarifaiProcess1 = new ClarifaiProcess();
        clarifaiProcess1.setId("id1");
        ClarifaiProcess clarifaiProcess2 = new ClarifaiProcess();
        clarifaiProcess2.setId(clarifaiProcess1.getId());
        assertThat(clarifaiProcess1).isEqualTo(clarifaiProcess2);
        clarifaiProcess2.setId("id2");
        assertThat(clarifaiProcess1).isNotEqualTo(clarifaiProcess2);
        clarifaiProcess1.setId(null);
        assertThat(clarifaiProcess1).isNotEqualTo(clarifaiProcess2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClarifaiProcessDTO.class);
        ClarifaiProcessDTO clarifaiProcessDTO1 = new ClarifaiProcessDTO();
        clarifaiProcessDTO1.setId("id1");
        ClarifaiProcessDTO clarifaiProcessDTO2 = new ClarifaiProcessDTO();
        assertThat(clarifaiProcessDTO1).isNotEqualTo(clarifaiProcessDTO2);
        clarifaiProcessDTO2.setId(clarifaiProcessDTO1.getId());
        assertThat(clarifaiProcessDTO1).isEqualTo(clarifaiProcessDTO2);
        clarifaiProcessDTO2.setId("id2");
        assertThat(clarifaiProcessDTO1).isNotEqualTo(clarifaiProcessDTO2);
        clarifaiProcessDTO1.setId(null);
        assertThat(clarifaiProcessDTO1).isNotEqualTo(clarifaiProcessDTO2);
    }
}

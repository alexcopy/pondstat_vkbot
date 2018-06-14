package ru.gpsbox.natmob.web.rest;

import ru.gpsbox.natmob.VkbotApp;

import ru.gpsbox.natmob.domain.VkPicture;
import ru.gpsbox.natmob.repository.VkPictureRepository;
import ru.gpsbox.natmob.service.VkPictureService;
import ru.gpsbox.natmob.repository.search.VkPictureSearchRepository;
import ru.gpsbox.natmob.service.dto.VkPictureDTO;
import ru.gpsbox.natmob.service.mapper.VkPictureMapper;
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
 * Test class for the VkPictureResource REST controller.
 *
 * @see VkPictureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class VkPictureResourceIntTest {

    private static final String DEFAULT_PICT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PICT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_OWNER_ID = 1;
    private static final Integer UPDATED_OWNER_ID = 2;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROC = 1;
    private static final Integer UPDATED_PROC = 2;

    private static final Integer DEFAULT_DOWNLOADED = 1;
    private static final Integer UPDATED_DOWNLOADED = 2;

    private static final Integer DEFAULT_DELETERANK = 1;
    private static final Integer UPDATED_DELETERANK = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROCID = 1;
    private static final Integer UPDATED_PROCID = 2;

    private static final Integer DEFAULT_RECOGNIZED = 1;
    private static final Integer UPDATED_RECOGNIZED = 2;

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    private static final Integer DEFAULT_PICDATE = 1;
    private static final Integer UPDATED_PICDATE = 2;

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIKES = 1;
    private static final Integer UPDATED_LIKES = 2;

    private static final Integer DEFAULT_IGNORED = 1;
    private static final Integer UPDATED_IGNORED = 2;

    @Autowired
    private VkPictureRepository vkPictureRepository;

    @Autowired
    private VkPictureMapper vkPictureMapper;

    @Autowired
    private VkPictureService vkPictureService;

    @Autowired
    private VkPictureSearchRepository vkPictureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVkPictureMockMvc;

    private VkPicture vkPicture;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VkPictureResource vkPictureResource = new VkPictureResource(vkPictureService);
        this.restVkPictureMockMvc = MockMvcBuilders.standaloneSetup(vkPictureResource)
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
    public static VkPicture createEntity() {
        VkPicture vkPicture = new VkPicture()
            .pictId(DEFAULT_PICT_ID)
            .ownerId(DEFAULT_OWNER_ID)
            .url(DEFAULT_URL)
            .proc(DEFAULT_PROC)
            .downloaded(DEFAULT_DOWNLOADED)
            .deleterank(DEFAULT_DELETERANK)
            .type(DEFAULT_TYPE)
            .procid(DEFAULT_PROCID)
            .recognized(DEFAULT_RECOGNIZED)
            .timestamp(DEFAULT_TIMESTAMP)
            .picdate(DEFAULT_PICDATE)
            .size(DEFAULT_SIZE)
            .text(DEFAULT_TEXT)
            .likes(DEFAULT_LIKES)
            .ignored(DEFAULT_IGNORED);
        return vkPicture;
    }

    @Before
    public void initTest() {
        vkPictureRepository.deleteAll();
        vkPictureSearchRepository.deleteAll();
        vkPicture = createEntity();
    }

    @Test
    public void createVkPicture() throws Exception {
        int databaseSizeBeforeCreate = vkPictureRepository.findAll().size();

        // Create the VkPicture
        VkPictureDTO vkPictureDTO = vkPictureMapper.toDto(vkPicture);
        restVkPictureMockMvc.perform(post("/api/vk-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkPictureDTO)))
            .andExpect(status().isCreated());

        // Validate the VkPicture in the database
        List<VkPicture> vkPictureList = vkPictureRepository.findAll();
        assertThat(vkPictureList).hasSize(databaseSizeBeforeCreate + 1);
        VkPicture testVkPicture = vkPictureList.get(vkPictureList.size() - 1);
        assertThat(testVkPicture.getPictId()).isEqualTo(DEFAULT_PICT_ID);
        assertThat(testVkPicture.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testVkPicture.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testVkPicture.getProc()).isEqualTo(DEFAULT_PROC);
        assertThat(testVkPicture.getDownloaded()).isEqualTo(DEFAULT_DOWNLOADED);
        assertThat(testVkPicture.getDeleterank()).isEqualTo(DEFAULT_DELETERANK);
        assertThat(testVkPicture.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVkPicture.getProcid()).isEqualTo(DEFAULT_PROCID);
        assertThat(testVkPicture.getRecognized()).isEqualTo(DEFAULT_RECOGNIZED);
        assertThat(testVkPicture.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testVkPicture.getPicdate()).isEqualTo(DEFAULT_PICDATE);
        assertThat(testVkPicture.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testVkPicture.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testVkPicture.getLikes()).isEqualTo(DEFAULT_LIKES);
        assertThat(testVkPicture.getIgnored()).isEqualTo(DEFAULT_IGNORED);

        // Validate the VkPicture in Elasticsearch
        VkPicture vkPictureEs = vkPictureSearchRepository.findOne(testVkPicture.getId());
        assertThat(vkPictureEs).isEqualToIgnoringGivenFields(testVkPicture);
    }

    @Test
    public void createVkPictureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vkPictureRepository.findAll().size();

        // Create the VkPicture with an existing ID
        vkPicture.setId("existing_id");
        VkPictureDTO vkPictureDTO = vkPictureMapper.toDto(vkPicture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVkPictureMockMvc.perform(post("/api/vk-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkPictureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VkPicture in the database
        List<VkPicture> vkPictureList = vkPictureRepository.findAll();
        assertThat(vkPictureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkPictIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkPictureRepository.findAll().size();
        // set the field null
        vkPicture.setPictId(null);

        // Create the VkPicture, which fails.
        VkPictureDTO vkPictureDTO = vkPictureMapper.toDto(vkPicture);

        restVkPictureMockMvc.perform(post("/api/vk-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkPictureDTO)))
            .andExpect(status().isBadRequest());

        List<VkPicture> vkPictureList = vkPictureRepository.findAll();
        assertThat(vkPictureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOwnerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkPictureRepository.findAll().size();
        // set the field null
        vkPicture.setOwnerId(null);

        // Create the VkPicture, which fails.
        VkPictureDTO vkPictureDTO = vkPictureMapper.toDto(vkPicture);

        restVkPictureMockMvc.perform(post("/api/vk-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkPictureDTO)))
            .andExpect(status().isBadRequest());

        List<VkPicture> vkPictureList = vkPictureRepository.findAll();
        assertThat(vkPictureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkPictureRepository.findAll().size();
        // set the field null
        vkPicture.setUrl(null);

        // Create the VkPicture, which fails.
        VkPictureDTO vkPictureDTO = vkPictureMapper.toDto(vkPicture);

        restVkPictureMockMvc.perform(post("/api/vk-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkPictureDTO)))
            .andExpect(status().isBadRequest());

        List<VkPicture> vkPictureList = vkPictureRepository.findAll();
        assertThat(vkPictureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVkPictures() throws Exception {
        // Initialize the database
        vkPictureRepository.save(vkPicture);

        // Get all the vkPictureList
        restVkPictureMockMvc.perform(get("/api/vk-pictures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vkPicture.getId())))
            .andExpect(jsonPath("$.[*].pictId").value(hasItem(DEFAULT_PICT_ID.toString())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].proc").value(hasItem(DEFAULT_PROC)))
            .andExpect(jsonPath("$.[*].downloaded").value(hasItem(DEFAULT_DOWNLOADED)))
            .andExpect(jsonPath("$.[*].deleterank").value(hasItem(DEFAULT_DELETERANK)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].procid").value(hasItem(DEFAULT_PROCID)))
            .andExpect(jsonPath("$.[*].recognized").value(hasItem(DEFAULT_RECOGNIZED)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].picdate").value(hasItem(DEFAULT_PICDATE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].likes").value(hasItem(DEFAULT_LIKES)))
            .andExpect(jsonPath("$.[*].ignored").value(hasItem(DEFAULT_IGNORED)));
    }

    @Test
    public void getVkPicture() throws Exception {
        // Initialize the database
        vkPictureRepository.save(vkPicture);

        // Get the vkPicture
        restVkPictureMockMvc.perform(get("/api/vk-pictures/{id}", vkPicture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vkPicture.getId()))
            .andExpect(jsonPath("$.pictId").value(DEFAULT_PICT_ID.toString()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.proc").value(DEFAULT_PROC))
            .andExpect(jsonPath("$.downloaded").value(DEFAULT_DOWNLOADED))
            .andExpect(jsonPath("$.deleterank").value(DEFAULT_DELETERANK))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.procid").value(DEFAULT_PROCID))
            .andExpect(jsonPath("$.recognized").value(DEFAULT_RECOGNIZED))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP))
            .andExpect(jsonPath("$.picdate").value(DEFAULT_PICDATE))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.likes").value(DEFAULT_LIKES))
            .andExpect(jsonPath("$.ignored").value(DEFAULT_IGNORED));
    }

    @Test
    public void getNonExistingVkPicture() throws Exception {
        // Get the vkPicture
        restVkPictureMockMvc.perform(get("/api/vk-pictures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVkPicture() throws Exception {
        // Initialize the database
        vkPictureRepository.save(vkPicture);
        vkPictureSearchRepository.save(vkPicture);
        int databaseSizeBeforeUpdate = vkPictureRepository.findAll().size();

        // Update the vkPicture
        VkPicture updatedVkPicture = vkPictureRepository.findOne(vkPicture.getId());
        updatedVkPicture
            .pictId(UPDATED_PICT_ID)
            .ownerId(UPDATED_OWNER_ID)
            .url(UPDATED_URL)
            .proc(UPDATED_PROC)
            .downloaded(UPDATED_DOWNLOADED)
            .deleterank(UPDATED_DELETERANK)
            .type(UPDATED_TYPE)
            .procid(UPDATED_PROCID)
            .recognized(UPDATED_RECOGNIZED)
            .timestamp(UPDATED_TIMESTAMP)
            .picdate(UPDATED_PICDATE)
            .size(UPDATED_SIZE)
            .text(UPDATED_TEXT)
            .likes(UPDATED_LIKES)
            .ignored(UPDATED_IGNORED);
        VkPictureDTO vkPictureDTO = vkPictureMapper.toDto(updatedVkPicture);

        restVkPictureMockMvc.perform(put("/api/vk-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkPictureDTO)))
            .andExpect(status().isOk());

        // Validate the VkPicture in the database
        List<VkPicture> vkPictureList = vkPictureRepository.findAll();
        assertThat(vkPictureList).hasSize(databaseSizeBeforeUpdate);
        VkPicture testVkPicture = vkPictureList.get(vkPictureList.size() - 1);
        assertThat(testVkPicture.getPictId()).isEqualTo(UPDATED_PICT_ID);
        assertThat(testVkPicture.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testVkPicture.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testVkPicture.getProc()).isEqualTo(UPDATED_PROC);
        assertThat(testVkPicture.getDownloaded()).isEqualTo(UPDATED_DOWNLOADED);
        assertThat(testVkPicture.getDeleterank()).isEqualTo(UPDATED_DELETERANK);
        assertThat(testVkPicture.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVkPicture.getProcid()).isEqualTo(UPDATED_PROCID);
        assertThat(testVkPicture.getRecognized()).isEqualTo(UPDATED_RECOGNIZED);
        assertThat(testVkPicture.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testVkPicture.getPicdate()).isEqualTo(UPDATED_PICDATE);
        assertThat(testVkPicture.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testVkPicture.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testVkPicture.getLikes()).isEqualTo(UPDATED_LIKES);
        assertThat(testVkPicture.getIgnored()).isEqualTo(UPDATED_IGNORED);

        // Validate the VkPicture in Elasticsearch
        VkPicture vkPictureEs = vkPictureSearchRepository.findOne(testVkPicture.getId());
        assertThat(vkPictureEs).isEqualToIgnoringGivenFields(testVkPicture);
    }

    @Test
    public void updateNonExistingVkPicture() throws Exception {
        int databaseSizeBeforeUpdate = vkPictureRepository.findAll().size();

        // Create the VkPicture
        VkPictureDTO vkPictureDTO = vkPictureMapper.toDto(vkPicture);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVkPictureMockMvc.perform(put("/api/vk-pictures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkPictureDTO)))
            .andExpect(status().isCreated());

        // Validate the VkPicture in the database
        List<VkPicture> vkPictureList = vkPictureRepository.findAll();
        assertThat(vkPictureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVkPicture() throws Exception {
        // Initialize the database
        vkPictureRepository.save(vkPicture);
        vkPictureSearchRepository.save(vkPicture);
        int databaseSizeBeforeDelete = vkPictureRepository.findAll().size();

        // Get the vkPicture
        restVkPictureMockMvc.perform(delete("/api/vk-pictures/{id}", vkPicture.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vkPictureExistsInEs = vkPictureSearchRepository.exists(vkPicture.getId());
        assertThat(vkPictureExistsInEs).isFalse();

        // Validate the database is empty
        List<VkPicture> vkPictureList = vkPictureRepository.findAll();
        assertThat(vkPictureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchVkPicture() throws Exception {
        // Initialize the database
        vkPictureRepository.save(vkPicture);
        vkPictureSearchRepository.save(vkPicture);

        // Search the vkPicture
        restVkPictureMockMvc.perform(get("/api/_search/vk-pictures?query=id:" + vkPicture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vkPicture.getId())))
            .andExpect(jsonPath("$.[*].pictId").value(hasItem(DEFAULT_PICT_ID.toString())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].proc").value(hasItem(DEFAULT_PROC)))
            .andExpect(jsonPath("$.[*].downloaded").value(hasItem(DEFAULT_DOWNLOADED)))
            .andExpect(jsonPath("$.[*].deleterank").value(hasItem(DEFAULT_DELETERANK)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].procid").value(hasItem(DEFAULT_PROCID)))
            .andExpect(jsonPath("$.[*].recognized").value(hasItem(DEFAULT_RECOGNIZED)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].picdate").value(hasItem(DEFAULT_PICDATE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].likes").value(hasItem(DEFAULT_LIKES)))
            .andExpect(jsonPath("$.[*].ignored").value(hasItem(DEFAULT_IGNORED)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VkPicture.class);
        VkPicture vkPicture1 = new VkPicture();
        vkPicture1.setId("id1");
        VkPicture vkPicture2 = new VkPicture();
        vkPicture2.setId(vkPicture1.getId());
        assertThat(vkPicture1).isEqualTo(vkPicture2);
        vkPicture2.setId("id2");
        assertThat(vkPicture1).isNotEqualTo(vkPicture2);
        vkPicture1.setId(null);
        assertThat(vkPicture1).isNotEqualTo(vkPicture2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VkPictureDTO.class);
        VkPictureDTO vkPictureDTO1 = new VkPictureDTO();
        vkPictureDTO1.setId("id1");
        VkPictureDTO vkPictureDTO2 = new VkPictureDTO();
        assertThat(vkPictureDTO1).isNotEqualTo(vkPictureDTO2);
        vkPictureDTO2.setId(vkPictureDTO1.getId());
        assertThat(vkPictureDTO1).isEqualTo(vkPictureDTO2);
        vkPictureDTO2.setId("id2");
        assertThat(vkPictureDTO1).isNotEqualTo(vkPictureDTO2);
        vkPictureDTO1.setId(null);
        assertThat(vkPictureDTO1).isNotEqualTo(vkPictureDTO2);
    }
}

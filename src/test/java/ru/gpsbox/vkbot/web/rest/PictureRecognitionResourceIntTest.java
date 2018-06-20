package ru.gpsbox.vkbot.web.rest;

import ru.gpsbox.vkbot.VkbotApp;

import ru.gpsbox.vkbot.domain.PictureRecognition;
import ru.gpsbox.vkbot.repository.PictureRecognitionRepository;
import ru.gpsbox.vkbot.service.PictureRecognitionService;
import ru.gpsbox.vkbot.repository.search.PictureRecognitionSearchRepository;
import ru.gpsbox.vkbot.service.dto.PictureRecognitionDTO;
import ru.gpsbox.vkbot.service.mapper.PictureRecognitionMapper;
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
 * Test class for the PictureRecognitionResource REST controller.
 *
 * @see PictureRecognitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class PictureRecognitionResourceIntTest {

    private static final Integer DEFAULT_PICTID = 1;
    private static final Integer UPDATED_PICTID = 2;

    private static final Long DEFAULT_MODELID = 1L;
    private static final Long UPDATED_MODELID = 2L;

    private static final Integer DEFAULT_SIMPRES = 1;
    private static final Integer UPDATED_SIMPRES = 2;

    private static final Integer DEFAULT_MEDRES = 1;
    private static final Integer UPDATED_MEDRES = 2;

    private static final Integer DEFAULT_IGNORE = 1;
    private static final Integer UPDATED_IGNORE = 2;

    private static final Integer DEFAULT_ISSELECTED = 1;
    private static final Integer UPDATED_ISSELECTED = 2;

    private static final Integer DEFAULT_ISMANUAL = 1;
    private static final Integer UPDATED_ISMANUAL = 2;

    @Autowired
    private PictureRecognitionRepository pictureRecognitionRepository;

    @Autowired
    private PictureRecognitionMapper pictureRecognitionMapper;

    @Autowired
    private PictureRecognitionService pictureRecognitionService;

    @Autowired
    private PictureRecognitionSearchRepository pictureRecognitionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPictureRecognitionMockMvc;

    private PictureRecognition pictureRecognition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PictureRecognitionResource pictureRecognitionResource = new PictureRecognitionResource(pictureRecognitionService);
        this.restPictureRecognitionMockMvc = MockMvcBuilders.standaloneSetup(pictureRecognitionResource)
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
    public static PictureRecognition createEntity() {
        PictureRecognition pictureRecognition = new PictureRecognition()
            .pictid(DEFAULT_PICTID)
            .modelid(DEFAULT_MODELID)
            .simpres(DEFAULT_SIMPRES)
            .medres(DEFAULT_MEDRES)
            .ignore(DEFAULT_IGNORE)
            .isselected(DEFAULT_ISSELECTED)
            .ismanual(DEFAULT_ISMANUAL);
        return pictureRecognition;
    }

    @Before
    public void initTest() {
        pictureRecognitionRepository.deleteAll();
        pictureRecognitionSearchRepository.deleteAll();
        pictureRecognition = createEntity();
    }

    @Test
    public void createPictureRecognition() throws Exception {
        int databaseSizeBeforeCreate = pictureRecognitionRepository.findAll().size();

        // Create the PictureRecognition
        PictureRecognitionDTO pictureRecognitionDTO = pictureRecognitionMapper.toDto(pictureRecognition);
        restPictureRecognitionMockMvc.perform(post("/api/picture-recognitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pictureRecognitionDTO)))
            .andExpect(status().isCreated());

        // Validate the PictureRecognition in the database
        List<PictureRecognition> pictureRecognitionList = pictureRecognitionRepository.findAll();
        assertThat(pictureRecognitionList).hasSize(databaseSizeBeforeCreate + 1);
        PictureRecognition testPictureRecognition = pictureRecognitionList.get(pictureRecognitionList.size() - 1);
        assertThat(testPictureRecognition.getPictid()).isEqualTo(DEFAULT_PICTID);
        assertThat(testPictureRecognition.getModelid()).isEqualTo(DEFAULT_MODELID);
        assertThat(testPictureRecognition.getSimpres()).isEqualTo(DEFAULT_SIMPRES);
        assertThat(testPictureRecognition.getMedres()).isEqualTo(DEFAULT_MEDRES);
        assertThat(testPictureRecognition.getIgnore()).isEqualTo(DEFAULT_IGNORE);
        assertThat(testPictureRecognition.getIsselected()).isEqualTo(DEFAULT_ISSELECTED);
        assertThat(testPictureRecognition.getIsmanual()).isEqualTo(DEFAULT_ISMANUAL);

        // Validate the PictureRecognition in Elasticsearch
        PictureRecognition pictureRecognitionEs = pictureRecognitionSearchRepository.findOne(testPictureRecognition.getId());
        assertThat(pictureRecognitionEs).isEqualToIgnoringGivenFields(testPictureRecognition);
    }

    @Test
    public void createPictureRecognitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pictureRecognitionRepository.findAll().size();

        // Create the PictureRecognition with an existing ID
        pictureRecognition.setId("existing_id");
        PictureRecognitionDTO pictureRecognitionDTO = pictureRecognitionMapper.toDto(pictureRecognition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPictureRecognitionMockMvc.perform(post("/api/picture-recognitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pictureRecognitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PictureRecognition in the database
        List<PictureRecognition> pictureRecognitionList = pictureRecognitionRepository.findAll();
        assertThat(pictureRecognitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPictureRecognitions() throws Exception {
        // Initialize the database
        pictureRecognitionRepository.save(pictureRecognition);

        // Get all the pictureRecognitionList
        restPictureRecognitionMockMvc.perform(get("/api/picture-recognitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pictureRecognition.getId())))
            .andExpect(jsonPath("$.[*].pictid").value(hasItem(DEFAULT_PICTID)))
            .andExpect(jsonPath("$.[*].modelid").value(hasItem(DEFAULT_MODELID.intValue())))
            .andExpect(jsonPath("$.[*].simpres").value(hasItem(DEFAULT_SIMPRES)))
            .andExpect(jsonPath("$.[*].medres").value(hasItem(DEFAULT_MEDRES)))
            .andExpect(jsonPath("$.[*].ignore").value(hasItem(DEFAULT_IGNORE)))
            .andExpect(jsonPath("$.[*].isselected").value(hasItem(DEFAULT_ISSELECTED)))
            .andExpect(jsonPath("$.[*].ismanual").value(hasItem(DEFAULT_ISMANUAL)));
    }

    @Test
    public void getPictureRecognition() throws Exception {
        // Initialize the database
        pictureRecognitionRepository.save(pictureRecognition);

        // Get the pictureRecognition
        restPictureRecognitionMockMvc.perform(get("/api/picture-recognitions/{id}", pictureRecognition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pictureRecognition.getId()))
            .andExpect(jsonPath("$.pictid").value(DEFAULT_PICTID))
            .andExpect(jsonPath("$.modelid").value(DEFAULT_MODELID.intValue()))
            .andExpect(jsonPath("$.simpres").value(DEFAULT_SIMPRES))
            .andExpect(jsonPath("$.medres").value(DEFAULT_MEDRES))
            .andExpect(jsonPath("$.ignore").value(DEFAULT_IGNORE))
            .andExpect(jsonPath("$.isselected").value(DEFAULT_ISSELECTED))
            .andExpect(jsonPath("$.ismanual").value(DEFAULT_ISMANUAL));
    }

    @Test
    public void getNonExistingPictureRecognition() throws Exception {
        // Get the pictureRecognition
        restPictureRecognitionMockMvc.perform(get("/api/picture-recognitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePictureRecognition() throws Exception {
        // Initialize the database
        pictureRecognitionRepository.save(pictureRecognition);
        pictureRecognitionSearchRepository.save(pictureRecognition);
        int databaseSizeBeforeUpdate = pictureRecognitionRepository.findAll().size();

        // Update the pictureRecognition
        PictureRecognition updatedPictureRecognition = pictureRecognitionRepository.findOne(pictureRecognition.getId());
        updatedPictureRecognition
            .pictid(UPDATED_PICTID)
            .modelid(UPDATED_MODELID)
            .simpres(UPDATED_SIMPRES)
            .medres(UPDATED_MEDRES)
            .ignore(UPDATED_IGNORE)
            .isselected(UPDATED_ISSELECTED)
            .ismanual(UPDATED_ISMANUAL);
        PictureRecognitionDTO pictureRecognitionDTO = pictureRecognitionMapper.toDto(updatedPictureRecognition);

        restPictureRecognitionMockMvc.perform(put("/api/picture-recognitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pictureRecognitionDTO)))
            .andExpect(status().isOk());

        // Validate the PictureRecognition in the database
        List<PictureRecognition> pictureRecognitionList = pictureRecognitionRepository.findAll();
        assertThat(pictureRecognitionList).hasSize(databaseSizeBeforeUpdate);
        PictureRecognition testPictureRecognition = pictureRecognitionList.get(pictureRecognitionList.size() - 1);
        assertThat(testPictureRecognition.getPictid()).isEqualTo(UPDATED_PICTID);
        assertThat(testPictureRecognition.getModelid()).isEqualTo(UPDATED_MODELID);
        assertThat(testPictureRecognition.getSimpres()).isEqualTo(UPDATED_SIMPRES);
        assertThat(testPictureRecognition.getMedres()).isEqualTo(UPDATED_MEDRES);
        assertThat(testPictureRecognition.getIgnore()).isEqualTo(UPDATED_IGNORE);
        assertThat(testPictureRecognition.getIsselected()).isEqualTo(UPDATED_ISSELECTED);
        assertThat(testPictureRecognition.getIsmanual()).isEqualTo(UPDATED_ISMANUAL);

        // Validate the PictureRecognition in Elasticsearch
        PictureRecognition pictureRecognitionEs = pictureRecognitionSearchRepository.findOne(testPictureRecognition.getId());
        assertThat(pictureRecognitionEs).isEqualToIgnoringGivenFields(testPictureRecognition);
    }

    @Test
    public void updateNonExistingPictureRecognition() throws Exception {
        int databaseSizeBeforeUpdate = pictureRecognitionRepository.findAll().size();

        // Create the PictureRecognition
        PictureRecognitionDTO pictureRecognitionDTO = pictureRecognitionMapper.toDto(pictureRecognition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPictureRecognitionMockMvc.perform(put("/api/picture-recognitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pictureRecognitionDTO)))
            .andExpect(status().isCreated());

        // Validate the PictureRecognition in the database
        List<PictureRecognition> pictureRecognitionList = pictureRecognitionRepository.findAll();
        assertThat(pictureRecognitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePictureRecognition() throws Exception {
        // Initialize the database
        pictureRecognitionRepository.save(pictureRecognition);
        pictureRecognitionSearchRepository.save(pictureRecognition);
        int databaseSizeBeforeDelete = pictureRecognitionRepository.findAll().size();

        // Get the pictureRecognition
        restPictureRecognitionMockMvc.perform(delete("/api/picture-recognitions/{id}", pictureRecognition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pictureRecognitionExistsInEs = pictureRecognitionSearchRepository.exists(pictureRecognition.getId());
        assertThat(pictureRecognitionExistsInEs).isFalse();

        // Validate the database is empty
        List<PictureRecognition> pictureRecognitionList = pictureRecognitionRepository.findAll();
        assertThat(pictureRecognitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchPictureRecognition() throws Exception {
        // Initialize the database
        pictureRecognitionRepository.save(pictureRecognition);
        pictureRecognitionSearchRepository.save(pictureRecognition);

        // Search the pictureRecognition
        restPictureRecognitionMockMvc.perform(get("/api/_search/picture-recognitions?query=id:" + pictureRecognition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pictureRecognition.getId())))
            .andExpect(jsonPath("$.[*].pictid").value(hasItem(DEFAULT_PICTID)))
            .andExpect(jsonPath("$.[*].modelid").value(hasItem(DEFAULT_MODELID.intValue())))
            .andExpect(jsonPath("$.[*].simpres").value(hasItem(DEFAULT_SIMPRES)))
            .andExpect(jsonPath("$.[*].medres").value(hasItem(DEFAULT_MEDRES)))
            .andExpect(jsonPath("$.[*].ignore").value(hasItem(DEFAULT_IGNORE)))
            .andExpect(jsonPath("$.[*].isselected").value(hasItem(DEFAULT_ISSELECTED)))
            .andExpect(jsonPath("$.[*].ismanual").value(hasItem(DEFAULT_ISMANUAL)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PictureRecognition.class);
        PictureRecognition pictureRecognition1 = new PictureRecognition();
        pictureRecognition1.setId("id1");
        PictureRecognition pictureRecognition2 = new PictureRecognition();
        pictureRecognition2.setId(pictureRecognition1.getId());
        assertThat(pictureRecognition1).isEqualTo(pictureRecognition2);
        pictureRecognition2.setId("id2");
        assertThat(pictureRecognition1).isNotEqualTo(pictureRecognition2);
        pictureRecognition1.setId(null);
        assertThat(pictureRecognition1).isNotEqualTo(pictureRecognition2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PictureRecognitionDTO.class);
        PictureRecognitionDTO pictureRecognitionDTO1 = new PictureRecognitionDTO();
        pictureRecognitionDTO1.setId("id1");
        PictureRecognitionDTO pictureRecognitionDTO2 = new PictureRecognitionDTO();
        assertThat(pictureRecognitionDTO1).isNotEqualTo(pictureRecognitionDTO2);
        pictureRecognitionDTO2.setId(pictureRecognitionDTO1.getId());
        assertThat(pictureRecognitionDTO1).isEqualTo(pictureRecognitionDTO2);
        pictureRecognitionDTO2.setId("id2");
        assertThat(pictureRecognitionDTO1).isNotEqualTo(pictureRecognitionDTO2);
        pictureRecognitionDTO1.setId(null);
        assertThat(pictureRecognitionDTO1).isNotEqualTo(pictureRecognitionDTO2);
    }
}

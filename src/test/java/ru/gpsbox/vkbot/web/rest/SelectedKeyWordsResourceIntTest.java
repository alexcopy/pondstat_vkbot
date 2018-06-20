package ru.gpsbox.vkbot.web.rest;

import ru.gpsbox.vkbot.VkbotApp;

import ru.gpsbox.vkbot.domain.SelectedKeyWords;
import ru.gpsbox.vkbot.repository.SelectedKeyWordsRepository;
import ru.gpsbox.vkbot.service.SelectedKeyWordsService;
import ru.gpsbox.vkbot.repository.search.SelectedKeyWordsSearchRepository;
import ru.gpsbox.vkbot.service.dto.SelectedKeyWordsDTO;
import ru.gpsbox.vkbot.service.mapper.SelectedKeyWordsMapper;
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
 * Test class for the SelectedKeyWordsResource REST controller.
 *
 * @see SelectedKeyWordsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class SelectedKeyWordsResourceIntTest {

    private static final Long DEFAULT_MODELID = 1L;
    private static final Long UPDATED_MODELID = 2L;

    private static final String DEFAULT_POSMATRIX = "AAAAAAAAAA";
    private static final String UPDATED_POSMATRIX = "BBBBBBBBBB";

    private static final String DEFAULT_NEUTMATRIX = "AAAAAAAAAA";
    private static final String UPDATED_NEUTMATRIX = "BBBBBBBBBB";

    private static final String DEFAULT_NEGMATRIX = "AAAAAAAAAA";
    private static final String UPDATED_NEGMATRIX = "BBBBBBBBBB";

    @Autowired
    private SelectedKeyWordsRepository selectedKeyWordsRepository;

    @Autowired
    private SelectedKeyWordsMapper selectedKeyWordsMapper;

    @Autowired
    private SelectedKeyWordsService selectedKeyWordsService;

    @Autowired
    private SelectedKeyWordsSearchRepository selectedKeyWordsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSelectedKeyWordsMockMvc;

    private SelectedKeyWords selectedKeyWords;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SelectedKeyWordsResource selectedKeyWordsResource = new SelectedKeyWordsResource(selectedKeyWordsService);
        this.restSelectedKeyWordsMockMvc = MockMvcBuilders.standaloneSetup(selectedKeyWordsResource)
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
    public static SelectedKeyWords createEntity() {
        SelectedKeyWords selectedKeyWords = new SelectedKeyWords()
            .modelid(DEFAULT_MODELID)
            .posmatrix(DEFAULT_POSMATRIX)
            .neutmatrix(DEFAULT_NEUTMATRIX)
            .negmatrix(DEFAULT_NEGMATRIX);
        return selectedKeyWords;
    }

    @Before
    public void initTest() {
        selectedKeyWordsRepository.deleteAll();
        selectedKeyWordsSearchRepository.deleteAll();
        selectedKeyWords = createEntity();
    }

    @Test
    public void createSelectedKeyWords() throws Exception {
        int databaseSizeBeforeCreate = selectedKeyWordsRepository.findAll().size();

        // Create the SelectedKeyWords
        SelectedKeyWordsDTO selectedKeyWordsDTO = selectedKeyWordsMapper.toDto(selectedKeyWords);
        restSelectedKeyWordsMockMvc.perform(post("/api/selected-key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectedKeyWordsDTO)))
            .andExpect(status().isCreated());

        // Validate the SelectedKeyWords in the database
        List<SelectedKeyWords> selectedKeyWordsList = selectedKeyWordsRepository.findAll();
        assertThat(selectedKeyWordsList).hasSize(databaseSizeBeforeCreate + 1);
        SelectedKeyWords testSelectedKeyWords = selectedKeyWordsList.get(selectedKeyWordsList.size() - 1);
        assertThat(testSelectedKeyWords.getModelid()).isEqualTo(DEFAULT_MODELID);
        assertThat(testSelectedKeyWords.getPosmatrix()).isEqualTo(DEFAULT_POSMATRIX);
        assertThat(testSelectedKeyWords.getNeutmatrix()).isEqualTo(DEFAULT_NEUTMATRIX);
        assertThat(testSelectedKeyWords.getNegmatrix()).isEqualTo(DEFAULT_NEGMATRIX);

        // Validate the SelectedKeyWords in Elasticsearch
        SelectedKeyWords selectedKeyWordsEs = selectedKeyWordsSearchRepository.findOne(testSelectedKeyWords.getId());
        assertThat(selectedKeyWordsEs).isEqualToIgnoringGivenFields(testSelectedKeyWords);
    }

    @Test
    public void createSelectedKeyWordsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = selectedKeyWordsRepository.findAll().size();

        // Create the SelectedKeyWords with an existing ID
        selectedKeyWords.setId("existing_id");
        SelectedKeyWordsDTO selectedKeyWordsDTO = selectedKeyWordsMapper.toDto(selectedKeyWords);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelectedKeyWordsMockMvc.perform(post("/api/selected-key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectedKeyWordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SelectedKeyWords in the database
        List<SelectedKeyWords> selectedKeyWordsList = selectedKeyWordsRepository.findAll();
        assertThat(selectedKeyWordsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllSelectedKeyWords() throws Exception {
        // Initialize the database
        selectedKeyWordsRepository.save(selectedKeyWords);

        // Get all the selectedKeyWordsList
        restSelectedKeyWordsMockMvc.perform(get("/api/selected-key-words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selectedKeyWords.getId())))
            .andExpect(jsonPath("$.[*].modelid").value(hasItem(DEFAULT_MODELID.intValue())))
            .andExpect(jsonPath("$.[*].posmatrix").value(hasItem(DEFAULT_POSMATRIX.toString())))
            .andExpect(jsonPath("$.[*].neutmatrix").value(hasItem(DEFAULT_NEUTMATRIX.toString())))
            .andExpect(jsonPath("$.[*].negmatrix").value(hasItem(DEFAULT_NEGMATRIX.toString())));
    }

    @Test
    public void getSelectedKeyWords() throws Exception {
        // Initialize the database
        selectedKeyWordsRepository.save(selectedKeyWords);

        // Get the selectedKeyWords
        restSelectedKeyWordsMockMvc.perform(get("/api/selected-key-words/{id}", selectedKeyWords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(selectedKeyWords.getId()))
            .andExpect(jsonPath("$.modelid").value(DEFAULT_MODELID.intValue()))
            .andExpect(jsonPath("$.posmatrix").value(DEFAULT_POSMATRIX.toString()))
            .andExpect(jsonPath("$.neutmatrix").value(DEFAULT_NEUTMATRIX.toString()))
            .andExpect(jsonPath("$.negmatrix").value(DEFAULT_NEGMATRIX.toString()));
    }

    @Test
    public void getNonExistingSelectedKeyWords() throws Exception {
        // Get the selectedKeyWords
        restSelectedKeyWordsMockMvc.perform(get("/api/selected-key-words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSelectedKeyWords() throws Exception {
        // Initialize the database
        selectedKeyWordsRepository.save(selectedKeyWords);
        selectedKeyWordsSearchRepository.save(selectedKeyWords);
        int databaseSizeBeforeUpdate = selectedKeyWordsRepository.findAll().size();

        // Update the selectedKeyWords
        SelectedKeyWords updatedSelectedKeyWords = selectedKeyWordsRepository.findOne(selectedKeyWords.getId());
        updatedSelectedKeyWords
            .modelid(UPDATED_MODELID)
            .posmatrix(UPDATED_POSMATRIX)
            .neutmatrix(UPDATED_NEUTMATRIX)
            .negmatrix(UPDATED_NEGMATRIX);
        SelectedKeyWordsDTO selectedKeyWordsDTO = selectedKeyWordsMapper.toDto(updatedSelectedKeyWords);

        restSelectedKeyWordsMockMvc.perform(put("/api/selected-key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectedKeyWordsDTO)))
            .andExpect(status().isOk());

        // Validate the SelectedKeyWords in the database
        List<SelectedKeyWords> selectedKeyWordsList = selectedKeyWordsRepository.findAll();
        assertThat(selectedKeyWordsList).hasSize(databaseSizeBeforeUpdate);
        SelectedKeyWords testSelectedKeyWords = selectedKeyWordsList.get(selectedKeyWordsList.size() - 1);
        assertThat(testSelectedKeyWords.getModelid()).isEqualTo(UPDATED_MODELID);
        assertThat(testSelectedKeyWords.getPosmatrix()).isEqualTo(UPDATED_POSMATRIX);
        assertThat(testSelectedKeyWords.getNeutmatrix()).isEqualTo(UPDATED_NEUTMATRIX);
        assertThat(testSelectedKeyWords.getNegmatrix()).isEqualTo(UPDATED_NEGMATRIX);

        // Validate the SelectedKeyWords in Elasticsearch
        SelectedKeyWords selectedKeyWordsEs = selectedKeyWordsSearchRepository.findOne(testSelectedKeyWords.getId());
        assertThat(selectedKeyWordsEs).isEqualToIgnoringGivenFields(testSelectedKeyWords);
    }

    @Test
    public void updateNonExistingSelectedKeyWords() throws Exception {
        int databaseSizeBeforeUpdate = selectedKeyWordsRepository.findAll().size();

        // Create the SelectedKeyWords
        SelectedKeyWordsDTO selectedKeyWordsDTO = selectedKeyWordsMapper.toDto(selectedKeyWords);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSelectedKeyWordsMockMvc.perform(put("/api/selected-key-words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectedKeyWordsDTO)))
            .andExpect(status().isCreated());

        // Validate the SelectedKeyWords in the database
        List<SelectedKeyWords> selectedKeyWordsList = selectedKeyWordsRepository.findAll();
        assertThat(selectedKeyWordsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteSelectedKeyWords() throws Exception {
        // Initialize the database
        selectedKeyWordsRepository.save(selectedKeyWords);
        selectedKeyWordsSearchRepository.save(selectedKeyWords);
        int databaseSizeBeforeDelete = selectedKeyWordsRepository.findAll().size();

        // Get the selectedKeyWords
        restSelectedKeyWordsMockMvc.perform(delete("/api/selected-key-words/{id}", selectedKeyWords.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean selectedKeyWordsExistsInEs = selectedKeyWordsSearchRepository.exists(selectedKeyWords.getId());
        assertThat(selectedKeyWordsExistsInEs).isFalse();

        // Validate the database is empty
        List<SelectedKeyWords> selectedKeyWordsList = selectedKeyWordsRepository.findAll();
        assertThat(selectedKeyWordsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchSelectedKeyWords() throws Exception {
        // Initialize the database
        selectedKeyWordsRepository.save(selectedKeyWords);
        selectedKeyWordsSearchRepository.save(selectedKeyWords);

        // Search the selectedKeyWords
        restSelectedKeyWordsMockMvc.perform(get("/api/_search/selected-key-words?query=id:" + selectedKeyWords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selectedKeyWords.getId())))
            .andExpect(jsonPath("$.[*].modelid").value(hasItem(DEFAULT_MODELID.intValue())))
            .andExpect(jsonPath("$.[*].posmatrix").value(hasItem(DEFAULT_POSMATRIX.toString())))
            .andExpect(jsonPath("$.[*].neutmatrix").value(hasItem(DEFAULT_NEUTMATRIX.toString())))
            .andExpect(jsonPath("$.[*].negmatrix").value(hasItem(DEFAULT_NEGMATRIX.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectedKeyWords.class);
        SelectedKeyWords selectedKeyWords1 = new SelectedKeyWords();
        selectedKeyWords1.setId("id1");
        SelectedKeyWords selectedKeyWords2 = new SelectedKeyWords();
        selectedKeyWords2.setId(selectedKeyWords1.getId());
        assertThat(selectedKeyWords1).isEqualTo(selectedKeyWords2);
        selectedKeyWords2.setId("id2");
        assertThat(selectedKeyWords1).isNotEqualTo(selectedKeyWords2);
        selectedKeyWords1.setId(null);
        assertThat(selectedKeyWords1).isNotEqualTo(selectedKeyWords2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectedKeyWordsDTO.class);
        SelectedKeyWordsDTO selectedKeyWordsDTO1 = new SelectedKeyWordsDTO();
        selectedKeyWordsDTO1.setId("id1");
        SelectedKeyWordsDTO selectedKeyWordsDTO2 = new SelectedKeyWordsDTO();
        assertThat(selectedKeyWordsDTO1).isNotEqualTo(selectedKeyWordsDTO2);
        selectedKeyWordsDTO2.setId(selectedKeyWordsDTO1.getId());
        assertThat(selectedKeyWordsDTO1).isEqualTo(selectedKeyWordsDTO2);
        selectedKeyWordsDTO2.setId("id2");
        assertThat(selectedKeyWordsDTO1).isNotEqualTo(selectedKeyWordsDTO2);
        selectedKeyWordsDTO1.setId(null);
        assertThat(selectedKeyWordsDTO1).isNotEqualTo(selectedKeyWordsDTO2);
    }
}

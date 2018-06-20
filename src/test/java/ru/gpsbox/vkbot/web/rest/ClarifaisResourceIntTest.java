package ru.gpsbox.vkbot.web.rest;

import ru.gpsbox.vkbot.VkbotApp;

import ru.gpsbox.vkbot.domain.Clarifais;
import ru.gpsbox.vkbot.repository.ClarifaisRepository;
import ru.gpsbox.vkbot.service.ClarifaisService;
import ru.gpsbox.vkbot.repository.search.ClarifaisSearchRepository;
import ru.gpsbox.vkbot.service.dto.ClarifaisDTO;
import ru.gpsbox.vkbot.service.mapper.ClarifaisMapper;
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
 * Test class for the ClarifaisResource REST controller.
 *
 * @see ClarifaisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class ClarifaisResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAILPASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_EMAILPASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_APIKEY = "AAAAAAAAAA";
    private static final String UPDATED_APIKEY = "BBBBBBBBBB";

    private static final String DEFAULT_MODELNAME = "AAAAAAAAAA";
    private static final String UPDATED_MODELNAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BILLDAY = "AAAAAAAAAA";
    private static final String UPDATED_BILLDAY = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTALUSAGE = 1;
    private static final Integer UPDATED_TOTALUSAGE = 2;

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Autowired
    private ClarifaisRepository clarifaisRepository;

    @Autowired
    private ClarifaisMapper clarifaisMapper;

    @Autowired
    private ClarifaisService clarifaisService;

    @Autowired
    private ClarifaisSearchRepository clarifaisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restClarifaisMockMvc;

    private Clarifais clarifais;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClarifaisResource clarifaisResource = new ClarifaisResource(clarifaisService);
        this.restClarifaisMockMvc = MockMvcBuilders.standaloneSetup(clarifaisResource)
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
    public static Clarifais createEntity() {
        Clarifais clarifais = new Clarifais()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .emailpassword(DEFAULT_EMAILPASSWORD)
            .apikey(DEFAULT_APIKEY)
            .modelname(DEFAULT_MODELNAME)
            .country(DEFAULT_COUNTRY)
            .billday(DEFAULT_BILLDAY)
            .totalusage(DEFAULT_TOTALUSAGE)
            .count(DEFAULT_COUNT);
        return clarifais;
    }

    @Before
    public void initTest() {
        clarifaisRepository.deleteAll();
        clarifaisSearchRepository.deleteAll();
        clarifais = createEntity();
    }

    @Test
    public void createClarifais() throws Exception {
        int databaseSizeBeforeCreate = clarifaisRepository.findAll().size();

        // Create the Clarifais
        ClarifaisDTO clarifaisDTO = clarifaisMapper.toDto(clarifais);
        restClarifaisMockMvc.perform(post("/api/clarifais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaisDTO)))
            .andExpect(status().isCreated());

        // Validate the Clarifais in the database
        List<Clarifais> clarifaisList = clarifaisRepository.findAll();
        assertThat(clarifaisList).hasSize(databaseSizeBeforeCreate + 1);
        Clarifais testClarifais = clarifaisList.get(clarifaisList.size() - 1);
        assertThat(testClarifais.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClarifais.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClarifais.getEmailpassword()).isEqualTo(DEFAULT_EMAILPASSWORD);
        assertThat(testClarifais.getApikey()).isEqualTo(DEFAULT_APIKEY);
        assertThat(testClarifais.getModelname()).isEqualTo(DEFAULT_MODELNAME);
        assertThat(testClarifais.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testClarifais.getBillday()).isEqualTo(DEFAULT_BILLDAY);
        assertThat(testClarifais.getTotalusage()).isEqualTo(DEFAULT_TOTALUSAGE);
        assertThat(testClarifais.getCount()).isEqualTo(DEFAULT_COUNT);

        // Validate the Clarifais in Elasticsearch
        Clarifais clarifaisEs = clarifaisSearchRepository.findOne(testClarifais.getId());
        assertThat(clarifaisEs).isEqualToIgnoringGivenFields(testClarifais);
    }

    @Test
    public void createClarifaisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clarifaisRepository.findAll().size();

        // Create the Clarifais with an existing ID
        clarifais.setId("existing_id");
        ClarifaisDTO clarifaisDTO = clarifaisMapper.toDto(clarifais);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClarifaisMockMvc.perform(post("/api/clarifais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clarifais in the database
        List<Clarifais> clarifaisList = clarifaisRepository.findAll();
        assertThat(clarifaisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllClarifais() throws Exception {
        // Initialize the database
        clarifaisRepository.save(clarifais);

        // Get all the clarifaisList
        restClarifaisMockMvc.perform(get("/api/clarifais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clarifais.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].emailpassword").value(hasItem(DEFAULT_EMAILPASSWORD.toString())))
            .andExpect(jsonPath("$.[*].apikey").value(hasItem(DEFAULT_APIKEY.toString())))
            .andExpect(jsonPath("$.[*].modelname").value(hasItem(DEFAULT_MODELNAME.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].billday").value(hasItem(DEFAULT_BILLDAY.toString())))
            .andExpect(jsonPath("$.[*].totalusage").value(hasItem(DEFAULT_TOTALUSAGE)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    public void getClarifais() throws Exception {
        // Initialize the database
        clarifaisRepository.save(clarifais);

        // Get the clarifais
        restClarifaisMockMvc.perform(get("/api/clarifais/{id}", clarifais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clarifais.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.emailpassword").value(DEFAULT_EMAILPASSWORD.toString()))
            .andExpect(jsonPath("$.apikey").value(DEFAULT_APIKEY.toString()))
            .andExpect(jsonPath("$.modelname").value(DEFAULT_MODELNAME.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.billday").value(DEFAULT_BILLDAY.toString()))
            .andExpect(jsonPath("$.totalusage").value(DEFAULT_TOTALUSAGE))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    public void getNonExistingClarifais() throws Exception {
        // Get the clarifais
        restClarifaisMockMvc.perform(get("/api/clarifais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClarifais() throws Exception {
        // Initialize the database
        clarifaisRepository.save(clarifais);
        clarifaisSearchRepository.save(clarifais);
        int databaseSizeBeforeUpdate = clarifaisRepository.findAll().size();

        // Update the clarifais
        Clarifais updatedClarifais = clarifaisRepository.findOne(clarifais.getId());
        updatedClarifais
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .emailpassword(UPDATED_EMAILPASSWORD)
            .apikey(UPDATED_APIKEY)
            .modelname(UPDATED_MODELNAME)
            .country(UPDATED_COUNTRY)
            .billday(UPDATED_BILLDAY)
            .totalusage(UPDATED_TOTALUSAGE)
            .count(UPDATED_COUNT);
        ClarifaisDTO clarifaisDTO = clarifaisMapper.toDto(updatedClarifais);

        restClarifaisMockMvc.perform(put("/api/clarifais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaisDTO)))
            .andExpect(status().isOk());

        // Validate the Clarifais in the database
        List<Clarifais> clarifaisList = clarifaisRepository.findAll();
        assertThat(clarifaisList).hasSize(databaseSizeBeforeUpdate);
        Clarifais testClarifais = clarifaisList.get(clarifaisList.size() - 1);
        assertThat(testClarifais.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClarifais.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClarifais.getEmailpassword()).isEqualTo(UPDATED_EMAILPASSWORD);
        assertThat(testClarifais.getApikey()).isEqualTo(UPDATED_APIKEY);
        assertThat(testClarifais.getModelname()).isEqualTo(UPDATED_MODELNAME);
        assertThat(testClarifais.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testClarifais.getBillday()).isEqualTo(UPDATED_BILLDAY);
        assertThat(testClarifais.getTotalusage()).isEqualTo(UPDATED_TOTALUSAGE);
        assertThat(testClarifais.getCount()).isEqualTo(UPDATED_COUNT);

        // Validate the Clarifais in Elasticsearch
        Clarifais clarifaisEs = clarifaisSearchRepository.findOne(testClarifais.getId());
        assertThat(clarifaisEs).isEqualToIgnoringGivenFields(testClarifais);
    }

    @Test
    public void updateNonExistingClarifais() throws Exception {
        int databaseSizeBeforeUpdate = clarifaisRepository.findAll().size();

        // Create the Clarifais
        ClarifaisDTO clarifaisDTO = clarifaisMapper.toDto(clarifais);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClarifaisMockMvc.perform(put("/api/clarifais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clarifaisDTO)))
            .andExpect(status().isCreated());

        // Validate the Clarifais in the database
        List<Clarifais> clarifaisList = clarifaisRepository.findAll();
        assertThat(clarifaisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteClarifais() throws Exception {
        // Initialize the database
        clarifaisRepository.save(clarifais);
        clarifaisSearchRepository.save(clarifais);
        int databaseSizeBeforeDelete = clarifaisRepository.findAll().size();

        // Get the clarifais
        restClarifaisMockMvc.perform(delete("/api/clarifais/{id}", clarifais.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean clarifaisExistsInEs = clarifaisSearchRepository.exists(clarifais.getId());
        assertThat(clarifaisExistsInEs).isFalse();

        // Validate the database is empty
        List<Clarifais> clarifaisList = clarifaisRepository.findAll();
        assertThat(clarifaisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchClarifais() throws Exception {
        // Initialize the database
        clarifaisRepository.save(clarifais);
        clarifaisSearchRepository.save(clarifais);

        // Search the clarifais
        restClarifaisMockMvc.perform(get("/api/_search/clarifais?query=id:" + clarifais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clarifais.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].emailpassword").value(hasItem(DEFAULT_EMAILPASSWORD.toString())))
            .andExpect(jsonPath("$.[*].apikey").value(hasItem(DEFAULT_APIKEY.toString())))
            .andExpect(jsonPath("$.[*].modelname").value(hasItem(DEFAULT_MODELNAME.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].billday").value(hasItem(DEFAULT_BILLDAY.toString())))
            .andExpect(jsonPath("$.[*].totalusage").value(hasItem(DEFAULT_TOTALUSAGE)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clarifais.class);
        Clarifais clarifais1 = new Clarifais();
        clarifais1.setId("id1");
        Clarifais clarifais2 = new Clarifais();
        clarifais2.setId(clarifais1.getId());
        assertThat(clarifais1).isEqualTo(clarifais2);
        clarifais2.setId("id2");
        assertThat(clarifais1).isNotEqualTo(clarifais2);
        clarifais1.setId(null);
        assertThat(clarifais1).isNotEqualTo(clarifais2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClarifaisDTO.class);
        ClarifaisDTO clarifaisDTO1 = new ClarifaisDTO();
        clarifaisDTO1.setId("id1");
        ClarifaisDTO clarifaisDTO2 = new ClarifaisDTO();
        assertThat(clarifaisDTO1).isNotEqualTo(clarifaisDTO2);
        clarifaisDTO2.setId(clarifaisDTO1.getId());
        assertThat(clarifaisDTO1).isEqualTo(clarifaisDTO2);
        clarifaisDTO2.setId("id2");
        assertThat(clarifaisDTO1).isNotEqualTo(clarifaisDTO2);
        clarifaisDTO1.setId(null);
        assertThat(clarifaisDTO1).isNotEqualTo(clarifaisDTO2);
    }
}

package ru.gpsbox.vkbot.web.rest;

import ru.gpsbox.vkbot.VkbotApp;

import ru.gpsbox.vkbot.domain.VkUser;
import ru.gpsbox.vkbot.repository.VkUserRepository;
import ru.gpsbox.vkbot.service.VkUserService;
import ru.gpsbox.vkbot.repository.search.VkUserSearchRepository;
import ru.gpsbox.vkbot.service.dto.VkUserDTO;
import ru.gpsbox.vkbot.service.mapper.VkUserMapper;
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
 * Test class for the VkUserResource REST controller.
 *
 * @see VkUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class VkUserResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final String DEFAULT_BDATE = "AAAAAAAAAA";
    private static final String UPDATED_BDATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CITYID = 1;
    private static final Integer UPDATED_CITYID = 2;

    private static final String DEFAULT_CITYTITLE = "AAAAAAAAAA";
    private static final String UPDATED_CITYTITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNTRYID = 1;
    private static final Integer UPDATED_COUNTRYID = 2;

    private static final String DEFAULT_COUNTRYTITLE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRYTITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_GROUPSCOUNT = 1;
    private static final Integer UPDATED_GROUPSCOUNT = 2;

    private static final Integer DEFAULT_GROUPSPROC = 1;
    private static final Integer UPDATED_GROUPSPROC = 2;

    private static final Integer DEFAULT_IMAGES = 1;
    private static final Integer UPDATED_IMAGES = 2;

    private static final Integer DEFAULT_IMAGESPROC = 1;
    private static final Integer UPDATED_IMAGESPROC = 2;

    private static final Integer DEFAULT_LASTSEEN = 1;
    private static final Integer UPDATED_LASTSEEN = 2;

    private static final String DEFAULT_PLATFORM = "AAAAAAAAAA";
    private static final String UPDATED_PLATFORM = "BBBBBBBBBB";

    private static final Integer DEFAULT_IGNORE = 1;
    private static final Integer UPDATED_IGNORE = 2;

    private static final Integer DEFAULT_PROC = 1;
    private static final Integer UPDATED_PROC = 2;

    private static final String DEFAULT_PHOTO_100 = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_100 = "BBBBBBBBBB";

    @Autowired
    private VkUserRepository vkUserRepository;

    @Autowired
    private VkUserMapper vkUserMapper;

    @Autowired
    private VkUserService vkUserService;

    @Autowired
    private VkUserSearchRepository vkUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVkUserMockMvc;

    private VkUser vkUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VkUserResource vkUserResource = new VkUserResource(vkUserService);
        this.restVkUserMockMvc = MockMvcBuilders.standaloneSetup(vkUserResource)
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
    public static VkUser createEntity() {
        VkUser vkUser = new VkUser()
            .userId(DEFAULT_USER_ID)
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .sex(DEFAULT_SEX)
            .bdate(DEFAULT_BDATE)
            .cityid(DEFAULT_CITYID)
            .citytitle(DEFAULT_CITYTITLE)
            .countryid(DEFAULT_COUNTRYID)
            .countrytitle(DEFAULT_COUNTRYTITLE)
            .groupscount(DEFAULT_GROUPSCOUNT)
            .groupsproc(DEFAULT_GROUPSPROC)
            .images(DEFAULT_IMAGES)
            .imagesproc(DEFAULT_IMAGESPROC)
            .lastseen(DEFAULT_LASTSEEN)
            .platform(DEFAULT_PLATFORM)
            .ignore(DEFAULT_IGNORE)
            .proc(DEFAULT_PROC)
            .photo100(DEFAULT_PHOTO_100);
        return vkUser;
    }

    @Before
    public void initTest() {
        vkUserRepository.deleteAll();
        vkUserSearchRepository.deleteAll();
        vkUser = createEntity();
    }

    @Test
    public void createVkUser() throws Exception {
        int databaseSizeBeforeCreate = vkUserRepository.findAll().size();

        // Create the VkUser
        VkUserDTO vkUserDTO = vkUserMapper.toDto(vkUser);
        restVkUserMockMvc.perform(post("/api/vk-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkUserDTO)))
            .andExpect(status().isCreated());

        // Validate the VkUser in the database
        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeCreate + 1);
        VkUser testVkUser = vkUserList.get(vkUserList.size() - 1);
        assertThat(testVkUser.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testVkUser.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testVkUser.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testVkUser.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testVkUser.getBdate()).isEqualTo(DEFAULT_BDATE);
        assertThat(testVkUser.getCityid()).isEqualTo(DEFAULT_CITYID);
        assertThat(testVkUser.getCitytitle()).isEqualTo(DEFAULT_CITYTITLE);
        assertThat(testVkUser.getCountryid()).isEqualTo(DEFAULT_COUNTRYID);
        assertThat(testVkUser.getCountrytitle()).isEqualTo(DEFAULT_COUNTRYTITLE);
        assertThat(testVkUser.getGroupscount()).isEqualTo(DEFAULT_GROUPSCOUNT);
        assertThat(testVkUser.getGroupsproc()).isEqualTo(DEFAULT_GROUPSPROC);
        assertThat(testVkUser.getImages()).isEqualTo(DEFAULT_IMAGES);
        assertThat(testVkUser.getImagesproc()).isEqualTo(DEFAULT_IMAGESPROC);
        assertThat(testVkUser.getLastseen()).isEqualTo(DEFAULT_LASTSEEN);
        assertThat(testVkUser.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
        assertThat(testVkUser.getIgnore()).isEqualTo(DEFAULT_IGNORE);
        assertThat(testVkUser.getProc()).isEqualTo(DEFAULT_PROC);
        assertThat(testVkUser.getPhoto100()).isEqualTo(DEFAULT_PHOTO_100);

        // Validate the VkUser in Elasticsearch
        VkUser vkUserEs = vkUserSearchRepository.findOne(testVkUser.getId());
        assertThat(vkUserEs).isEqualToIgnoringGivenFields(testVkUser);
    }

    @Test
    public void createVkUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vkUserRepository.findAll().size();

        // Create the VkUser with an existing ID
        vkUser.setId("existing_id");
        VkUserDTO vkUserDTO = vkUserMapper.toDto(vkUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVkUserMockMvc.perform(post("/api/vk-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VkUser in the database
        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkUserRepository.findAll().size();
        // set the field null
        vkUser.setUserId(null);

        // Create the VkUser, which fails.
        VkUserDTO vkUserDTO = vkUserMapper.toDto(vkUser);

        restVkUserMockMvc.perform(post("/api/vk-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkUserDTO)))
            .andExpect(status().isBadRequest());

        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFirstnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkUserRepository.findAll().size();
        // set the field null
        vkUser.setFirstname(null);

        // Create the VkUser, which fails.
        VkUserDTO vkUserDTO = vkUserMapper.toDto(vkUser);

        restVkUserMockMvc.perform(post("/api/vk-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkUserDTO)))
            .andExpect(status().isBadRequest());

        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLastnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkUserRepository.findAll().size();
        // set the field null
        vkUser.setLastname(null);

        // Create the VkUser, which fails.
        VkUserDTO vkUserDTO = vkUserMapper.toDto(vkUser);

        restVkUserMockMvc.perform(post("/api/vk-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkUserDTO)))
            .andExpect(status().isBadRequest());

        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkUserRepository.findAll().size();
        // set the field null
        vkUser.setSex(null);

        // Create the VkUser, which fails.
        VkUserDTO vkUserDTO = vkUserMapper.toDto(vkUser);

        restVkUserMockMvc.perform(post("/api/vk-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkUserDTO)))
            .andExpect(status().isBadRequest());

        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVkUsers() throws Exception {
        // Initialize the database
        vkUserRepository.save(vkUser);

        // Get all the vkUserList
        restVkUserMockMvc.perform(get("/api/vk-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vkUser.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].bdate").value(hasItem(DEFAULT_BDATE.toString())))
            .andExpect(jsonPath("$.[*].cityid").value(hasItem(DEFAULT_CITYID)))
            .andExpect(jsonPath("$.[*].citytitle").value(hasItem(DEFAULT_CITYTITLE.toString())))
            .andExpect(jsonPath("$.[*].countryid").value(hasItem(DEFAULT_COUNTRYID)))
            .andExpect(jsonPath("$.[*].countrytitle").value(hasItem(DEFAULT_COUNTRYTITLE.toString())))
            .andExpect(jsonPath("$.[*].groupscount").value(hasItem(DEFAULT_GROUPSCOUNT)))
            .andExpect(jsonPath("$.[*].groupsproc").value(hasItem(DEFAULT_GROUPSPROC)))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)))
            .andExpect(jsonPath("$.[*].imagesproc").value(hasItem(DEFAULT_IMAGESPROC)))
            .andExpect(jsonPath("$.[*].lastseen").value(hasItem(DEFAULT_LASTSEEN)))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())))
            .andExpect(jsonPath("$.[*].ignore").value(hasItem(DEFAULT_IGNORE)))
            .andExpect(jsonPath("$.[*].proc").value(hasItem(DEFAULT_PROC)))
            .andExpect(jsonPath("$.[*].photo100").value(hasItem(DEFAULT_PHOTO_100.toString())));
    }

    @Test
    public void getVkUser() throws Exception {
        // Initialize the database
        vkUserRepository.save(vkUser);

        // Get the vkUser
        restVkUserMockMvc.perform(get("/api/vk-users/{id}", vkUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vkUser.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.bdate").value(DEFAULT_BDATE.toString()))
            .andExpect(jsonPath("$.cityid").value(DEFAULT_CITYID))
            .andExpect(jsonPath("$.citytitle").value(DEFAULT_CITYTITLE.toString()))
            .andExpect(jsonPath("$.countryid").value(DEFAULT_COUNTRYID))
            .andExpect(jsonPath("$.countrytitle").value(DEFAULT_COUNTRYTITLE.toString()))
            .andExpect(jsonPath("$.groupscount").value(DEFAULT_GROUPSCOUNT))
            .andExpect(jsonPath("$.groupsproc").value(DEFAULT_GROUPSPROC))
            .andExpect(jsonPath("$.images").value(DEFAULT_IMAGES))
            .andExpect(jsonPath("$.imagesproc").value(DEFAULT_IMAGESPROC))
            .andExpect(jsonPath("$.lastseen").value(DEFAULT_LASTSEEN))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()))
            .andExpect(jsonPath("$.ignore").value(DEFAULT_IGNORE))
            .andExpect(jsonPath("$.proc").value(DEFAULT_PROC))
            .andExpect(jsonPath("$.photo100").value(DEFAULT_PHOTO_100.toString()));
    }

    @Test
    public void getNonExistingVkUser() throws Exception {
        // Get the vkUser
        restVkUserMockMvc.perform(get("/api/vk-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVkUser() throws Exception {
        // Initialize the database
        vkUserRepository.save(vkUser);
        vkUserSearchRepository.save(vkUser);
        int databaseSizeBeforeUpdate = vkUserRepository.findAll().size();

        // Update the vkUser
        VkUser updatedVkUser = vkUserRepository.findOne(vkUser.getId());
        updatedVkUser
            .userId(UPDATED_USER_ID)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .sex(UPDATED_SEX)
            .bdate(UPDATED_BDATE)
            .cityid(UPDATED_CITYID)
            .citytitle(UPDATED_CITYTITLE)
            .countryid(UPDATED_COUNTRYID)
            .countrytitle(UPDATED_COUNTRYTITLE)
            .groupscount(UPDATED_GROUPSCOUNT)
            .groupsproc(UPDATED_GROUPSPROC)
            .images(UPDATED_IMAGES)
            .imagesproc(UPDATED_IMAGESPROC)
            .lastseen(UPDATED_LASTSEEN)
            .platform(UPDATED_PLATFORM)
            .ignore(UPDATED_IGNORE)
            .proc(UPDATED_PROC)
            .photo100(UPDATED_PHOTO_100);
        VkUserDTO vkUserDTO = vkUserMapper.toDto(updatedVkUser);

        restVkUserMockMvc.perform(put("/api/vk-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkUserDTO)))
            .andExpect(status().isOk());

        // Validate the VkUser in the database
        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeUpdate);
        VkUser testVkUser = vkUserList.get(vkUserList.size() - 1);
        assertThat(testVkUser.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testVkUser.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testVkUser.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testVkUser.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testVkUser.getBdate()).isEqualTo(UPDATED_BDATE);
        assertThat(testVkUser.getCityid()).isEqualTo(UPDATED_CITYID);
        assertThat(testVkUser.getCitytitle()).isEqualTo(UPDATED_CITYTITLE);
        assertThat(testVkUser.getCountryid()).isEqualTo(UPDATED_COUNTRYID);
        assertThat(testVkUser.getCountrytitle()).isEqualTo(UPDATED_COUNTRYTITLE);
        assertThat(testVkUser.getGroupscount()).isEqualTo(UPDATED_GROUPSCOUNT);
        assertThat(testVkUser.getGroupsproc()).isEqualTo(UPDATED_GROUPSPROC);
        assertThat(testVkUser.getImages()).isEqualTo(UPDATED_IMAGES);
        assertThat(testVkUser.getImagesproc()).isEqualTo(UPDATED_IMAGESPROC);
        assertThat(testVkUser.getLastseen()).isEqualTo(UPDATED_LASTSEEN);
        assertThat(testVkUser.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testVkUser.getIgnore()).isEqualTo(UPDATED_IGNORE);
        assertThat(testVkUser.getProc()).isEqualTo(UPDATED_PROC);
        assertThat(testVkUser.getPhoto100()).isEqualTo(UPDATED_PHOTO_100);

        // Validate the VkUser in Elasticsearch
        VkUser vkUserEs = vkUserSearchRepository.findOne(testVkUser.getId());
        assertThat(vkUserEs).isEqualToIgnoringGivenFields(testVkUser);
    }

    @Test
    public void updateNonExistingVkUser() throws Exception {
        int databaseSizeBeforeUpdate = vkUserRepository.findAll().size();

        // Create the VkUser
        VkUserDTO vkUserDTO = vkUserMapper.toDto(vkUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVkUserMockMvc.perform(put("/api/vk-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkUserDTO)))
            .andExpect(status().isCreated());

        // Validate the VkUser in the database
        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVkUser() throws Exception {
        // Initialize the database
        vkUserRepository.save(vkUser);
        vkUserSearchRepository.save(vkUser);
        int databaseSizeBeforeDelete = vkUserRepository.findAll().size();

        // Get the vkUser
        restVkUserMockMvc.perform(delete("/api/vk-users/{id}", vkUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vkUserExistsInEs = vkUserSearchRepository.exists(vkUser.getId());
        assertThat(vkUserExistsInEs).isFalse();

        // Validate the database is empty
        List<VkUser> vkUserList = vkUserRepository.findAll();
        assertThat(vkUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchVkUser() throws Exception {
        // Initialize the database
        vkUserRepository.save(vkUser);
        vkUserSearchRepository.save(vkUser);

        // Search the vkUser
        restVkUserMockMvc.perform(get("/api/_search/vk-users?query=id:" + vkUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vkUser.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].bdate").value(hasItem(DEFAULT_BDATE.toString())))
            .andExpect(jsonPath("$.[*].cityid").value(hasItem(DEFAULT_CITYID)))
            .andExpect(jsonPath("$.[*].citytitle").value(hasItem(DEFAULT_CITYTITLE.toString())))
            .andExpect(jsonPath("$.[*].countryid").value(hasItem(DEFAULT_COUNTRYID)))
            .andExpect(jsonPath("$.[*].countrytitle").value(hasItem(DEFAULT_COUNTRYTITLE.toString())))
            .andExpect(jsonPath("$.[*].groupscount").value(hasItem(DEFAULT_GROUPSCOUNT)))
            .andExpect(jsonPath("$.[*].groupsproc").value(hasItem(DEFAULT_GROUPSPROC)))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)))
            .andExpect(jsonPath("$.[*].imagesproc").value(hasItem(DEFAULT_IMAGESPROC)))
            .andExpect(jsonPath("$.[*].lastseen").value(hasItem(DEFAULT_LASTSEEN)))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())))
            .andExpect(jsonPath("$.[*].ignore").value(hasItem(DEFAULT_IGNORE)))
            .andExpect(jsonPath("$.[*].proc").value(hasItem(DEFAULT_PROC)))
            .andExpect(jsonPath("$.[*].photo100").value(hasItem(DEFAULT_PHOTO_100.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VkUser.class);
        VkUser vkUser1 = new VkUser();
        vkUser1.setId("id1");
        VkUser vkUser2 = new VkUser();
        vkUser2.setId(vkUser1.getId());
        assertThat(vkUser1).isEqualTo(vkUser2);
        vkUser2.setId("id2");
        assertThat(vkUser1).isNotEqualTo(vkUser2);
        vkUser1.setId(null);
        assertThat(vkUser1).isNotEqualTo(vkUser2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VkUserDTO.class);
        VkUserDTO vkUserDTO1 = new VkUserDTO();
        vkUserDTO1.setId("id1");
        VkUserDTO vkUserDTO2 = new VkUserDTO();
        assertThat(vkUserDTO1).isNotEqualTo(vkUserDTO2);
        vkUserDTO2.setId(vkUserDTO1.getId());
        assertThat(vkUserDTO1).isEqualTo(vkUserDTO2);
        vkUserDTO2.setId("id2");
        assertThat(vkUserDTO1).isNotEqualTo(vkUserDTO2);
        vkUserDTO1.setId(null);
        assertThat(vkUserDTO1).isNotEqualTo(vkUserDTO2);
    }
}

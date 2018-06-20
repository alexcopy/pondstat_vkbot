package ru.gpsbox.vkbot.web.rest;

import ru.gpsbox.vkbot.VkbotApp;

import ru.gpsbox.vkbot.domain.VkGroup;
import ru.gpsbox.vkbot.repository.VkGroupRepository;
import ru.gpsbox.vkbot.service.VkGroupService;
import ru.gpsbox.vkbot.repository.search.VkGroupSearchRepository;
import ru.gpsbox.vkbot.service.dto.VkGroupDTO;
import ru.gpsbox.vkbot.service.mapper.VkGroupMapper;
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
 * Test class for the VkGroupResource REST controller.
 *
 * @see VkGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VkbotApp.class)
public class VkGroupResourceIntTest {

    private static final Integer DEFAULT_GROUPID = 1;
    private static final Integer UPDATED_GROUPID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCREENNAME = "AAAAAAAAAA";
    private static final String UPDATED_SCREENNAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISCLOSED = 1;
    private static final Integer UPDATED_ISCLOSED = 2;

    private static final Integer DEFAULT_PROC = 1;
    private static final Integer UPDATED_PROC = 2;

    private static final Integer DEFAULT_PROCUSERS = 1;
    private static final Integer UPDATED_PROCUSERS = 2;

    private static final Integer DEFAULT_USERSQTY = 1;
    private static final Integer UPDATED_USERSQTY = 2;

    private static final Integer DEFAULT_USERSADDED = 1;
    private static final Integer UPDATED_USERSADDED = 2;

    private static final Integer DEFAULT_IGNORE = 1;
    private static final Integer UPDATED_IGNORE = 2;

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private VkGroupRepository vkGroupRepository;

    @Autowired
    private VkGroupMapper vkGroupMapper;

    @Autowired
    private VkGroupService vkGroupService;

    @Autowired
    private VkGroupSearchRepository vkGroupSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVkGroupMockMvc;

    private VkGroup vkGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VkGroupResource vkGroupResource = new VkGroupResource(vkGroupService);
        this.restVkGroupMockMvc = MockMvcBuilders.standaloneSetup(vkGroupResource)
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
    public static VkGroup createEntity() {
        VkGroup vkGroup = new VkGroup()
            .groupid(DEFAULT_GROUPID)
            .name(DEFAULT_NAME)
            .screenname(DEFAULT_SCREENNAME)
            .type(DEFAULT_TYPE)
            .isclosed(DEFAULT_ISCLOSED)
            .proc(DEFAULT_PROC)
            .procusers(DEFAULT_PROCUSERS)
            .usersqty(DEFAULT_USERSQTY)
            .usersadded(DEFAULT_USERSADDED)
            .ignore(DEFAULT_IGNORE)
            .stateProvince(DEFAULT_STATE_PROVINCE);
        return vkGroup;
    }

    @Before
    public void initTest() {
        vkGroupRepository.deleteAll();
        vkGroupSearchRepository.deleteAll();
        vkGroup = createEntity();
    }

    @Test
    public void createVkGroup() throws Exception {
        int databaseSizeBeforeCreate = vkGroupRepository.findAll().size();

        // Create the VkGroup
        VkGroupDTO vkGroupDTO = vkGroupMapper.toDto(vkGroup);
        restVkGroupMockMvc.perform(post("/api/vk-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the VkGroup in the database
        List<VkGroup> vkGroupList = vkGroupRepository.findAll();
        assertThat(vkGroupList).hasSize(databaseSizeBeforeCreate + 1);
        VkGroup testVkGroup = vkGroupList.get(vkGroupList.size() - 1);
        assertThat(testVkGroup.getGroupid()).isEqualTo(DEFAULT_GROUPID);
        assertThat(testVkGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVkGroup.getScreenname()).isEqualTo(DEFAULT_SCREENNAME);
        assertThat(testVkGroup.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVkGroup.getIsclosed()).isEqualTo(DEFAULT_ISCLOSED);
        assertThat(testVkGroup.getProc()).isEqualTo(DEFAULT_PROC);
        assertThat(testVkGroup.getProcusers()).isEqualTo(DEFAULT_PROCUSERS);
        assertThat(testVkGroup.getUsersqty()).isEqualTo(DEFAULT_USERSQTY);
        assertThat(testVkGroup.getUsersadded()).isEqualTo(DEFAULT_USERSADDED);
        assertThat(testVkGroup.getIgnore()).isEqualTo(DEFAULT_IGNORE);
        assertThat(testVkGroup.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);

        // Validate the VkGroup in Elasticsearch
        VkGroup vkGroupEs = vkGroupSearchRepository.findOne(testVkGroup.getId());
        assertThat(vkGroupEs).isEqualToIgnoringGivenFields(testVkGroup);
    }

    @Test
    public void createVkGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vkGroupRepository.findAll().size();

        // Create the VkGroup with an existing ID
        vkGroup.setId("existing_id");
        VkGroupDTO vkGroupDTO = vkGroupMapper.toDto(vkGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVkGroupMockMvc.perform(post("/api/vk-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VkGroup in the database
        List<VkGroup> vkGroupList = vkGroupRepository.findAll();
        assertThat(vkGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkGroupidIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkGroupRepository.findAll().size();
        // set the field null
        vkGroup.setGroupid(null);

        // Create the VkGroup, which fails.
        VkGroupDTO vkGroupDTO = vkGroupMapper.toDto(vkGroup);

        restVkGroupMockMvc.perform(post("/api/vk-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkGroupDTO)))
            .andExpect(status().isBadRequest());

        List<VkGroup> vkGroupList = vkGroupRepository.findAll();
        assertThat(vkGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vkGroupRepository.findAll().size();
        // set the field null
        vkGroup.setName(null);

        // Create the VkGroup, which fails.
        VkGroupDTO vkGroupDTO = vkGroupMapper.toDto(vkGroup);

        restVkGroupMockMvc.perform(post("/api/vk-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkGroupDTO)))
            .andExpect(status().isBadRequest());

        List<VkGroup> vkGroupList = vkGroupRepository.findAll();
        assertThat(vkGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVkGroups() throws Exception {
        // Initialize the database
        vkGroupRepository.save(vkGroup);

        // Get all the vkGroupList
        restVkGroupMockMvc.perform(get("/api/vk-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vkGroup.getId())))
            .andExpect(jsonPath("$.[*].groupid").value(hasItem(DEFAULT_GROUPID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].screenname").value(hasItem(DEFAULT_SCREENNAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isclosed").value(hasItem(DEFAULT_ISCLOSED)))
            .andExpect(jsonPath("$.[*].proc").value(hasItem(DEFAULT_PROC)))
            .andExpect(jsonPath("$.[*].procusers").value(hasItem(DEFAULT_PROCUSERS)))
            .andExpect(jsonPath("$.[*].usersqty").value(hasItem(DEFAULT_USERSQTY)))
            .andExpect(jsonPath("$.[*].usersadded").value(hasItem(DEFAULT_USERSADDED)))
            .andExpect(jsonPath("$.[*].ignore").value(hasItem(DEFAULT_IGNORE)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())));
    }

    @Test
    public void getVkGroup() throws Exception {
        // Initialize the database
        vkGroupRepository.save(vkGroup);

        // Get the vkGroup
        restVkGroupMockMvc.perform(get("/api/vk-groups/{id}", vkGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vkGroup.getId()))
            .andExpect(jsonPath("$.groupid").value(DEFAULT_GROUPID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.screenname").value(DEFAULT_SCREENNAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.isclosed").value(DEFAULT_ISCLOSED))
            .andExpect(jsonPath("$.proc").value(DEFAULT_PROC))
            .andExpect(jsonPath("$.procusers").value(DEFAULT_PROCUSERS))
            .andExpect(jsonPath("$.usersqty").value(DEFAULT_USERSQTY))
            .andExpect(jsonPath("$.usersadded").value(DEFAULT_USERSADDED))
            .andExpect(jsonPath("$.ignore").value(DEFAULT_IGNORE))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()));
    }

    @Test
    public void getNonExistingVkGroup() throws Exception {
        // Get the vkGroup
        restVkGroupMockMvc.perform(get("/api/vk-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVkGroup() throws Exception {
        // Initialize the database
        vkGroupRepository.save(vkGroup);
        vkGroupSearchRepository.save(vkGroup);
        int databaseSizeBeforeUpdate = vkGroupRepository.findAll().size();

        // Update the vkGroup
        VkGroup updatedVkGroup = vkGroupRepository.findOne(vkGroup.getId());
        updatedVkGroup
            .groupid(UPDATED_GROUPID)
            .name(UPDATED_NAME)
            .screenname(UPDATED_SCREENNAME)
            .type(UPDATED_TYPE)
            .isclosed(UPDATED_ISCLOSED)
            .proc(UPDATED_PROC)
            .procusers(UPDATED_PROCUSERS)
            .usersqty(UPDATED_USERSQTY)
            .usersadded(UPDATED_USERSADDED)
            .ignore(UPDATED_IGNORE)
            .stateProvince(UPDATED_STATE_PROVINCE);
        VkGroupDTO vkGroupDTO = vkGroupMapper.toDto(updatedVkGroup);

        restVkGroupMockMvc.perform(put("/api/vk-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkGroupDTO)))
            .andExpect(status().isOk());

        // Validate the VkGroup in the database
        List<VkGroup> vkGroupList = vkGroupRepository.findAll();
        assertThat(vkGroupList).hasSize(databaseSizeBeforeUpdate);
        VkGroup testVkGroup = vkGroupList.get(vkGroupList.size() - 1);
        assertThat(testVkGroup.getGroupid()).isEqualTo(UPDATED_GROUPID);
        assertThat(testVkGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVkGroup.getScreenname()).isEqualTo(UPDATED_SCREENNAME);
        assertThat(testVkGroup.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVkGroup.getIsclosed()).isEqualTo(UPDATED_ISCLOSED);
        assertThat(testVkGroup.getProc()).isEqualTo(UPDATED_PROC);
        assertThat(testVkGroup.getProcusers()).isEqualTo(UPDATED_PROCUSERS);
        assertThat(testVkGroup.getUsersqty()).isEqualTo(UPDATED_USERSQTY);
        assertThat(testVkGroup.getUsersadded()).isEqualTo(UPDATED_USERSADDED);
        assertThat(testVkGroup.getIgnore()).isEqualTo(UPDATED_IGNORE);
        assertThat(testVkGroup.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);

        // Validate the VkGroup in Elasticsearch
        VkGroup vkGroupEs = vkGroupSearchRepository.findOne(testVkGroup.getId());
        assertThat(vkGroupEs).isEqualToIgnoringGivenFields(testVkGroup);
    }

    @Test
    public void updateNonExistingVkGroup() throws Exception {
        int databaseSizeBeforeUpdate = vkGroupRepository.findAll().size();

        // Create the VkGroup
        VkGroupDTO vkGroupDTO = vkGroupMapper.toDto(vkGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVkGroupMockMvc.perform(put("/api/vk-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vkGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the VkGroup in the database
        List<VkGroup> vkGroupList = vkGroupRepository.findAll();
        assertThat(vkGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVkGroup() throws Exception {
        // Initialize the database
        vkGroupRepository.save(vkGroup);
        vkGroupSearchRepository.save(vkGroup);
        int databaseSizeBeforeDelete = vkGroupRepository.findAll().size();

        // Get the vkGroup
        restVkGroupMockMvc.perform(delete("/api/vk-groups/{id}", vkGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vkGroupExistsInEs = vkGroupSearchRepository.exists(vkGroup.getId());
        assertThat(vkGroupExistsInEs).isFalse();

        // Validate the database is empty
        List<VkGroup> vkGroupList = vkGroupRepository.findAll();
        assertThat(vkGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchVkGroup() throws Exception {
        // Initialize the database
        vkGroupRepository.save(vkGroup);
        vkGroupSearchRepository.save(vkGroup);

        // Search the vkGroup
        restVkGroupMockMvc.perform(get("/api/_search/vk-groups?query=id:" + vkGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vkGroup.getId())))
            .andExpect(jsonPath("$.[*].groupid").value(hasItem(DEFAULT_GROUPID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].screenname").value(hasItem(DEFAULT_SCREENNAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isclosed").value(hasItem(DEFAULT_ISCLOSED)))
            .andExpect(jsonPath("$.[*].proc").value(hasItem(DEFAULT_PROC)))
            .andExpect(jsonPath("$.[*].procusers").value(hasItem(DEFAULT_PROCUSERS)))
            .andExpect(jsonPath("$.[*].usersqty").value(hasItem(DEFAULT_USERSQTY)))
            .andExpect(jsonPath("$.[*].usersadded").value(hasItem(DEFAULT_USERSADDED)))
            .andExpect(jsonPath("$.[*].ignore").value(hasItem(DEFAULT_IGNORE)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VkGroup.class);
        VkGroup vkGroup1 = new VkGroup();
        vkGroup1.setId("id1");
        VkGroup vkGroup2 = new VkGroup();
        vkGroup2.setId(vkGroup1.getId());
        assertThat(vkGroup1).isEqualTo(vkGroup2);
        vkGroup2.setId("id2");
        assertThat(vkGroup1).isNotEqualTo(vkGroup2);
        vkGroup1.setId(null);
        assertThat(vkGroup1).isNotEqualTo(vkGroup2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VkGroupDTO.class);
        VkGroupDTO vkGroupDTO1 = new VkGroupDTO();
        vkGroupDTO1.setId("id1");
        VkGroupDTO vkGroupDTO2 = new VkGroupDTO();
        assertThat(vkGroupDTO1).isNotEqualTo(vkGroupDTO2);
        vkGroupDTO2.setId(vkGroupDTO1.getId());
        assertThat(vkGroupDTO1).isEqualTo(vkGroupDTO2);
        vkGroupDTO2.setId("id2");
        assertThat(vkGroupDTO1).isNotEqualTo(vkGroupDTO2);
        vkGroupDTO1.setId(null);
        assertThat(vkGroupDTO1).isNotEqualTo(vkGroupDTO2);
    }
}

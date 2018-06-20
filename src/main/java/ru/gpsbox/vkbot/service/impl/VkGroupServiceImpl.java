package ru.gpsbox.vkbot.service.impl;

import ru.gpsbox.vkbot.service.VkGroupService;
import ru.gpsbox.vkbot.domain.VkGroup;
import ru.gpsbox.vkbot.repository.VkGroupRepository;
import ru.gpsbox.vkbot.repository.search.VkGroupSearchRepository;
import ru.gpsbox.vkbot.service.dto.VkGroupDTO;
import ru.gpsbox.vkbot.service.mapper.VkGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VkGroup.
 */
@Service
public class VkGroupServiceImpl implements VkGroupService {

    private final Logger log = LoggerFactory.getLogger(VkGroupServiceImpl.class);

    private final VkGroupRepository vkGroupRepository;

    private final VkGroupMapper vkGroupMapper;

    private final VkGroupSearchRepository vkGroupSearchRepository;

    public VkGroupServiceImpl(VkGroupRepository vkGroupRepository, VkGroupMapper vkGroupMapper, VkGroupSearchRepository vkGroupSearchRepository) {
        this.vkGroupRepository = vkGroupRepository;
        this.vkGroupMapper = vkGroupMapper;
        this.vkGroupSearchRepository = vkGroupSearchRepository;
    }

    /**
     * Save a vkGroup.
     *
     * @param vkGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VkGroupDTO save(VkGroupDTO vkGroupDTO) {
        log.debug("Request to save VkGroup : {}", vkGroupDTO);
        VkGroup vkGroup = vkGroupMapper.toEntity(vkGroupDTO);
        vkGroup = vkGroupRepository.save(vkGroup);
        VkGroupDTO result = vkGroupMapper.toDto(vkGroup);
        vkGroupSearchRepository.save(vkGroup);
        return result;
    }

    /**
     * Get all the vkGroups.
     *
     * @return the list of entities
     */
    @Override
    public List<VkGroupDTO> findAll() {
        log.debug("Request to get all VkGroups");
        return vkGroupRepository.findAll().stream()
            .map(vkGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vkGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public VkGroupDTO findOne(String id) {
        log.debug("Request to get VkGroup : {}", id);
        VkGroup vkGroup = vkGroupRepository.findOne(id);
        return vkGroupMapper.toDto(vkGroup);
    }

    /**
     * Delete the vkGroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VkGroup : {}", id);
        vkGroupRepository.delete(id);
        vkGroupSearchRepository.delete(id);
    }

    /**
     * Search for the vkGroup corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<VkGroupDTO> search(String query) {
        log.debug("Request to search VkGroups for query {}", query);
        return StreamSupport
            .stream(vkGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vkGroupMapper::toDto)
            .collect(Collectors.toList());
    }
}

package ru.gpsbox.natmob.service.impl;

import ru.gpsbox.natmob.service.VkUserService;
import ru.gpsbox.natmob.domain.VkUser;
import ru.gpsbox.natmob.repository.VkUserRepository;
import ru.gpsbox.natmob.repository.search.VkUserSearchRepository;
import ru.gpsbox.natmob.service.dto.VkUserDTO;
import ru.gpsbox.natmob.service.mapper.VkUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VkUser.
 */
@Service
public class VkUserServiceImpl implements VkUserService {

    private final Logger log = LoggerFactory.getLogger(VkUserServiceImpl.class);

    private final VkUserRepository vkUserRepository;

    private final VkUserMapper vkUserMapper;

    private final VkUserSearchRepository vkUserSearchRepository;

    public VkUserServiceImpl(VkUserRepository vkUserRepository, VkUserMapper vkUserMapper, VkUserSearchRepository vkUserSearchRepository) {
        this.vkUserRepository = vkUserRepository;
        this.vkUserMapper = vkUserMapper;
        this.vkUserSearchRepository = vkUserSearchRepository;
    }

    /**
     * Save a vkUser.
     *
     * @param vkUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VkUserDTO save(VkUserDTO vkUserDTO) {
        log.debug("Request to save VkUser : {}", vkUserDTO);
        VkUser vkUser = vkUserMapper.toEntity(vkUserDTO);
        vkUser = vkUserRepository.save(vkUser);
        VkUserDTO result = vkUserMapper.toDto(vkUser);
        vkUserSearchRepository.save(vkUser);
        return result;
    }

    /**
     * Get all the vkUsers.
     *
     * @return the list of entities
     */
    @Override
    public List<VkUserDTO> findAll() {
        log.debug("Request to get all VkUsers");
        return vkUserRepository.findAll().stream()
            .map(vkUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vkUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public VkUserDTO findOne(String id) {
        log.debug("Request to get VkUser : {}", id);
        VkUser vkUser = vkUserRepository.findOne(id);
        return vkUserMapper.toDto(vkUser);
    }

    /**
     * Delete the vkUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VkUser : {}", id);
        vkUserRepository.delete(id);
        vkUserSearchRepository.delete(id);
    }

    /**
     * Search for the vkUser corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<VkUserDTO> search(String query) {
        log.debug("Request to search VkUsers for query {}", query);
        return StreamSupport
            .stream(vkUserSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vkUserMapper::toDto)
            .collect(Collectors.toList());
    }
}

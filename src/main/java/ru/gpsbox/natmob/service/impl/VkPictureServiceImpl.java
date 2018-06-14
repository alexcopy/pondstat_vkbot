package ru.gpsbox.natmob.service.impl;

import ru.gpsbox.natmob.service.VkPictureService;
import ru.gpsbox.natmob.domain.VkPicture;
import ru.gpsbox.natmob.repository.VkPictureRepository;
import ru.gpsbox.natmob.repository.search.VkPictureSearchRepository;
import ru.gpsbox.natmob.service.dto.VkPictureDTO;
import ru.gpsbox.natmob.service.mapper.VkPictureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VkPicture.
 */
@Service
public class VkPictureServiceImpl implements VkPictureService {

    private final Logger log = LoggerFactory.getLogger(VkPictureServiceImpl.class);

    private final VkPictureRepository vkPictureRepository;

    private final VkPictureMapper vkPictureMapper;

    private final VkPictureSearchRepository vkPictureSearchRepository;

    public VkPictureServiceImpl(VkPictureRepository vkPictureRepository, VkPictureMapper vkPictureMapper, VkPictureSearchRepository vkPictureSearchRepository) {
        this.vkPictureRepository = vkPictureRepository;
        this.vkPictureMapper = vkPictureMapper;
        this.vkPictureSearchRepository = vkPictureSearchRepository;
    }

    /**
     * Save a vkPicture.
     *
     * @param vkPictureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VkPictureDTO save(VkPictureDTO vkPictureDTO) {
        log.debug("Request to save VkPicture : {}", vkPictureDTO);
        VkPicture vkPicture = vkPictureMapper.toEntity(vkPictureDTO);
        vkPicture = vkPictureRepository.save(vkPicture);
        VkPictureDTO result = vkPictureMapper.toDto(vkPicture);
        vkPictureSearchRepository.save(vkPicture);
        return result;
    }

    /**
     * Get all the vkPictures.
     *
     * @return the list of entities
     */
    @Override
    public List<VkPictureDTO> findAll() {
        log.debug("Request to get all VkPictures");
        return vkPictureRepository.findAll().stream()
            .map(vkPictureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vkPicture by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public VkPictureDTO findOne(String id) {
        log.debug("Request to get VkPicture : {}", id);
        VkPicture vkPicture = vkPictureRepository.findOne(id);
        return vkPictureMapper.toDto(vkPicture);
    }

    /**
     * Delete the vkPicture by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VkPicture : {}", id);
        vkPictureRepository.delete(id);
        vkPictureSearchRepository.delete(id);
    }

    /**
     * Search for the vkPicture corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<VkPictureDTO> search(String query) {
        log.debug("Request to search VkPictures for query {}", query);
        return StreamSupport
            .stream(vkPictureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vkPictureMapper::toDto)
            .collect(Collectors.toList());
    }
}

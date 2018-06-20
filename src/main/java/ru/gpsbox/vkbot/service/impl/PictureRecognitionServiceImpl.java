package ru.gpsbox.vkbot.service.impl;

import ru.gpsbox.vkbot.service.PictureRecognitionService;
import ru.gpsbox.vkbot.domain.PictureRecognition;
import ru.gpsbox.vkbot.repository.PictureRecognitionRepository;
import ru.gpsbox.vkbot.repository.search.PictureRecognitionSearchRepository;
import ru.gpsbox.vkbot.service.dto.PictureRecognitionDTO;
import ru.gpsbox.vkbot.service.mapper.PictureRecognitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PictureRecognition.
 */
@Service
public class PictureRecognitionServiceImpl implements PictureRecognitionService {

    private final Logger log = LoggerFactory.getLogger(PictureRecognitionServiceImpl.class);

    private final PictureRecognitionRepository pictureRecognitionRepository;

    private final PictureRecognitionMapper pictureRecognitionMapper;

    private final PictureRecognitionSearchRepository pictureRecognitionSearchRepository;

    public PictureRecognitionServiceImpl(PictureRecognitionRepository pictureRecognitionRepository, PictureRecognitionMapper pictureRecognitionMapper, PictureRecognitionSearchRepository pictureRecognitionSearchRepository) {
        this.pictureRecognitionRepository = pictureRecognitionRepository;
        this.pictureRecognitionMapper = pictureRecognitionMapper;
        this.pictureRecognitionSearchRepository = pictureRecognitionSearchRepository;
    }

    /**
     * Save a pictureRecognition.
     *
     * @param pictureRecognitionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PictureRecognitionDTO save(PictureRecognitionDTO pictureRecognitionDTO) {
        log.debug("Request to save PictureRecognition : {}", pictureRecognitionDTO);
        PictureRecognition pictureRecognition = pictureRecognitionMapper.toEntity(pictureRecognitionDTO);
        pictureRecognition = pictureRecognitionRepository.save(pictureRecognition);
        PictureRecognitionDTO result = pictureRecognitionMapper.toDto(pictureRecognition);
        pictureRecognitionSearchRepository.save(pictureRecognition);
        return result;
    }

    /**
     * Get all the pictureRecognitions.
     *
     * @return the list of entities
     */
    @Override
    public List<PictureRecognitionDTO> findAll() {
        log.debug("Request to get all PictureRecognitions");
        return pictureRecognitionRepository.findAll().stream()
            .map(pictureRecognitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pictureRecognition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public PictureRecognitionDTO findOne(String id) {
        log.debug("Request to get PictureRecognition : {}", id);
        PictureRecognition pictureRecognition = pictureRecognitionRepository.findOne(id);
        return pictureRecognitionMapper.toDto(pictureRecognition);
    }

    /**
     * Delete the pictureRecognition by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete PictureRecognition : {}", id);
        pictureRecognitionRepository.delete(id);
        pictureRecognitionSearchRepository.delete(id);
    }

    /**
     * Search for the pictureRecognition corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<PictureRecognitionDTO> search(String query) {
        log.debug("Request to search PictureRecognitions for query {}", query);
        return StreamSupport
            .stream(pictureRecognitionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(pictureRecognitionMapper::toDto)
            .collect(Collectors.toList());
    }
}

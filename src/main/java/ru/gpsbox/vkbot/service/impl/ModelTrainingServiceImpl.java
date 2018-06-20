package ru.gpsbox.vkbot.service.impl;

import ru.gpsbox.vkbot.service.ModelTrainingService;
import ru.gpsbox.vkbot.domain.ModelTraining;
import ru.gpsbox.vkbot.repository.ModelTrainingRepository;
import ru.gpsbox.vkbot.repository.search.ModelTrainingSearchRepository;
import ru.gpsbox.vkbot.service.dto.ModelTrainingDTO;
import ru.gpsbox.vkbot.service.mapper.ModelTrainingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ModelTraining.
 */
@Service
public class ModelTrainingServiceImpl implements ModelTrainingService {

    private final Logger log = LoggerFactory.getLogger(ModelTrainingServiceImpl.class);

    private final ModelTrainingRepository modelTrainingRepository;

    private final ModelTrainingMapper modelTrainingMapper;

    private final ModelTrainingSearchRepository modelTrainingSearchRepository;

    public ModelTrainingServiceImpl(ModelTrainingRepository modelTrainingRepository, ModelTrainingMapper modelTrainingMapper, ModelTrainingSearchRepository modelTrainingSearchRepository) {
        this.modelTrainingRepository = modelTrainingRepository;
        this.modelTrainingMapper = modelTrainingMapper;
        this.modelTrainingSearchRepository = modelTrainingSearchRepository;
    }

    /**
     * Save a modelTraining.
     *
     * @param modelTrainingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ModelTrainingDTO save(ModelTrainingDTO modelTrainingDTO) {
        log.debug("Request to save ModelTraining : {}", modelTrainingDTO);
        ModelTraining modelTraining = modelTrainingMapper.toEntity(modelTrainingDTO);
        modelTraining = modelTrainingRepository.save(modelTraining);
        ModelTrainingDTO result = modelTrainingMapper.toDto(modelTraining);
        modelTrainingSearchRepository.save(modelTraining);
        return result;
    }

    /**
     * Get all the modelTrainings.
     *
     * @return the list of entities
     */
    @Override
    public List<ModelTrainingDTO> findAll() {
        log.debug("Request to get all ModelTrainings");
        return modelTrainingRepository.findAll().stream()
            .map(modelTrainingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one modelTraining by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public ModelTrainingDTO findOne(String id) {
        log.debug("Request to get ModelTraining : {}", id);
        ModelTraining modelTraining = modelTrainingRepository.findOne(id);
        return modelTrainingMapper.toDto(modelTraining);
    }

    /**
     * Delete the modelTraining by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ModelTraining : {}", id);
        modelTrainingRepository.delete(id);
        modelTrainingSearchRepository.delete(id);
    }

    /**
     * Search for the modelTraining corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<ModelTrainingDTO> search(String query) {
        log.debug("Request to search ModelTrainings for query {}", query);
        return StreamSupport
            .stream(modelTrainingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(modelTrainingMapper::toDto)
            .collect(Collectors.toList());
    }
}

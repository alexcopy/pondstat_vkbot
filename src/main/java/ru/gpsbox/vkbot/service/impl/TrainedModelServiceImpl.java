package ru.gpsbox.vkbot.service.impl;

import ru.gpsbox.vkbot.service.TrainedModelService;
import ru.gpsbox.vkbot.domain.TrainedModel;
import ru.gpsbox.vkbot.repository.TrainedModelRepository;
import ru.gpsbox.vkbot.repository.search.TrainedModelSearchRepository;
import ru.gpsbox.vkbot.service.dto.TrainedModelDTO;
import ru.gpsbox.vkbot.service.mapper.TrainedModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TrainedModel.
 */
@Service
public class TrainedModelServiceImpl implements TrainedModelService {

    private final Logger log = LoggerFactory.getLogger(TrainedModelServiceImpl.class);

    private final TrainedModelRepository trainedModelRepository;

    private final TrainedModelMapper trainedModelMapper;

    private final TrainedModelSearchRepository trainedModelSearchRepository;

    public TrainedModelServiceImpl(TrainedModelRepository trainedModelRepository, TrainedModelMapper trainedModelMapper, TrainedModelSearchRepository trainedModelSearchRepository) {
        this.trainedModelRepository = trainedModelRepository;
        this.trainedModelMapper = trainedModelMapper;
        this.trainedModelSearchRepository = trainedModelSearchRepository;
    }

    /**
     * Save a trainedModel.
     *
     * @param trainedModelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TrainedModelDTO save(TrainedModelDTO trainedModelDTO) {
        log.debug("Request to save TrainedModel : {}", trainedModelDTO);
        TrainedModel trainedModel = trainedModelMapper.toEntity(trainedModelDTO);
        trainedModel = trainedModelRepository.save(trainedModel);
        TrainedModelDTO result = trainedModelMapper.toDto(trainedModel);
        trainedModelSearchRepository.save(trainedModel);
        return result;
    }

    /**
     * Get all the trainedModels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<TrainedModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainedModels");
        return trainedModelRepository.findAll(pageable)
            .map(trainedModelMapper::toDto);
    }

    /**
     * Get one trainedModel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public TrainedModelDTO findOne(String id) {
        log.debug("Request to get TrainedModel : {}", id);
        TrainedModel trainedModel = trainedModelRepository.findOne(id);
        return trainedModelMapper.toDto(trainedModel);
    }

    /**
     * Delete the trainedModel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete TrainedModel : {}", id);
        trainedModelRepository.delete(id);
        trainedModelSearchRepository.delete(id);
    }

    /**
     * Search for the trainedModel corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<TrainedModelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TrainedModels for query {}", query);
        Page<TrainedModel> result = trainedModelSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(trainedModelMapper::toDto);
    }
}

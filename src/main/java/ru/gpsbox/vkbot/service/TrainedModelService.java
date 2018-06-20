package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.TrainedModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TrainedModel.
 */
public interface TrainedModelService {

    /**
     * Save a trainedModel.
     *
     * @param trainedModelDTO the entity to save
     * @return the persisted entity
     */
    TrainedModelDTO save(TrainedModelDTO trainedModelDTO);

    /**
     * Get all the trainedModels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TrainedModelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainedModel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TrainedModelDTO findOne(String id);

    /**
     * Delete the "id" trainedModel.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the trainedModel corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TrainedModelDTO> search(String query, Pageable pageable);
}

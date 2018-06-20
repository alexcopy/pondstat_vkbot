package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.ModelTrainingDTO;
import java.util.List;

/**
 * Service Interface for managing ModelTraining.
 */
public interface ModelTrainingService {

    /**
     * Save a modelTraining.
     *
     * @param modelTrainingDTO the entity to save
     * @return the persisted entity
     */
    ModelTrainingDTO save(ModelTrainingDTO modelTrainingDTO);

    /**
     * Get all the modelTrainings.
     *
     * @return the list of entities
     */
    List<ModelTrainingDTO> findAll();

    /**
     * Get the "id" modelTraining.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ModelTrainingDTO findOne(String id);

    /**
     * Delete the "id" modelTraining.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the modelTraining corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ModelTrainingDTO> search(String query);
}

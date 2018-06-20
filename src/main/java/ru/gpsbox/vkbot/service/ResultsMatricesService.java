package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.ResultsMatricesDTO;
import java.util.List;

/**
 * Service Interface for managing ResultsMatrices.
 */
public interface ResultsMatricesService {

    /**
     * Save a resultsMatrices.
     *
     * @param resultsMatricesDTO the entity to save
     * @return the persisted entity
     */
    ResultsMatricesDTO save(ResultsMatricesDTO resultsMatricesDTO);

    /**
     * Get all the resultsMatrices.
     *
     * @return the list of entities
     */
    List<ResultsMatricesDTO> findAll();

    /**
     * Get the "id" resultsMatrices.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ResultsMatricesDTO findOne(String id);

    /**
     * Delete the "id" resultsMatrices.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the resultsMatrices corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ResultsMatricesDTO> search(String query);
}

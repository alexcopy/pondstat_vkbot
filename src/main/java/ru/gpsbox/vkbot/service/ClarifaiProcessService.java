package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.ClarifaiProcessDTO;
import java.util.List;

/**
 * Service Interface for managing ClarifaiProcess.
 */
public interface ClarifaiProcessService {

    /**
     * Save a clarifaiProcess.
     *
     * @param clarifaiProcessDTO the entity to save
     * @return the persisted entity
     */
    ClarifaiProcessDTO save(ClarifaiProcessDTO clarifaiProcessDTO);

    /**
     * Get all the clarifaiProcesses.
     *
     * @return the list of entities
     */
    List<ClarifaiProcessDTO> findAll();

    /**
     * Get the "id" clarifaiProcess.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClarifaiProcessDTO findOne(String id);

    /**
     * Delete the "id" clarifaiProcess.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the clarifaiProcess corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ClarifaiProcessDTO> search(String query);
}

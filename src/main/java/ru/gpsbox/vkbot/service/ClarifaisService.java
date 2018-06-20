package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.ClarifaisDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Clarifais.
 */
public interface ClarifaisService {

    /**
     * Save a clarifais.
     *
     * @param clarifaisDTO the entity to save
     * @return the persisted entity
     */
    ClarifaisDTO save(ClarifaisDTO clarifaisDTO);

    /**
     * Get all the clarifais.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClarifaisDTO> findAll(Pageable pageable);

    /**
     * Get the "id" clarifais.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClarifaisDTO findOne(String id);

    /**
     * Delete the "id" clarifais.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the clarifais corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClarifaisDTO> search(String query, Pageable pageable);
}

package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.SuggestIgnoredDTO;
import java.util.List;

/**
 * Service Interface for managing SuggestIgnored.
 */
public interface SuggestIgnoredService {

    /**
     * Save a suggestIgnored.
     *
     * @param suggestIgnoredDTO the entity to save
     * @return the persisted entity
     */
    SuggestIgnoredDTO save(SuggestIgnoredDTO suggestIgnoredDTO);

    /**
     * Get all the suggestIgnoreds.
     *
     * @return the list of entities
     */
    List<SuggestIgnoredDTO> findAll();

    /**
     * Get the "id" suggestIgnored.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SuggestIgnoredDTO findOne(String id);

    /**
     * Delete the "id" suggestIgnored.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the suggestIgnored corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<SuggestIgnoredDTO> search(String query);
}

package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.SelectedKeyWordsDTO;
import java.util.List;

/**
 * Service Interface for managing SelectedKeyWords.
 */
public interface SelectedKeyWordsService {

    /**
     * Save a selectedKeyWords.
     *
     * @param selectedKeyWordsDTO the entity to save
     * @return the persisted entity
     */
    SelectedKeyWordsDTO save(SelectedKeyWordsDTO selectedKeyWordsDTO);

    /**
     * Get all the selectedKeyWords.
     *
     * @return the list of entities
     */
    List<SelectedKeyWordsDTO> findAll();

    /**
     * Get the "id" selectedKeyWords.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SelectedKeyWordsDTO findOne(String id);

    /**
     * Delete the "id" selectedKeyWords.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the selectedKeyWords corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<SelectedKeyWordsDTO> search(String query);
}

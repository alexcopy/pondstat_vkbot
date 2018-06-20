package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.VkGroupDTO;
import java.util.List;

/**
 * Service Interface for managing VkGroup.
 */
public interface VkGroupService {

    /**
     * Save a vkGroup.
     *
     * @param vkGroupDTO the entity to save
     * @return the persisted entity
     */
    VkGroupDTO save(VkGroupDTO vkGroupDTO);

    /**
     * Get all the vkGroups.
     *
     * @return the list of entities
     */
    List<VkGroupDTO> findAll();

    /**
     * Get the "id" vkGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VkGroupDTO findOne(String id);

    /**
     * Delete the "id" vkGroup.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the vkGroup corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<VkGroupDTO> search(String query);
}

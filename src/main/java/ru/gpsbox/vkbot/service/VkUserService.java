package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.VkUserDTO;
import java.util.List;

/**
 * Service Interface for managing VkUser.
 */
public interface VkUserService {

    /**
     * Save a vkUser.
     *
     * @param vkUserDTO the entity to save
     * @return the persisted entity
     */
    VkUserDTO save(VkUserDTO vkUserDTO);

    /**
     * Get all the vkUsers.
     *
     * @return the list of entities
     */
    List<VkUserDTO> findAll();

    /**
     * Get the "id" vkUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VkUserDTO findOne(String id);

    /**
     * Delete the "id" vkUser.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the vkUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<VkUserDTO> search(String query);
}

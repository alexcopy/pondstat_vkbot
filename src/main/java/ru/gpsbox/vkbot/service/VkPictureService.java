package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.VkPictureDTO;
import java.util.List;

/**
 * Service Interface for managing VkPicture.
 */
public interface VkPictureService {

    /**
     * Save a vkPicture.
     *
     * @param vkPictureDTO the entity to save
     * @return the persisted entity
     */
    VkPictureDTO save(VkPictureDTO vkPictureDTO);

    /**
     * Get all the vkPictures.
     *
     * @return the list of entities
     */
    List<VkPictureDTO> findAll();

    /**
     * Get the "id" vkPicture.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VkPictureDTO findOne(String id);

    /**
     * Delete the "id" vkPicture.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the vkPicture corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<VkPictureDTO> search(String query);
}

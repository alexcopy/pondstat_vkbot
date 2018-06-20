package ru.gpsbox.vkbot.service;

import ru.gpsbox.vkbot.service.dto.PictureRecognitionDTO;
import java.util.List;

/**
 * Service Interface for managing PictureRecognition.
 */
public interface PictureRecognitionService {

    /**
     * Save a pictureRecognition.
     *
     * @param pictureRecognitionDTO the entity to save
     * @return the persisted entity
     */
    PictureRecognitionDTO save(PictureRecognitionDTO pictureRecognitionDTO);

    /**
     * Get all the pictureRecognitions.
     *
     * @return the list of entities
     */
    List<PictureRecognitionDTO> findAll();

    /**
     * Get the "id" pictureRecognition.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PictureRecognitionDTO findOne(String id);

    /**
     * Delete the "id" pictureRecognition.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the pictureRecognition corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PictureRecognitionDTO> search(String query);
}

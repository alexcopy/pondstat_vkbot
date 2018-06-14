package ru.gpsbox.natmob.repository;

import ru.gpsbox.natmob.domain.VkPicture;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the VkPicture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VkPictureRepository extends MongoRepository<VkPicture, String> {

}

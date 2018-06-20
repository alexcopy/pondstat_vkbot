package ru.gpsbox.vkbot.repository;

import ru.gpsbox.vkbot.domain.PictureRecognition;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the PictureRecognition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PictureRecognitionRepository extends MongoRepository<PictureRecognition, String> {

}

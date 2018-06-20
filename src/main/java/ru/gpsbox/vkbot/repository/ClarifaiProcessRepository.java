package ru.gpsbox.vkbot.repository;

import ru.gpsbox.vkbot.domain.ClarifaiProcess;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ClarifaiProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClarifaiProcessRepository extends MongoRepository<ClarifaiProcess, String> {

}

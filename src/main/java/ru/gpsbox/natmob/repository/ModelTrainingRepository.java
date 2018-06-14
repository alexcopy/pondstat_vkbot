package ru.gpsbox.natmob.repository;

import ru.gpsbox.natmob.domain.ModelTraining;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ModelTraining entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelTrainingRepository extends MongoRepository<ModelTraining, String> {

}

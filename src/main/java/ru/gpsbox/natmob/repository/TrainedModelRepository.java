package ru.gpsbox.natmob.repository;

import ru.gpsbox.natmob.domain.TrainedModel;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the TrainedModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainedModelRepository extends MongoRepository<TrainedModel, String> {

}

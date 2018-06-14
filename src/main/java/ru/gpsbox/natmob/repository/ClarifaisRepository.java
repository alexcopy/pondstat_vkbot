package ru.gpsbox.natmob.repository;

import ru.gpsbox.natmob.domain.Clarifais;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Clarifais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClarifaisRepository extends MongoRepository<Clarifais, String> {

}

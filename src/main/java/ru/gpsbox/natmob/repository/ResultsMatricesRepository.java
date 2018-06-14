package ru.gpsbox.natmob.repository;

import ru.gpsbox.natmob.domain.ResultsMatrices;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ResultsMatrices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultsMatricesRepository extends MongoRepository<ResultsMatrices, String> {

}

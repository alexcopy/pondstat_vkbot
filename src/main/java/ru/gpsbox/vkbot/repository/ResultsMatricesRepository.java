package ru.gpsbox.vkbot.repository;

import ru.gpsbox.vkbot.domain.ResultsMatrices;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ResultsMatrices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultsMatricesRepository extends MongoRepository<ResultsMatrices, String> {

}

package ru.gpsbox.natmob.repository;

import ru.gpsbox.natmob.domain.SuggestIgnored;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the SuggestIgnored entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuggestIgnoredRepository extends MongoRepository<SuggestIgnored, String> {

}

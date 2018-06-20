package ru.gpsbox.vkbot.repository;

import ru.gpsbox.vkbot.domain.SuggestIgnored;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the SuggestIgnored entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuggestIgnoredRepository extends MongoRepository<SuggestIgnored, String> {

}

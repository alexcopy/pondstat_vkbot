package ru.gpsbox.natmob.repository;

import ru.gpsbox.natmob.domain.SelectedKeyWords;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the SelectedKeyWords entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelectedKeyWordsRepository extends MongoRepository<SelectedKeyWords, String> {

}

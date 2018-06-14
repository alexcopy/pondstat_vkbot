package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.ModelTraining;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ModelTraining entity.
 */
public interface ModelTrainingSearchRepository extends ElasticsearchRepository<ModelTraining, String> {
}

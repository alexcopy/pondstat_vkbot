package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.TrainedModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TrainedModel entity.
 */
public interface TrainedModelSearchRepository extends ElasticsearchRepository<TrainedModel, String> {
}

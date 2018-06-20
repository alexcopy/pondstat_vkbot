package ru.gpsbox.vkbot.repository.search;

import ru.gpsbox.vkbot.domain.TrainedModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TrainedModel entity.
 */
public interface TrainedModelSearchRepository extends ElasticsearchRepository<TrainedModel, String> {
}

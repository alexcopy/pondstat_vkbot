package ru.gpsbox.vkbot.repository.search;

import ru.gpsbox.vkbot.domain.Clarifais;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Clarifais entity.
 */
public interface ClarifaisSearchRepository extends ElasticsearchRepository<Clarifais, String> {
}

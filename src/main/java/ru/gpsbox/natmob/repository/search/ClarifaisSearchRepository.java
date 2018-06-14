package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.Clarifais;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Clarifais entity.
 */
public interface ClarifaisSearchRepository extends ElasticsearchRepository<Clarifais, String> {
}

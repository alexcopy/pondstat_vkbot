package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.ClarifaiProcess;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ClarifaiProcess entity.
 */
public interface ClarifaiProcessSearchRepository extends ElasticsearchRepository<ClarifaiProcess, String> {
}

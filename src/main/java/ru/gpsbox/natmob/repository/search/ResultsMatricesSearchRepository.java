package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.ResultsMatrices;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ResultsMatrices entity.
 */
public interface ResultsMatricesSearchRepository extends ElasticsearchRepository<ResultsMatrices, String> {
}

package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.SuggestIgnored;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SuggestIgnored entity.
 */
public interface SuggestIgnoredSearchRepository extends ElasticsearchRepository<SuggestIgnored, String> {
}

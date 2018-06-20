package ru.gpsbox.vkbot.repository.search;

import ru.gpsbox.vkbot.domain.SuggestIgnored;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SuggestIgnored entity.
 */
public interface SuggestIgnoredSearchRepository extends ElasticsearchRepository<SuggestIgnored, String> {
}

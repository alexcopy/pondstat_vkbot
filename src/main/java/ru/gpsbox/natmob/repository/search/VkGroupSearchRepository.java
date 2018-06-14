package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.VkGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VkGroup entity.
 */
public interface VkGroupSearchRepository extends ElasticsearchRepository<VkGroup, String> {
}

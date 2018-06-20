package ru.gpsbox.vkbot.repository.search;

import ru.gpsbox.vkbot.domain.VkGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VkGroup entity.
 */
public interface VkGroupSearchRepository extends ElasticsearchRepository<VkGroup, String> {
}

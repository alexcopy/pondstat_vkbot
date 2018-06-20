package ru.gpsbox.vkbot.repository.search;

import ru.gpsbox.vkbot.domain.VkUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VkUser entity.
 */
public interface VkUserSearchRepository extends ElasticsearchRepository<VkUser, String> {
}

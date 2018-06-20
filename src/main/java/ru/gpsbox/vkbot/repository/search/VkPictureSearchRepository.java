package ru.gpsbox.vkbot.repository.search;

import ru.gpsbox.vkbot.domain.VkPicture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VkPicture entity.
 */
public interface VkPictureSearchRepository extends ElasticsearchRepository<VkPicture, String> {
}

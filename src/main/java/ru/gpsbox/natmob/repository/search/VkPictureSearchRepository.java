package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.VkPicture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VkPicture entity.
 */
public interface VkPictureSearchRepository extends ElasticsearchRepository<VkPicture, String> {
}

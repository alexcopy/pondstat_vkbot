package ru.gpsbox.vkbot.repository.search;

import ru.gpsbox.vkbot.domain.PictureRecognition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PictureRecognition entity.
 */
public interface PictureRecognitionSearchRepository extends ElasticsearchRepository<PictureRecognition, String> {
}

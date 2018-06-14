package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.PictureRecognition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PictureRecognition entity.
 */
public interface PictureRecognitionSearchRepository extends ElasticsearchRepository<PictureRecognition, String> {
}

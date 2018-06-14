package ru.gpsbox.natmob.repository.search;

import ru.gpsbox.natmob.domain.SelectedKeyWords;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SelectedKeyWords entity.
 */
public interface SelectedKeyWordsSearchRepository extends ElasticsearchRepository<SelectedKeyWords, String> {
}

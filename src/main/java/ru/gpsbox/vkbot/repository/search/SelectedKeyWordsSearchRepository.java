package ru.gpsbox.vkbot.repository.search;

import ru.gpsbox.vkbot.domain.SelectedKeyWords;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SelectedKeyWords entity.
 */
public interface SelectedKeyWordsSearchRepository extends ElasticsearchRepository<SelectedKeyWords, String> {
}

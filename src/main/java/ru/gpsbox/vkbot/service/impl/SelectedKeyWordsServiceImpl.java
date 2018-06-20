package ru.gpsbox.vkbot.service.impl;

import ru.gpsbox.vkbot.service.SelectedKeyWordsService;
import ru.gpsbox.vkbot.domain.SelectedKeyWords;
import ru.gpsbox.vkbot.repository.SelectedKeyWordsRepository;
import ru.gpsbox.vkbot.repository.search.SelectedKeyWordsSearchRepository;
import ru.gpsbox.vkbot.service.dto.SelectedKeyWordsDTO;
import ru.gpsbox.vkbot.service.mapper.SelectedKeyWordsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SelectedKeyWords.
 */
@Service
public class SelectedKeyWordsServiceImpl implements SelectedKeyWordsService {

    private final Logger log = LoggerFactory.getLogger(SelectedKeyWordsServiceImpl.class);

    private final SelectedKeyWordsRepository selectedKeyWordsRepository;

    private final SelectedKeyWordsMapper selectedKeyWordsMapper;

    private final SelectedKeyWordsSearchRepository selectedKeyWordsSearchRepository;

    public SelectedKeyWordsServiceImpl(SelectedKeyWordsRepository selectedKeyWordsRepository, SelectedKeyWordsMapper selectedKeyWordsMapper, SelectedKeyWordsSearchRepository selectedKeyWordsSearchRepository) {
        this.selectedKeyWordsRepository = selectedKeyWordsRepository;
        this.selectedKeyWordsMapper = selectedKeyWordsMapper;
        this.selectedKeyWordsSearchRepository = selectedKeyWordsSearchRepository;
    }

    /**
     * Save a selectedKeyWords.
     *
     * @param selectedKeyWordsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SelectedKeyWordsDTO save(SelectedKeyWordsDTO selectedKeyWordsDTO) {
        log.debug("Request to save SelectedKeyWords : {}", selectedKeyWordsDTO);
        SelectedKeyWords selectedKeyWords = selectedKeyWordsMapper.toEntity(selectedKeyWordsDTO);
        selectedKeyWords = selectedKeyWordsRepository.save(selectedKeyWords);
        SelectedKeyWordsDTO result = selectedKeyWordsMapper.toDto(selectedKeyWords);
        selectedKeyWordsSearchRepository.save(selectedKeyWords);
        return result;
    }

    /**
     * Get all the selectedKeyWords.
     *
     * @return the list of entities
     */
    @Override
    public List<SelectedKeyWordsDTO> findAll() {
        log.debug("Request to get all SelectedKeyWords");
        return selectedKeyWordsRepository.findAll().stream()
            .map(selectedKeyWordsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one selectedKeyWords by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public SelectedKeyWordsDTO findOne(String id) {
        log.debug("Request to get SelectedKeyWords : {}", id);
        SelectedKeyWords selectedKeyWords = selectedKeyWordsRepository.findOne(id);
        return selectedKeyWordsMapper.toDto(selectedKeyWords);
    }

    /**
     * Delete the selectedKeyWords by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SelectedKeyWords : {}", id);
        selectedKeyWordsRepository.delete(id);
        selectedKeyWordsSearchRepository.delete(id);
    }

    /**
     * Search for the selectedKeyWords corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<SelectedKeyWordsDTO> search(String query) {
        log.debug("Request to search SelectedKeyWords for query {}", query);
        return StreamSupport
            .stream(selectedKeyWordsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(selectedKeyWordsMapper::toDto)
            .collect(Collectors.toList());
    }
}

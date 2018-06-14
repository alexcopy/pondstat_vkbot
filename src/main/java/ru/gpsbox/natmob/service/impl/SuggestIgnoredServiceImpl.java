package ru.gpsbox.natmob.service.impl;

import ru.gpsbox.natmob.service.SuggestIgnoredService;
import ru.gpsbox.natmob.domain.SuggestIgnored;
import ru.gpsbox.natmob.repository.SuggestIgnoredRepository;
import ru.gpsbox.natmob.repository.search.SuggestIgnoredSearchRepository;
import ru.gpsbox.natmob.service.dto.SuggestIgnoredDTO;
import ru.gpsbox.natmob.service.mapper.SuggestIgnoredMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SuggestIgnored.
 */
@Service
public class SuggestIgnoredServiceImpl implements SuggestIgnoredService {

    private final Logger log = LoggerFactory.getLogger(SuggestIgnoredServiceImpl.class);

    private final SuggestIgnoredRepository suggestIgnoredRepository;

    private final SuggestIgnoredMapper suggestIgnoredMapper;

    private final SuggestIgnoredSearchRepository suggestIgnoredSearchRepository;

    public SuggestIgnoredServiceImpl(SuggestIgnoredRepository suggestIgnoredRepository, SuggestIgnoredMapper suggestIgnoredMapper, SuggestIgnoredSearchRepository suggestIgnoredSearchRepository) {
        this.suggestIgnoredRepository = suggestIgnoredRepository;
        this.suggestIgnoredMapper = suggestIgnoredMapper;
        this.suggestIgnoredSearchRepository = suggestIgnoredSearchRepository;
    }

    /**
     * Save a suggestIgnored.
     *
     * @param suggestIgnoredDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SuggestIgnoredDTO save(SuggestIgnoredDTO suggestIgnoredDTO) {
        log.debug("Request to save SuggestIgnored : {}", suggestIgnoredDTO);
        SuggestIgnored suggestIgnored = suggestIgnoredMapper.toEntity(suggestIgnoredDTO);
        suggestIgnored = suggestIgnoredRepository.save(suggestIgnored);
        SuggestIgnoredDTO result = suggestIgnoredMapper.toDto(suggestIgnored);
        suggestIgnoredSearchRepository.save(suggestIgnored);
        return result;
    }

    /**
     * Get all the suggestIgnoreds.
     *
     * @return the list of entities
     */
    @Override
    public List<SuggestIgnoredDTO> findAll() {
        log.debug("Request to get all SuggestIgnoreds");
        return suggestIgnoredRepository.findAll().stream()
            .map(suggestIgnoredMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one suggestIgnored by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public SuggestIgnoredDTO findOne(String id) {
        log.debug("Request to get SuggestIgnored : {}", id);
        SuggestIgnored suggestIgnored = suggestIgnoredRepository.findOne(id);
        return suggestIgnoredMapper.toDto(suggestIgnored);
    }

    /**
     * Delete the suggestIgnored by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SuggestIgnored : {}", id);
        suggestIgnoredRepository.delete(id);
        suggestIgnoredSearchRepository.delete(id);
    }

    /**
     * Search for the suggestIgnored corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<SuggestIgnoredDTO> search(String query) {
        log.debug("Request to search SuggestIgnoreds for query {}", query);
        return StreamSupport
            .stream(suggestIgnoredSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(suggestIgnoredMapper::toDto)
            .collect(Collectors.toList());
    }
}

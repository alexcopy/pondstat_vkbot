package ru.gpsbox.vkbot.service.impl;

import ru.gpsbox.vkbot.service.ResultsMatricesService;
import ru.gpsbox.vkbot.domain.ResultsMatrices;
import ru.gpsbox.vkbot.repository.ResultsMatricesRepository;
import ru.gpsbox.vkbot.repository.search.ResultsMatricesSearchRepository;
import ru.gpsbox.vkbot.service.dto.ResultsMatricesDTO;
import ru.gpsbox.vkbot.service.mapper.ResultsMatricesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ResultsMatrices.
 */
@Service
public class ResultsMatricesServiceImpl implements ResultsMatricesService {

    private final Logger log = LoggerFactory.getLogger(ResultsMatricesServiceImpl.class);

    private final ResultsMatricesRepository resultsMatricesRepository;

    private final ResultsMatricesMapper resultsMatricesMapper;

    private final ResultsMatricesSearchRepository resultsMatricesSearchRepository;

    public ResultsMatricesServiceImpl(ResultsMatricesRepository resultsMatricesRepository, ResultsMatricesMapper resultsMatricesMapper, ResultsMatricesSearchRepository resultsMatricesSearchRepository) {
        this.resultsMatricesRepository = resultsMatricesRepository;
        this.resultsMatricesMapper = resultsMatricesMapper;
        this.resultsMatricesSearchRepository = resultsMatricesSearchRepository;
    }

    /**
     * Save a resultsMatrices.
     *
     * @param resultsMatricesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ResultsMatricesDTO save(ResultsMatricesDTO resultsMatricesDTO) {
        log.debug("Request to save ResultsMatrices : {}", resultsMatricesDTO);
        ResultsMatrices resultsMatrices = resultsMatricesMapper.toEntity(resultsMatricesDTO);
        resultsMatrices = resultsMatricesRepository.save(resultsMatrices);
        ResultsMatricesDTO result = resultsMatricesMapper.toDto(resultsMatrices);
        resultsMatricesSearchRepository.save(resultsMatrices);
        return result;
    }

    /**
     * Get all the resultsMatrices.
     *
     * @return the list of entities
     */
    @Override
    public List<ResultsMatricesDTO> findAll() {
        log.debug("Request to get all ResultsMatrices");
        return resultsMatricesRepository.findAll().stream()
            .map(resultsMatricesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one resultsMatrices by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public ResultsMatricesDTO findOne(String id) {
        log.debug("Request to get ResultsMatrices : {}", id);
        ResultsMatrices resultsMatrices = resultsMatricesRepository.findOne(id);
        return resultsMatricesMapper.toDto(resultsMatrices);
    }

    /**
     * Delete the resultsMatrices by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ResultsMatrices : {}", id);
        resultsMatricesRepository.delete(id);
        resultsMatricesSearchRepository.delete(id);
    }

    /**
     * Search for the resultsMatrices corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<ResultsMatricesDTO> search(String query) {
        log.debug("Request to search ResultsMatrices for query {}", query);
        return StreamSupport
            .stream(resultsMatricesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(resultsMatricesMapper::toDto)
            .collect(Collectors.toList());
    }
}

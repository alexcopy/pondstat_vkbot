package ru.gpsbox.natmob.service.impl;

import ru.gpsbox.natmob.service.ClarifaiProcessService;
import ru.gpsbox.natmob.domain.ClarifaiProcess;
import ru.gpsbox.natmob.repository.ClarifaiProcessRepository;
import ru.gpsbox.natmob.repository.search.ClarifaiProcessSearchRepository;
import ru.gpsbox.natmob.service.dto.ClarifaiProcessDTO;
import ru.gpsbox.natmob.service.mapper.ClarifaiProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ClarifaiProcess.
 */
@Service
public class ClarifaiProcessServiceImpl implements ClarifaiProcessService {

    private final Logger log = LoggerFactory.getLogger(ClarifaiProcessServiceImpl.class);

    private final ClarifaiProcessRepository clarifaiProcessRepository;

    private final ClarifaiProcessMapper clarifaiProcessMapper;

    private final ClarifaiProcessSearchRepository clarifaiProcessSearchRepository;

    public ClarifaiProcessServiceImpl(ClarifaiProcessRepository clarifaiProcessRepository, ClarifaiProcessMapper clarifaiProcessMapper, ClarifaiProcessSearchRepository clarifaiProcessSearchRepository) {
        this.clarifaiProcessRepository = clarifaiProcessRepository;
        this.clarifaiProcessMapper = clarifaiProcessMapper;
        this.clarifaiProcessSearchRepository = clarifaiProcessSearchRepository;
    }

    /**
     * Save a clarifaiProcess.
     *
     * @param clarifaiProcessDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClarifaiProcessDTO save(ClarifaiProcessDTO clarifaiProcessDTO) {
        log.debug("Request to save ClarifaiProcess : {}", clarifaiProcessDTO);
        ClarifaiProcess clarifaiProcess = clarifaiProcessMapper.toEntity(clarifaiProcessDTO);
        clarifaiProcess = clarifaiProcessRepository.save(clarifaiProcess);
        ClarifaiProcessDTO result = clarifaiProcessMapper.toDto(clarifaiProcess);
        clarifaiProcessSearchRepository.save(clarifaiProcess);
        return result;
    }

    /**
     * Get all the clarifaiProcesses.
     *
     * @return the list of entities
     */
    @Override
    public List<ClarifaiProcessDTO> findAll() {
        log.debug("Request to get all ClarifaiProcesses");
        return clarifaiProcessRepository.findAll().stream()
            .map(clarifaiProcessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one clarifaiProcess by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public ClarifaiProcessDTO findOne(String id) {
        log.debug("Request to get ClarifaiProcess : {}", id);
        ClarifaiProcess clarifaiProcess = clarifaiProcessRepository.findOne(id);
        return clarifaiProcessMapper.toDto(clarifaiProcess);
    }

    /**
     * Delete the clarifaiProcess by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ClarifaiProcess : {}", id);
        clarifaiProcessRepository.delete(id);
        clarifaiProcessSearchRepository.delete(id);
    }

    /**
     * Search for the clarifaiProcess corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<ClarifaiProcessDTO> search(String query) {
        log.debug("Request to search ClarifaiProcesses for query {}", query);
        return StreamSupport
            .stream(clarifaiProcessSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(clarifaiProcessMapper::toDto)
            .collect(Collectors.toList());
    }
}

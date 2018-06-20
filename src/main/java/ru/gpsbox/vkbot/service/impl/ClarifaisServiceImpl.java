package ru.gpsbox.vkbot.service.impl;

import ru.gpsbox.vkbot.service.ClarifaisService;
import ru.gpsbox.vkbot.domain.Clarifais;
import ru.gpsbox.vkbot.repository.ClarifaisRepository;
import ru.gpsbox.vkbot.repository.search.ClarifaisSearchRepository;
import ru.gpsbox.vkbot.service.dto.ClarifaisDTO;
import ru.gpsbox.vkbot.service.mapper.ClarifaisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Clarifais.
 */
@Service
public class ClarifaisServiceImpl implements ClarifaisService {

    private final Logger log = LoggerFactory.getLogger(ClarifaisServiceImpl.class);

    private final ClarifaisRepository clarifaisRepository;

    private final ClarifaisMapper clarifaisMapper;

    private final ClarifaisSearchRepository clarifaisSearchRepository;

    public ClarifaisServiceImpl(ClarifaisRepository clarifaisRepository, ClarifaisMapper clarifaisMapper, ClarifaisSearchRepository clarifaisSearchRepository) {
        this.clarifaisRepository = clarifaisRepository;
        this.clarifaisMapper = clarifaisMapper;
        this.clarifaisSearchRepository = clarifaisSearchRepository;
    }

    /**
     * Save a clarifais.
     *
     * @param clarifaisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClarifaisDTO save(ClarifaisDTO clarifaisDTO) {
        log.debug("Request to save Clarifais : {}", clarifaisDTO);
        Clarifais clarifais = clarifaisMapper.toEntity(clarifaisDTO);
        clarifais = clarifaisRepository.save(clarifais);
        ClarifaisDTO result = clarifaisMapper.toDto(clarifais);
        clarifaisSearchRepository.save(clarifais);
        return result;
    }

    /**
     * Get all the clarifais.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ClarifaisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clarifais");
        return clarifaisRepository.findAll(pageable)
            .map(clarifaisMapper::toDto);
    }

    /**
     * Get one clarifais by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public ClarifaisDTO findOne(String id) {
        log.debug("Request to get Clarifais : {}", id);
        Clarifais clarifais = clarifaisRepository.findOne(id);
        return clarifaisMapper.toDto(clarifais);
    }

    /**
     * Delete the clarifais by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Clarifais : {}", id);
        clarifaisRepository.delete(id);
        clarifaisSearchRepository.delete(id);
    }

    /**
     * Search for the clarifais corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ClarifaisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clarifais for query {}", query);
        Page<Clarifais> result = clarifaisSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(clarifaisMapper::toDto);
    }
}

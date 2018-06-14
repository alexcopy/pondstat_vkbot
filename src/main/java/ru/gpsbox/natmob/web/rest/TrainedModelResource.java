package ru.gpsbox.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.natmob.service.TrainedModelService;
import ru.gpsbox.natmob.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.natmob.web.rest.util.HeaderUtil;
import ru.gpsbox.natmob.web.rest.util.PaginationUtil;
import ru.gpsbox.natmob.service.dto.TrainedModelDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TrainedModel.
 */
@RestController
@RequestMapping("/api")
public class TrainedModelResource {

    private final Logger log = LoggerFactory.getLogger(TrainedModelResource.class);

    private static final String ENTITY_NAME = "trainedModel";

    private final TrainedModelService trainedModelService;

    public TrainedModelResource(TrainedModelService trainedModelService) {
        this.trainedModelService = trainedModelService;
    }

    /**
     * POST  /trained-models : Create a new trainedModel.
     *
     * @param trainedModelDTO the trainedModelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trainedModelDTO, or with status 400 (Bad Request) if the trainedModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trained-models")
    @Timed
    public ResponseEntity<TrainedModelDTO> createTrainedModel(@Valid @RequestBody TrainedModelDTO trainedModelDTO) throws URISyntaxException {
        log.debug("REST request to save TrainedModel : {}", trainedModelDTO);
        if (trainedModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainedModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainedModelDTO result = trainedModelService.save(trainedModelDTO);
        return ResponseEntity.created(new URI("/api/trained-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trained-models : Updates an existing trainedModel.
     *
     * @param trainedModelDTO the trainedModelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trainedModelDTO,
     * or with status 400 (Bad Request) if the trainedModelDTO is not valid,
     * or with status 500 (Internal Server Error) if the trainedModelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trained-models")
    @Timed
    public ResponseEntity<TrainedModelDTO> updateTrainedModel(@Valid @RequestBody TrainedModelDTO trainedModelDTO) throws URISyntaxException {
        log.debug("REST request to update TrainedModel : {}", trainedModelDTO);
        if (trainedModelDTO.getId() == null) {
            return createTrainedModel(trainedModelDTO);
        }
        TrainedModelDTO result = trainedModelService.save(trainedModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trainedModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trained-models : get all the trainedModels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trainedModels in body
     */
    @GetMapping("/trained-models")
    @Timed
    public ResponseEntity<List<TrainedModelDTO>> getAllTrainedModels(Pageable pageable) {
        log.debug("REST request to get a page of TrainedModels");
        Page<TrainedModelDTO> page = trainedModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trained-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trained-models/:id : get the "id" trainedModel.
     *
     * @param id the id of the trainedModelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trainedModelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trained-models/{id}")
    @Timed
    public ResponseEntity<TrainedModelDTO> getTrainedModel(@PathVariable String id) {
        log.debug("REST request to get TrainedModel : {}", id);
        TrainedModelDTO trainedModelDTO = trainedModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trainedModelDTO));
    }

    /**
     * DELETE  /trained-models/:id : delete the "id" trainedModel.
     *
     * @param id the id of the trainedModelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trained-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrainedModel(@PathVariable String id) {
        log.debug("REST request to delete TrainedModel : {}", id);
        trainedModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/trained-models?query=:query : search for the trainedModel corresponding
     * to the query.
     *
     * @param query the query of the trainedModel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/trained-models")
    @Timed
    public ResponseEntity<List<TrainedModelDTO>> searchTrainedModels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TrainedModels for query {}", query);
        Page<TrainedModelDTO> page = trainedModelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/trained-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}

package ru.gpsbox.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.natmob.service.ResultsMatricesService;
import ru.gpsbox.natmob.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.natmob.web.rest.util.HeaderUtil;
import ru.gpsbox.natmob.service.dto.ResultsMatricesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ResultsMatrices.
 */
@RestController
@RequestMapping("/api")
public class ResultsMatricesResource {

    private final Logger log = LoggerFactory.getLogger(ResultsMatricesResource.class);

    private static final String ENTITY_NAME = "resultsMatrices";

    private final ResultsMatricesService resultsMatricesService;

    public ResultsMatricesResource(ResultsMatricesService resultsMatricesService) {
        this.resultsMatricesService = resultsMatricesService;
    }

    /**
     * POST  /results-matrices : Create a new resultsMatrices.
     *
     * @param resultsMatricesDTO the resultsMatricesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultsMatricesDTO, or with status 400 (Bad Request) if the resultsMatrices has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/results-matrices")
    @Timed
    public ResponseEntity<ResultsMatricesDTO> createResultsMatrices(@RequestBody ResultsMatricesDTO resultsMatricesDTO) throws URISyntaxException {
        log.debug("REST request to save ResultsMatrices : {}", resultsMatricesDTO);
        if (resultsMatricesDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultsMatrices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultsMatricesDTO result = resultsMatricesService.save(resultsMatricesDTO);
        return ResponseEntity.created(new URI("/api/results-matrices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /results-matrices : Updates an existing resultsMatrices.
     *
     * @param resultsMatricesDTO the resultsMatricesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultsMatricesDTO,
     * or with status 400 (Bad Request) if the resultsMatricesDTO is not valid,
     * or with status 500 (Internal Server Error) if the resultsMatricesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/results-matrices")
    @Timed
    public ResponseEntity<ResultsMatricesDTO> updateResultsMatrices(@RequestBody ResultsMatricesDTO resultsMatricesDTO) throws URISyntaxException {
        log.debug("REST request to update ResultsMatrices : {}", resultsMatricesDTO);
        if (resultsMatricesDTO.getId() == null) {
            return createResultsMatrices(resultsMatricesDTO);
        }
        ResultsMatricesDTO result = resultsMatricesService.save(resultsMatricesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultsMatricesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /results-matrices : get all the resultsMatrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resultsMatrices in body
     */
    @GetMapping("/results-matrices")
    @Timed
    public List<ResultsMatricesDTO> getAllResultsMatrices() {
        log.debug("REST request to get all ResultsMatrices");
        return resultsMatricesService.findAll();
        }

    /**
     * GET  /results-matrices/:id : get the "id" resultsMatrices.
     *
     * @param id the id of the resultsMatricesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultsMatricesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/results-matrices/{id}")
    @Timed
    public ResponseEntity<ResultsMatricesDTO> getResultsMatrices(@PathVariable String id) {
        log.debug("REST request to get ResultsMatrices : {}", id);
        ResultsMatricesDTO resultsMatricesDTO = resultsMatricesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resultsMatricesDTO));
    }

    /**
     * DELETE  /results-matrices/:id : delete the "id" resultsMatrices.
     *
     * @param id the id of the resultsMatricesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/results-matrices/{id}")
    @Timed
    public ResponseEntity<Void> deleteResultsMatrices(@PathVariable String id) {
        log.debug("REST request to delete ResultsMatrices : {}", id);
        resultsMatricesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/results-matrices?query=:query : search for the resultsMatrices corresponding
     * to the query.
     *
     * @param query the query of the resultsMatrices search
     * @return the result of the search
     */
    @GetMapping("/_search/results-matrices")
    @Timed
    public List<ResultsMatricesDTO> searchResultsMatrices(@RequestParam String query) {
        log.debug("REST request to search ResultsMatrices for query {}", query);
        return resultsMatricesService.search(query);
    }

}

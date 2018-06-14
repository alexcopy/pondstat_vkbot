package ru.gpsbox.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.natmob.service.ClarifaiProcessService;
import ru.gpsbox.natmob.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.natmob.web.rest.util.HeaderUtil;
import ru.gpsbox.natmob.service.dto.ClarifaiProcessDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing ClarifaiProcess.
 */
@RestController
@RequestMapping("/api")
public class ClarifaiProcessResource {

    private final Logger log = LoggerFactory.getLogger(ClarifaiProcessResource.class);

    private static final String ENTITY_NAME = "clarifaiProcess";

    private final ClarifaiProcessService clarifaiProcessService;

    public ClarifaiProcessResource(ClarifaiProcessService clarifaiProcessService) {
        this.clarifaiProcessService = clarifaiProcessService;
    }

    /**
     * POST  /clarifai-processes : Create a new clarifaiProcess.
     *
     * @param clarifaiProcessDTO the clarifaiProcessDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clarifaiProcessDTO, or with status 400 (Bad Request) if the clarifaiProcess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clarifai-processes")
    @Timed
    public ResponseEntity<ClarifaiProcessDTO> createClarifaiProcess(@Valid @RequestBody ClarifaiProcessDTO clarifaiProcessDTO) throws URISyntaxException {
        log.debug("REST request to save ClarifaiProcess : {}", clarifaiProcessDTO);
        if (clarifaiProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new clarifaiProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClarifaiProcessDTO result = clarifaiProcessService.save(clarifaiProcessDTO);
        return ResponseEntity.created(new URI("/api/clarifai-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clarifai-processes : Updates an existing clarifaiProcess.
     *
     * @param clarifaiProcessDTO the clarifaiProcessDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clarifaiProcessDTO,
     * or with status 400 (Bad Request) if the clarifaiProcessDTO is not valid,
     * or with status 500 (Internal Server Error) if the clarifaiProcessDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clarifai-processes")
    @Timed
    public ResponseEntity<ClarifaiProcessDTO> updateClarifaiProcess(@Valid @RequestBody ClarifaiProcessDTO clarifaiProcessDTO) throws URISyntaxException {
        log.debug("REST request to update ClarifaiProcess : {}", clarifaiProcessDTO);
        if (clarifaiProcessDTO.getId() == null) {
            return createClarifaiProcess(clarifaiProcessDTO);
        }
        ClarifaiProcessDTO result = clarifaiProcessService.save(clarifaiProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clarifaiProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clarifai-processes : get all the clarifaiProcesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clarifaiProcesses in body
     */
    @GetMapping("/clarifai-processes")
    @Timed
    public List<ClarifaiProcessDTO> getAllClarifaiProcesses() {
        log.debug("REST request to get all ClarifaiProcesses");
        return clarifaiProcessService.findAll();
        }

    /**
     * GET  /clarifai-processes/:id : get the "id" clarifaiProcess.
     *
     * @param id the id of the clarifaiProcessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clarifaiProcessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clarifai-processes/{id}")
    @Timed
    public ResponseEntity<ClarifaiProcessDTO> getClarifaiProcess(@PathVariable String id) {
        log.debug("REST request to get ClarifaiProcess : {}", id);
        ClarifaiProcessDTO clarifaiProcessDTO = clarifaiProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clarifaiProcessDTO));
    }

    /**
     * DELETE  /clarifai-processes/:id : delete the "id" clarifaiProcess.
     *
     * @param id the id of the clarifaiProcessDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clarifai-processes/{id}")
    @Timed
    public ResponseEntity<Void> deleteClarifaiProcess(@PathVariable String id) {
        log.debug("REST request to delete ClarifaiProcess : {}", id);
        clarifaiProcessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/clarifai-processes?query=:query : search for the clarifaiProcess corresponding
     * to the query.
     *
     * @param query the query of the clarifaiProcess search
     * @return the result of the search
     */
    @GetMapping("/_search/clarifai-processes")
    @Timed
    public List<ClarifaiProcessDTO> searchClarifaiProcesses(@RequestParam String query) {
        log.debug("REST request to search ClarifaiProcesses for query {}", query);
        return clarifaiProcessService.search(query);
    }

}

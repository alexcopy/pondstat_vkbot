package ru.gpsbox.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.natmob.service.ModelTrainingService;
import ru.gpsbox.natmob.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.natmob.web.rest.util.HeaderUtil;
import ru.gpsbox.natmob.service.dto.ModelTrainingDTO;
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
 * REST controller for managing ModelTraining.
 */
@RestController
@RequestMapping("/api")
public class ModelTrainingResource {

    private final Logger log = LoggerFactory.getLogger(ModelTrainingResource.class);

    private static final String ENTITY_NAME = "modelTraining";

    private final ModelTrainingService modelTrainingService;

    public ModelTrainingResource(ModelTrainingService modelTrainingService) {
        this.modelTrainingService = modelTrainingService;
    }

    /**
     * POST  /model-trainings : Create a new modelTraining.
     *
     * @param modelTrainingDTO the modelTrainingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modelTrainingDTO, or with status 400 (Bad Request) if the modelTraining has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/model-trainings")
    @Timed
    public ResponseEntity<ModelTrainingDTO> createModelTraining(@RequestBody ModelTrainingDTO modelTrainingDTO) throws URISyntaxException {
        log.debug("REST request to save ModelTraining : {}", modelTrainingDTO);
        if (modelTrainingDTO.getId() != null) {
            throw new BadRequestAlertException("A new modelTraining cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModelTrainingDTO result = modelTrainingService.save(modelTrainingDTO);
        return ResponseEntity.created(new URI("/api/model-trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /model-trainings : Updates an existing modelTraining.
     *
     * @param modelTrainingDTO the modelTrainingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modelTrainingDTO,
     * or with status 400 (Bad Request) if the modelTrainingDTO is not valid,
     * or with status 500 (Internal Server Error) if the modelTrainingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/model-trainings")
    @Timed
    public ResponseEntity<ModelTrainingDTO> updateModelTraining(@RequestBody ModelTrainingDTO modelTrainingDTO) throws URISyntaxException {
        log.debug("REST request to update ModelTraining : {}", modelTrainingDTO);
        if (modelTrainingDTO.getId() == null) {
            return createModelTraining(modelTrainingDTO);
        }
        ModelTrainingDTO result = modelTrainingService.save(modelTrainingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modelTrainingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /model-trainings : get all the modelTrainings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of modelTrainings in body
     */
    @GetMapping("/model-trainings")
    @Timed
    public List<ModelTrainingDTO> getAllModelTrainings() {
        log.debug("REST request to get all ModelTrainings");
        return modelTrainingService.findAll();
        }

    /**
     * GET  /model-trainings/:id : get the "id" modelTraining.
     *
     * @param id the id of the modelTrainingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modelTrainingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/model-trainings/{id}")
    @Timed
    public ResponseEntity<ModelTrainingDTO> getModelTraining(@PathVariable String id) {
        log.debug("REST request to get ModelTraining : {}", id);
        ModelTrainingDTO modelTrainingDTO = modelTrainingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modelTrainingDTO));
    }

    /**
     * DELETE  /model-trainings/:id : delete the "id" modelTraining.
     *
     * @param id the id of the modelTrainingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/model-trainings/{id}")
    @Timed
    public ResponseEntity<Void> deleteModelTraining(@PathVariable String id) {
        log.debug("REST request to delete ModelTraining : {}", id);
        modelTrainingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/model-trainings?query=:query : search for the modelTraining corresponding
     * to the query.
     *
     * @param query the query of the modelTraining search
     * @return the result of the search
     */
    @GetMapping("/_search/model-trainings")
    @Timed
    public List<ModelTrainingDTO> searchModelTrainings(@RequestParam String query) {
        log.debug("REST request to search ModelTrainings for query {}", query);
        return modelTrainingService.search(query);
    }

}

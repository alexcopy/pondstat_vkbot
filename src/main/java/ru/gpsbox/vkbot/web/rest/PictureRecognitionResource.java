package ru.gpsbox.vkbot.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.vkbot.service.PictureRecognitionService;
import ru.gpsbox.vkbot.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.vkbot.web.rest.util.HeaderUtil;
import ru.gpsbox.vkbot.service.dto.PictureRecognitionDTO;
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
 * REST controller for managing PictureRecognition.
 */
@RestController
@RequestMapping("/api")
public class PictureRecognitionResource {

    private final Logger log = LoggerFactory.getLogger(PictureRecognitionResource.class);

    private static final String ENTITY_NAME = "pictureRecognition";

    private final PictureRecognitionService pictureRecognitionService;

    public PictureRecognitionResource(PictureRecognitionService pictureRecognitionService) {
        this.pictureRecognitionService = pictureRecognitionService;
    }

    /**
     * POST  /picture-recognitions : Create a new pictureRecognition.
     *
     * @param pictureRecognitionDTO the pictureRecognitionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pictureRecognitionDTO, or with status 400 (Bad Request) if the pictureRecognition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/picture-recognitions")
    @Timed
    public ResponseEntity<PictureRecognitionDTO> createPictureRecognition(@RequestBody PictureRecognitionDTO pictureRecognitionDTO) throws URISyntaxException {
        log.debug("REST request to save PictureRecognition : {}", pictureRecognitionDTO);
        if (pictureRecognitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new pictureRecognition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PictureRecognitionDTO result = pictureRecognitionService.save(pictureRecognitionDTO);
        return ResponseEntity.created(new URI("/api/picture-recognitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /picture-recognitions : Updates an existing pictureRecognition.
     *
     * @param pictureRecognitionDTO the pictureRecognitionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pictureRecognitionDTO,
     * or with status 400 (Bad Request) if the pictureRecognitionDTO is not valid,
     * or with status 500 (Internal Server Error) if the pictureRecognitionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/picture-recognitions")
    @Timed
    public ResponseEntity<PictureRecognitionDTO> updatePictureRecognition(@RequestBody PictureRecognitionDTO pictureRecognitionDTO) throws URISyntaxException {
        log.debug("REST request to update PictureRecognition : {}", pictureRecognitionDTO);
        if (pictureRecognitionDTO.getId() == null) {
            return createPictureRecognition(pictureRecognitionDTO);
        }
        PictureRecognitionDTO result = pictureRecognitionService.save(pictureRecognitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pictureRecognitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /picture-recognitions : get all the pictureRecognitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pictureRecognitions in body
     */
    @GetMapping("/picture-recognitions")
    @Timed
    public List<PictureRecognitionDTO> getAllPictureRecognitions() {
        log.debug("REST request to get all PictureRecognitions");
        return pictureRecognitionService.findAll();
        }

    /**
     * GET  /picture-recognitions/:id : get the "id" pictureRecognition.
     *
     * @param id the id of the pictureRecognitionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pictureRecognitionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/picture-recognitions/{id}")
    @Timed
    public ResponseEntity<PictureRecognitionDTO> getPictureRecognition(@PathVariable String id) {
        log.debug("REST request to get PictureRecognition : {}", id);
        PictureRecognitionDTO pictureRecognitionDTO = pictureRecognitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pictureRecognitionDTO));
    }

    /**
     * DELETE  /picture-recognitions/:id : delete the "id" pictureRecognition.
     *
     * @param id the id of the pictureRecognitionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/picture-recognitions/{id}")
    @Timed
    public ResponseEntity<Void> deletePictureRecognition(@PathVariable String id) {
        log.debug("REST request to delete PictureRecognition : {}", id);
        pictureRecognitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/picture-recognitions?query=:query : search for the pictureRecognition corresponding
     * to the query.
     *
     * @param query the query of the pictureRecognition search
     * @return the result of the search
     */
    @GetMapping("/_search/picture-recognitions")
    @Timed
    public List<PictureRecognitionDTO> searchPictureRecognitions(@RequestParam String query) {
        log.debug("REST request to search PictureRecognitions for query {}", query);
        return pictureRecognitionService.search(query);
    }

}

package ru.gpsbox.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.natmob.service.SuggestIgnoredService;
import ru.gpsbox.natmob.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.natmob.web.rest.util.HeaderUtil;
import ru.gpsbox.natmob.service.dto.SuggestIgnoredDTO;
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
 * REST controller for managing SuggestIgnored.
 */
@RestController
@RequestMapping("/api")
public class SuggestIgnoredResource {

    private final Logger log = LoggerFactory.getLogger(SuggestIgnoredResource.class);

    private static final String ENTITY_NAME = "suggestIgnored";

    private final SuggestIgnoredService suggestIgnoredService;

    public SuggestIgnoredResource(SuggestIgnoredService suggestIgnoredService) {
        this.suggestIgnoredService = suggestIgnoredService;
    }

    /**
     * POST  /suggest-ignoreds : Create a new suggestIgnored.
     *
     * @param suggestIgnoredDTO the suggestIgnoredDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suggestIgnoredDTO, or with status 400 (Bad Request) if the suggestIgnored has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suggest-ignoreds")
    @Timed
    public ResponseEntity<SuggestIgnoredDTO> createSuggestIgnored(@RequestBody SuggestIgnoredDTO suggestIgnoredDTO) throws URISyntaxException {
        log.debug("REST request to save SuggestIgnored : {}", suggestIgnoredDTO);
        if (suggestIgnoredDTO.getId() != null) {
            throw new BadRequestAlertException("A new suggestIgnored cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuggestIgnoredDTO result = suggestIgnoredService.save(suggestIgnoredDTO);
        return ResponseEntity.created(new URI("/api/suggest-ignoreds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suggest-ignoreds : Updates an existing suggestIgnored.
     *
     * @param suggestIgnoredDTO the suggestIgnoredDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suggestIgnoredDTO,
     * or with status 400 (Bad Request) if the suggestIgnoredDTO is not valid,
     * or with status 500 (Internal Server Error) if the suggestIgnoredDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suggest-ignoreds")
    @Timed
    public ResponseEntity<SuggestIgnoredDTO> updateSuggestIgnored(@RequestBody SuggestIgnoredDTO suggestIgnoredDTO) throws URISyntaxException {
        log.debug("REST request to update SuggestIgnored : {}", suggestIgnoredDTO);
        if (suggestIgnoredDTO.getId() == null) {
            return createSuggestIgnored(suggestIgnoredDTO);
        }
        SuggestIgnoredDTO result = suggestIgnoredService.save(suggestIgnoredDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suggestIgnoredDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suggest-ignoreds : get all the suggestIgnoreds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of suggestIgnoreds in body
     */
    @GetMapping("/suggest-ignoreds")
    @Timed
    public List<SuggestIgnoredDTO> getAllSuggestIgnoreds() {
        log.debug("REST request to get all SuggestIgnoreds");
        return suggestIgnoredService.findAll();
        }

    /**
     * GET  /suggest-ignoreds/:id : get the "id" suggestIgnored.
     *
     * @param id the id of the suggestIgnoredDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suggestIgnoredDTO, or with status 404 (Not Found)
     */
    @GetMapping("/suggest-ignoreds/{id}")
    @Timed
    public ResponseEntity<SuggestIgnoredDTO> getSuggestIgnored(@PathVariable String id) {
        log.debug("REST request to get SuggestIgnored : {}", id);
        SuggestIgnoredDTO suggestIgnoredDTO = suggestIgnoredService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(suggestIgnoredDTO));
    }

    /**
     * DELETE  /suggest-ignoreds/:id : delete the "id" suggestIgnored.
     *
     * @param id the id of the suggestIgnoredDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suggest-ignoreds/{id}")
    @Timed
    public ResponseEntity<Void> deleteSuggestIgnored(@PathVariable String id) {
        log.debug("REST request to delete SuggestIgnored : {}", id);
        suggestIgnoredService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/suggest-ignoreds?query=:query : search for the suggestIgnored corresponding
     * to the query.
     *
     * @param query the query of the suggestIgnored search
     * @return the result of the search
     */
    @GetMapping("/_search/suggest-ignoreds")
    @Timed
    public List<SuggestIgnoredDTO> searchSuggestIgnoreds(@RequestParam String query) {
        log.debug("REST request to search SuggestIgnoreds for query {}", query);
        return suggestIgnoredService.search(query);
    }

}

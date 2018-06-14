package ru.gpsbox.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.natmob.service.ClarifaisService;
import ru.gpsbox.natmob.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.natmob.web.rest.util.HeaderUtil;
import ru.gpsbox.natmob.web.rest.util.PaginationUtil;
import ru.gpsbox.natmob.service.dto.ClarifaisDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Clarifais.
 */
@RestController
@RequestMapping("/api")
public class ClarifaisResource {

    private final Logger log = LoggerFactory.getLogger(ClarifaisResource.class);

    private static final String ENTITY_NAME = "clarifais";

    private final ClarifaisService clarifaisService;

    public ClarifaisResource(ClarifaisService clarifaisService) {
        this.clarifaisService = clarifaisService;
    }

    /**
     * POST  /clarifais : Create a new clarifais.
     *
     * @param clarifaisDTO the clarifaisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clarifaisDTO, or with status 400 (Bad Request) if the clarifais has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clarifais")
    @Timed
    public ResponseEntity<ClarifaisDTO> createClarifais(@RequestBody ClarifaisDTO clarifaisDTO) throws URISyntaxException {
        log.debug("REST request to save Clarifais : {}", clarifaisDTO);
        if (clarifaisDTO.getId() != null) {
            throw new BadRequestAlertException("A new clarifais cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClarifaisDTO result = clarifaisService.save(clarifaisDTO);
        return ResponseEntity.created(new URI("/api/clarifais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clarifais : Updates an existing clarifais.
     *
     * @param clarifaisDTO the clarifaisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clarifaisDTO,
     * or with status 400 (Bad Request) if the clarifaisDTO is not valid,
     * or with status 500 (Internal Server Error) if the clarifaisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clarifais")
    @Timed
    public ResponseEntity<ClarifaisDTO> updateClarifais(@RequestBody ClarifaisDTO clarifaisDTO) throws URISyntaxException {
        log.debug("REST request to update Clarifais : {}", clarifaisDTO);
        if (clarifaisDTO.getId() == null) {
            return createClarifais(clarifaisDTO);
        }
        ClarifaisDTO result = clarifaisService.save(clarifaisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clarifaisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clarifais : get all the clarifais.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clarifais in body
     */
    @GetMapping("/clarifais")
    @Timed
    public ResponseEntity<List<ClarifaisDTO>> getAllClarifais(Pageable pageable) {
        log.debug("REST request to get a page of Clarifais");
        Page<ClarifaisDTO> page = clarifaisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clarifais");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clarifais/:id : get the "id" clarifais.
     *
     * @param id the id of the clarifaisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clarifaisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clarifais/{id}")
    @Timed
    public ResponseEntity<ClarifaisDTO> getClarifais(@PathVariable String id) {
        log.debug("REST request to get Clarifais : {}", id);
        ClarifaisDTO clarifaisDTO = clarifaisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clarifaisDTO));
    }

    /**
     * DELETE  /clarifais/:id : delete the "id" clarifais.
     *
     * @param id the id of the clarifaisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clarifais/{id}")
    @Timed
    public ResponseEntity<Void> deleteClarifais(@PathVariable String id) {
        log.debug("REST request to delete Clarifais : {}", id);
        clarifaisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/clarifais?query=:query : search for the clarifais corresponding
     * to the query.
     *
     * @param query the query of the clarifais search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/clarifais")
    @Timed
    public ResponseEntity<List<ClarifaisDTO>> searchClarifais(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clarifais for query {}", query);
        Page<ClarifaisDTO> page = clarifaisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clarifais");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}

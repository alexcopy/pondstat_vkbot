package ru.gpsbox.vkbot.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.vkbot.service.SelectedKeyWordsService;
import ru.gpsbox.vkbot.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.vkbot.web.rest.util.HeaderUtil;
import ru.gpsbox.vkbot.service.dto.SelectedKeyWordsDTO;
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
 * REST controller for managing SelectedKeyWords.
 */
@RestController
@RequestMapping("/api")
public class SelectedKeyWordsResource {

    private final Logger log = LoggerFactory.getLogger(SelectedKeyWordsResource.class);

    private static final String ENTITY_NAME = "selectedKeyWords";

    private final SelectedKeyWordsService selectedKeyWordsService;

    public SelectedKeyWordsResource(SelectedKeyWordsService selectedKeyWordsService) {
        this.selectedKeyWordsService = selectedKeyWordsService;
    }

    /**
     * POST  /selected-key-words : Create a new selectedKeyWords.
     *
     * @param selectedKeyWordsDTO the selectedKeyWordsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new selectedKeyWordsDTO, or with status 400 (Bad Request) if the selectedKeyWords has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/selected-key-words")
    @Timed
    public ResponseEntity<SelectedKeyWordsDTO> createSelectedKeyWords(@RequestBody SelectedKeyWordsDTO selectedKeyWordsDTO) throws URISyntaxException {
        log.debug("REST request to save SelectedKeyWords : {}", selectedKeyWordsDTO);
        if (selectedKeyWordsDTO.getId() != null) {
            throw new BadRequestAlertException("A new selectedKeyWords cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SelectedKeyWordsDTO result = selectedKeyWordsService.save(selectedKeyWordsDTO);
        return ResponseEntity.created(new URI("/api/selected-key-words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /selected-key-words : Updates an existing selectedKeyWords.
     *
     * @param selectedKeyWordsDTO the selectedKeyWordsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated selectedKeyWordsDTO,
     * or with status 400 (Bad Request) if the selectedKeyWordsDTO is not valid,
     * or with status 500 (Internal Server Error) if the selectedKeyWordsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/selected-key-words")
    @Timed
    public ResponseEntity<SelectedKeyWordsDTO> updateSelectedKeyWords(@RequestBody SelectedKeyWordsDTO selectedKeyWordsDTO) throws URISyntaxException {
        log.debug("REST request to update SelectedKeyWords : {}", selectedKeyWordsDTO);
        if (selectedKeyWordsDTO.getId() == null) {
            return createSelectedKeyWords(selectedKeyWordsDTO);
        }
        SelectedKeyWordsDTO result = selectedKeyWordsService.save(selectedKeyWordsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, selectedKeyWordsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /selected-key-words : get all the selectedKeyWords.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of selectedKeyWords in body
     */
    @GetMapping("/selected-key-words")
    @Timed
    public List<SelectedKeyWordsDTO> getAllSelectedKeyWords() {
        log.debug("REST request to get all SelectedKeyWords");
        return selectedKeyWordsService.findAll();
        }

    /**
     * GET  /selected-key-words/:id : get the "id" selectedKeyWords.
     *
     * @param id the id of the selectedKeyWordsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the selectedKeyWordsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/selected-key-words/{id}")
    @Timed
    public ResponseEntity<SelectedKeyWordsDTO> getSelectedKeyWords(@PathVariable String id) {
        log.debug("REST request to get SelectedKeyWords : {}", id);
        SelectedKeyWordsDTO selectedKeyWordsDTO = selectedKeyWordsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(selectedKeyWordsDTO));
    }

    /**
     * DELETE  /selected-key-words/:id : delete the "id" selectedKeyWords.
     *
     * @param id the id of the selectedKeyWordsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/selected-key-words/{id}")
    @Timed
    public ResponseEntity<Void> deleteSelectedKeyWords(@PathVariable String id) {
        log.debug("REST request to delete SelectedKeyWords : {}", id);
        selectedKeyWordsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/selected-key-words?query=:query : search for the selectedKeyWords corresponding
     * to the query.
     *
     * @param query the query of the selectedKeyWords search
     * @return the result of the search
     */
    @GetMapping("/_search/selected-key-words")
    @Timed
    public List<SelectedKeyWordsDTO> searchSelectedKeyWords(@RequestParam String query) {
        log.debug("REST request to search SelectedKeyWords for query {}", query);
        return selectedKeyWordsService.search(query);
    }

}

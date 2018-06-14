package ru.gpsbox.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.natmob.service.VkPictureService;
import ru.gpsbox.natmob.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.natmob.web.rest.util.HeaderUtil;
import ru.gpsbox.natmob.service.dto.VkPictureDTO;
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
 * REST controller for managing VkPicture.
 */
@RestController
@RequestMapping("/api")
public class VkPictureResource {

    private final Logger log = LoggerFactory.getLogger(VkPictureResource.class);

    private static final String ENTITY_NAME = "vkPicture";

    private final VkPictureService vkPictureService;

    public VkPictureResource(VkPictureService vkPictureService) {
        this.vkPictureService = vkPictureService;
    }

    /**
     * POST  /vk-pictures : Create a new vkPicture.
     *
     * @param vkPictureDTO the vkPictureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vkPictureDTO, or with status 400 (Bad Request) if the vkPicture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vk-pictures")
    @Timed
    public ResponseEntity<VkPictureDTO> createVkPicture(@Valid @RequestBody VkPictureDTO vkPictureDTO) throws URISyntaxException {
        log.debug("REST request to save VkPicture : {}", vkPictureDTO);
        if (vkPictureDTO.getId() != null) {
            throw new BadRequestAlertException("A new vkPicture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VkPictureDTO result = vkPictureService.save(vkPictureDTO);
        return ResponseEntity.created(new URI("/api/vk-pictures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vk-pictures : Updates an existing vkPicture.
     *
     * @param vkPictureDTO the vkPictureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vkPictureDTO,
     * or with status 400 (Bad Request) if the vkPictureDTO is not valid,
     * or with status 500 (Internal Server Error) if the vkPictureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vk-pictures")
    @Timed
    public ResponseEntity<VkPictureDTO> updateVkPicture(@Valid @RequestBody VkPictureDTO vkPictureDTO) throws URISyntaxException {
        log.debug("REST request to update VkPicture : {}", vkPictureDTO);
        if (vkPictureDTO.getId() == null) {
            return createVkPicture(vkPictureDTO);
        }
        VkPictureDTO result = vkPictureService.save(vkPictureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vkPictureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vk-pictures : get all the vkPictures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vkPictures in body
     */
    @GetMapping("/vk-pictures")
    @Timed
    public List<VkPictureDTO> getAllVkPictures() {
        log.debug("REST request to get all VkPictures");
        return vkPictureService.findAll();
        }

    /**
     * GET  /vk-pictures/:id : get the "id" vkPicture.
     *
     * @param id the id of the vkPictureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vkPictureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vk-pictures/{id}")
    @Timed
    public ResponseEntity<VkPictureDTO> getVkPicture(@PathVariable String id) {
        log.debug("REST request to get VkPicture : {}", id);
        VkPictureDTO vkPictureDTO = vkPictureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vkPictureDTO));
    }

    /**
     * DELETE  /vk-pictures/:id : delete the "id" vkPicture.
     *
     * @param id the id of the vkPictureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vk-pictures/{id}")
    @Timed
    public ResponseEntity<Void> deleteVkPicture(@PathVariable String id) {
        log.debug("REST request to delete VkPicture : {}", id);
        vkPictureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/vk-pictures?query=:query : search for the vkPicture corresponding
     * to the query.
     *
     * @param query the query of the vkPicture search
     * @return the result of the search
     */
    @GetMapping("/_search/vk-pictures")
    @Timed
    public List<VkPictureDTO> searchVkPictures(@RequestParam String query) {
        log.debug("REST request to search VkPictures for query {}", query);
        return vkPictureService.search(query);
    }

}

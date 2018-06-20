package ru.gpsbox.vkbot.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.vkbot.service.VkGroupService;
import ru.gpsbox.vkbot.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.vkbot.web.rest.util.HeaderUtil;
import ru.gpsbox.vkbot.service.dto.VkGroupDTO;
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
 * REST controller for managing VkGroup.
 */
@RestController
@RequestMapping("/api")
public class VkGroupResource {

    private final Logger log = LoggerFactory.getLogger(VkGroupResource.class);

    private static final String ENTITY_NAME = "vkGroup";

    private final VkGroupService vkGroupService;

    public VkGroupResource(VkGroupService vkGroupService) {
        this.vkGroupService = vkGroupService;
    }

    /**
     * POST  /vk-groups : Create a new vkGroup.
     *
     * @param vkGroupDTO the vkGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vkGroupDTO, or with status 400 (Bad Request) if the vkGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vk-groups")
    @Timed
    public ResponseEntity<VkGroupDTO> createVkGroup(@Valid @RequestBody VkGroupDTO vkGroupDTO) throws URISyntaxException {
        log.debug("REST request to save VkGroup : {}", vkGroupDTO);
        if (vkGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new vkGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VkGroupDTO result = vkGroupService.save(vkGroupDTO);
        return ResponseEntity.created(new URI("/api/vk-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vk-groups : Updates an existing vkGroup.
     *
     * @param vkGroupDTO the vkGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vkGroupDTO,
     * or with status 400 (Bad Request) if the vkGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the vkGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vk-groups")
    @Timed
    public ResponseEntity<VkGroupDTO> updateVkGroup(@Valid @RequestBody VkGroupDTO vkGroupDTO) throws URISyntaxException {
        log.debug("REST request to update VkGroup : {}", vkGroupDTO);
        if (vkGroupDTO.getId() == null) {
            return createVkGroup(vkGroupDTO);
        }
        VkGroupDTO result = vkGroupService.save(vkGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vkGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vk-groups : get all the vkGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vkGroups in body
     */
    @GetMapping("/vk-groups")
    @Timed
    public List<VkGroupDTO> getAllVkGroups() {
        log.debug("REST request to get all VkGroups");
        return vkGroupService.findAll();
        }

    /**
     * GET  /vk-groups/:id : get the "id" vkGroup.
     *
     * @param id the id of the vkGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vkGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vk-groups/{id}")
    @Timed
    public ResponseEntity<VkGroupDTO> getVkGroup(@PathVariable String id) {
        log.debug("REST request to get VkGroup : {}", id);
        VkGroupDTO vkGroupDTO = vkGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vkGroupDTO));
    }

    /**
     * DELETE  /vk-groups/:id : delete the "id" vkGroup.
     *
     * @param id the id of the vkGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vk-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteVkGroup(@PathVariable String id) {
        log.debug("REST request to delete VkGroup : {}", id);
        vkGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/vk-groups?query=:query : search for the vkGroup corresponding
     * to the query.
     *
     * @param query the query of the vkGroup search
     * @return the result of the search
     */
    @GetMapping("/_search/vk-groups")
    @Timed
    public List<VkGroupDTO> searchVkGroups(@RequestParam String query) {
        log.debug("REST request to search VkGroups for query {}", query);
        return vkGroupService.search(query);
    }

}

package ru.gpsbox.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.gpsbox.natmob.service.VkUserService;
import ru.gpsbox.natmob.web.rest.errors.BadRequestAlertException;
import ru.gpsbox.natmob.web.rest.util.HeaderUtil;
import ru.gpsbox.natmob.service.dto.VkUserDTO;
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
 * REST controller for managing VkUser.
 */
@RestController
@RequestMapping("/api")
public class VkUserResource {

    private final Logger log = LoggerFactory.getLogger(VkUserResource.class);

    private static final String ENTITY_NAME = "vkUser";

    private final VkUserService vkUserService;

    public VkUserResource(VkUserService vkUserService) {
        this.vkUserService = vkUserService;
    }

    /**
     * POST  /vk-users : Create a new vkUser.
     *
     * @param vkUserDTO the vkUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vkUserDTO, or with status 400 (Bad Request) if the vkUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vk-users")
    @Timed
    public ResponseEntity<VkUserDTO> createVkUser(@Valid @RequestBody VkUserDTO vkUserDTO) throws URISyntaxException {
        log.debug("REST request to save VkUser : {}", vkUserDTO);
        if (vkUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new vkUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VkUserDTO result = vkUserService.save(vkUserDTO);
        return ResponseEntity.created(new URI("/api/vk-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vk-users : Updates an existing vkUser.
     *
     * @param vkUserDTO the vkUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vkUserDTO,
     * or with status 400 (Bad Request) if the vkUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the vkUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vk-users")
    @Timed
    public ResponseEntity<VkUserDTO> updateVkUser(@Valid @RequestBody VkUserDTO vkUserDTO) throws URISyntaxException {
        log.debug("REST request to update VkUser : {}", vkUserDTO);
        if (vkUserDTO.getId() == null) {
            return createVkUser(vkUserDTO);
        }
        VkUserDTO result = vkUserService.save(vkUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vkUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vk-users : get all the vkUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vkUsers in body
     */
    @GetMapping("/vk-users")
    @Timed
    public List<VkUserDTO> getAllVkUsers() {
        log.debug("REST request to get all VkUsers");
        return vkUserService.findAll();
        }

    /**
     * GET  /vk-users/:id : get the "id" vkUser.
     *
     * @param id the id of the vkUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vkUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vk-users/{id}")
    @Timed
    public ResponseEntity<VkUserDTO> getVkUser(@PathVariable String id) {
        log.debug("REST request to get VkUser : {}", id);
        VkUserDTO vkUserDTO = vkUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vkUserDTO));
    }

    /**
     * DELETE  /vk-users/:id : delete the "id" vkUser.
     *
     * @param id the id of the vkUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vk-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteVkUser(@PathVariable String id) {
        log.debug("REST request to delete VkUser : {}", id);
        vkUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/vk-users?query=:query : search for the vkUser corresponding
     * to the query.
     *
     * @param query the query of the vkUser search
     * @return the result of the search
     */
    @GetMapping("/_search/vk-users")
    @Timed
    public List<VkUserDTO> searchVkUsers(@RequestParam String query) {
        log.debug("REST request to search VkUsers for query {}", query);
        return vkUserService.search(query);
    }

}

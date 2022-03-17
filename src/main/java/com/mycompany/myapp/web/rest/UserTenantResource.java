package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.UserTenantRepository;
import com.mycompany.myapp.service.UserTenantService;
import com.mycompany.myapp.service.dto.UserTenantDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.UserTenant}.
 */
@RestController
@RequestMapping("/api")
public class UserTenantResource {

    private final Logger log = LoggerFactory.getLogger(UserTenantResource.class);

    private static final String ENTITY_NAME = "userTenant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserTenantService userTenantService;

    private final UserTenantRepository userTenantRepository;

    public UserTenantResource(UserTenantService userTenantService, UserTenantRepository userTenantRepository) {
        this.userTenantService = userTenantService;
        this.userTenantRepository = userTenantRepository;
    }

    /**
     * {@code POST  /user-tenants} : Create a new userTenant.
     *
     * @param userTenantDTO the userTenantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userTenantDTO, or with status {@code 400 (Bad Request)} if the userTenant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-tenants")
    public ResponseEntity<UserTenantDTO> createUserTenant(@RequestBody UserTenantDTO userTenantDTO) throws URISyntaxException {
        log.debug("REST request to save UserTenant : {}", userTenantDTO);
        if (userTenantDTO.getId() != null) {
            throw new BadRequestAlertException("A new userTenant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTenantDTO result = userTenantService.save(userTenantDTO);
        return ResponseEntity
            .created(new URI("/api/user-tenants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-tenants/:id} : Updates an existing userTenant.
     *
     * @param id the id of the userTenantDTO to save.
     * @param userTenantDTO the userTenantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTenantDTO,
     * or with status {@code 400 (Bad Request)} if the userTenantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userTenantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-tenants/{id}")
    public ResponseEntity<UserTenantDTO> updateUserTenant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTenantDTO userTenantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserTenant : {}, {}", id, userTenantDTO);
        if (userTenantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTenantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTenantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserTenantDTO result = userTenantService.save(userTenantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userTenantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-tenants/:id} : Partial updates given fields of an existing userTenant, field will ignore if it is null
     *
     * @param id the id of the userTenantDTO to save.
     * @param userTenantDTO the userTenantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTenantDTO,
     * or with status {@code 400 (Bad Request)} if the userTenantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userTenantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userTenantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-tenants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserTenantDTO> partialUpdateUserTenant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTenantDTO userTenantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserTenant partially : {}, {}", id, userTenantDTO);
        if (userTenantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTenantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTenantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserTenantDTO> result = userTenantService.partialUpdate(userTenantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userTenantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-tenants} : get all the userTenants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userTenants in body.
     */
    @GetMapping("/user-tenants")
    public List<UserTenantDTO> getAllUserTenants() {
        log.debug("REST request to get all UserTenants");
        return userTenantService.findAll();
    }

    /**
     * {@code GET  /user-tenants/:id} : get the "id" userTenant.
     *
     * @param id the id of the userTenantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userTenantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-tenants/{id}")
    public ResponseEntity<UserTenantDTO> getUserTenant(@PathVariable Long id) {
        log.debug("REST request to get UserTenant : {}", id);
        Optional<UserTenantDTO> userTenantDTO = userTenantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userTenantDTO);
    }

    /**
     * {@code DELETE  /user-tenants/:id} : delete the "id" userTenant.
     *
     * @param id the id of the userTenantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-tenants/{id}")
    public ResponseEntity<Void> deleteUserTenant(@PathVariable Long id) {
        log.debug("REST request to delete UserTenant : {}", id);
        userTenantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.asart.asart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asart.asart.service.CollaboratorService;
import com.asart.asart.web.rest.errors.BadRequestAlertException;
import com.asart.asart.web.rest.util.HeaderUtil;
import com.asart.asart.service.dto.CollaboratorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Collaborator.
 */
@RestController
@RequestMapping("/api")
public class CollaboratorResource {

    private final Logger log = LoggerFactory.getLogger(CollaboratorResource.class);

    private static final String ENTITY_NAME = "collaborator";

    private final CollaboratorService collaboratorService;

    public CollaboratorResource(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    /**
     * POST  /collaborators : Create a new collaborator.
     *
     * @param collaboratorDTO the collaboratorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collaboratorDTO, or with status 400 (Bad Request) if the collaborator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collaborators")
    @Timed
    public ResponseEntity<CollaboratorDTO> createCollaborator(@RequestBody CollaboratorDTO collaboratorDTO) throws URISyntaxException {
        log.debug("REST request to save Collaborator : {}", collaboratorDTO);
        if (collaboratorDTO.getId() != null) {
            throw new BadRequestAlertException("A new collaborator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollaboratorDTO result = collaboratorService.save(collaboratorDTO);
        return ResponseEntity.created(new URI("/api/collaborators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collaborators : Updates an existing collaborator.
     *
     * @param collaboratorDTO the collaboratorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collaboratorDTO,
     * or with status 400 (Bad Request) if the collaboratorDTO is not valid,
     * or with status 500 (Internal Server Error) if the collaboratorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collaborators")
    @Timed
    public ResponseEntity<CollaboratorDTO> updateCollaborator(@RequestBody CollaboratorDTO collaboratorDTO) throws URISyntaxException {
        log.debug("REST request to update Collaborator : {}", collaboratorDTO);
        if (collaboratorDTO.getId() == null) {
            return createCollaborator(collaboratorDTO);
        }
        CollaboratorDTO result = collaboratorService.save(collaboratorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collaboratorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collaborators : get all the collaborators.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collaborators in body
     */
    @GetMapping("/collaborators")
    @Timed
    public List<CollaboratorDTO> getAllCollaborators() {
        log.debug("REST request to get all Collaborators");
        return collaboratorService.findAll();
        }

    /**
     * GET  /collaborators/:id : get the "id" collaborator.
     *
     * @param id the id of the collaboratorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collaboratorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collaborators/{id}")
    @Timed
    public ResponseEntity<CollaboratorDTO> getCollaborator(@PathVariable Long id) {
        log.debug("REST request to get Collaborator : {}", id);
        CollaboratorDTO collaboratorDTO = collaboratorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collaboratorDTO));
    }

    /**
     * DELETE  /collaborators/:id : delete the "id" collaborator.
     *
     * @param id the id of the collaboratorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collaborators/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollaborator(@PathVariable Long id) {
        log.debug("REST request to delete Collaborator : {}", id);
        collaboratorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

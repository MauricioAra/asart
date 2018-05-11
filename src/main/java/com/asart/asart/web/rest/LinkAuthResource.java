package com.asart.asart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asart.asart.service.LinkAuthService;
import com.asart.asart.web.rest.errors.BadRequestAlertException;
import com.asart.asart.web.rest.util.HeaderUtil;
import com.asart.asart.service.dto.LinkAuthDTO;
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
 * REST controller for managing LinkAuth.
 */
@RestController
@RequestMapping("/api")
public class LinkAuthResource {

    private final Logger log = LoggerFactory.getLogger(LinkAuthResource.class);

    private static final String ENTITY_NAME = "linkAuth";

    private final LinkAuthService linkAuthService;

    public LinkAuthResource(LinkAuthService linkAuthService) {
        this.linkAuthService = linkAuthService;
    }

    /**
     * POST  /link-auths : Create a new linkAuth.
     *
     * @param linkAuthDTO the linkAuthDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new linkAuthDTO, or with status 400 (Bad Request) if the linkAuth has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/link-auths")
    @Timed
    public ResponseEntity<LinkAuthDTO> createLinkAuth(@RequestBody LinkAuthDTO linkAuthDTO) throws URISyntaxException {
        log.debug("REST request to save LinkAuth : {}", linkAuthDTO);
        if (linkAuthDTO.getId() != null) {
            throw new BadRequestAlertException("A new linkAuth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinkAuthDTO result = linkAuthService.save(linkAuthDTO);
        return ResponseEntity.created(new URI("/api/link-auths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /link-auths : Updates an existing linkAuth.
     *
     * @param linkAuthDTO the linkAuthDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated linkAuthDTO,
     * or with status 400 (Bad Request) if the linkAuthDTO is not valid,
     * or with status 500 (Internal Server Error) if the linkAuthDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/link-auths")
    @Timed
    public ResponseEntity<LinkAuthDTO> updateLinkAuth(@RequestBody LinkAuthDTO linkAuthDTO) throws URISyntaxException {
        log.debug("REST request to update LinkAuth : {}", linkAuthDTO);
        if (linkAuthDTO.getId() == null) {
            return createLinkAuth(linkAuthDTO);
        }
        LinkAuthDTO result = linkAuthService.save(linkAuthDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, linkAuthDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /link-auths : get all the linkAuths.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of linkAuths in body
     */
    @GetMapping("/link-auths")
    @Timed
    public List<LinkAuthDTO> getAllLinkAuths() {
        log.debug("REST request to get all LinkAuths");
        return linkAuthService.findAll();
        }

    /**
     * GET  /link-auths/:id : get the "id" linkAuth.
     *
     * @param id the id of the linkAuthDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the linkAuthDTO, or with status 404 (Not Found)
     */
    @GetMapping("/link-auths/{id}")
    @Timed
    public ResponseEntity<LinkAuthDTO> getLinkAuth(@PathVariable Long id) {
        log.debug("REST request to get LinkAuth : {}", id);
        LinkAuthDTO linkAuthDTO = linkAuthService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(linkAuthDTO));
    }

    /**
     * DELETE  /link-auths/:id : delete the "id" linkAuth.
     *
     * @param id the id of the linkAuthDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/link-auths/{id}")
    @Timed
    public ResponseEntity<Void> deleteLinkAuth(@PathVariable Long id) {
        log.debug("REST request to delete LinkAuth : {}", id);
        linkAuthService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.asart.asart.service;

import com.asart.asart.service.dto.LinkAuthDTO;
import java.util.List;

/**
 * Service Interface for managing LinkAuth.
 */
public interface LinkAuthService {

    /**
     * Save a linkAuth.
     *
     * @param linkAuthDTO the entity to save
     * @return the persisted entity
     */
    LinkAuthDTO save(LinkAuthDTO linkAuthDTO);

    /**
     * Get all the linkAuths.
     *
     * @return the list of entities
     */
    List<LinkAuthDTO> findAll();

    /**
     * Get the "id" linkAuth.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LinkAuthDTO findOne(Long id);

    /**
     * Delete the "id" linkAuth.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

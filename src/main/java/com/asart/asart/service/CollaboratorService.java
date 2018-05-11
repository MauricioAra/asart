package com.asart.asart.service;

import com.asart.asart.service.dto.CollaboratorDTO;
import java.util.List;

/**
 * Service Interface for managing Collaborator.
 */
public interface CollaboratorService {

    /**
     * Save a collaborator.
     *
     * @param collaboratorDTO the entity to save
     * @return the persisted entity
     */
    CollaboratorDTO save(CollaboratorDTO collaboratorDTO);

    /**
     * Get all the collaborators.
     *
     * @return the list of entities
     */
    List<CollaboratorDTO> findAll();

    /**
     * Get the "id" collaborator.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CollaboratorDTO findOne(Long id);

    /**
     * Delete the "id" collaborator.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

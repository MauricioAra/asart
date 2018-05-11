package com.asart.asart.service;

import com.asart.asart.service.dto.LogWorkDTO;
import java.util.List;

/**
 * Service Interface for managing LogWork.
 */
public interface LogWorkService {

    /**
     * Save a logWork.
     *
     * @param logWorkDTO the entity to save
     * @return the persisted entity
     */
    LogWorkDTO save(LogWorkDTO logWorkDTO);

    /**
     * Get all the logWorks.
     *
     * @return the list of entities
     */
    List<LogWorkDTO> findAll();

    /**
     * Get the "id" logWork.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LogWorkDTO findOne(Long id);

    /**
     * Delete the "id" logWork.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

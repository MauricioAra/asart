package com.asart.asart.service.impl;

import com.asart.asart.service.LogWorkService;
import com.asart.asart.domain.LogWork;
import com.asart.asart.repository.LogWorkRepository;
import com.asart.asart.service.dto.LogWorkDTO;
import com.asart.asart.service.mapper.LogWorkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LogWork.
 */
@Service
@Transactional
public class LogWorkServiceImpl implements LogWorkService {

    private final Logger log = LoggerFactory.getLogger(LogWorkServiceImpl.class);

    private final LogWorkRepository logWorkRepository;

    private final LogWorkMapper logWorkMapper;

    public LogWorkServiceImpl(LogWorkRepository logWorkRepository, LogWorkMapper logWorkMapper) {
        this.logWorkRepository = logWorkRepository;
        this.logWorkMapper = logWorkMapper;
    }

    /**
     * Save a logWork.
     *
     * @param logWorkDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LogWorkDTO save(LogWorkDTO logWorkDTO) {
        log.debug("Request to save LogWork : {}", logWorkDTO);
        LogWork logWork = logWorkMapper.toEntity(logWorkDTO);
        logWork = logWorkRepository.save(logWork);
        return logWorkMapper.toDto(logWork);
    }

    /**
     * Get all the logWorks.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LogWorkDTO> findAll() {
        log.debug("Request to get all LogWorks");
        return logWorkRepository.findAll().stream()
            .map(logWorkMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one logWork by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LogWorkDTO findOne(Long id) {
        log.debug("Request to get LogWork : {}", id);
        LogWork logWork = logWorkRepository.findOne(id);
        return logWorkMapper.toDto(logWork);
    }

    /**
     * Delete the logWork by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogWork : {}", id);
        logWorkRepository.delete(id);
    }
}

package com.asart.asart.service.impl;

import com.asart.asart.service.LinkAuthService;
import com.asart.asart.domain.LinkAuth;
import com.asart.asart.repository.LinkAuthRepository;
import com.asart.asart.service.dto.LinkAuthDTO;
import com.asart.asart.service.mapper.LinkAuthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LinkAuth.
 */
@Service
@Transactional
public class LinkAuthServiceImpl implements LinkAuthService {

    private final Logger log = LoggerFactory.getLogger(LinkAuthServiceImpl.class);

    private final LinkAuthRepository linkAuthRepository;

    private final LinkAuthMapper linkAuthMapper;

    public LinkAuthServiceImpl(LinkAuthRepository linkAuthRepository, LinkAuthMapper linkAuthMapper) {
        this.linkAuthRepository = linkAuthRepository;
        this.linkAuthMapper = linkAuthMapper;
    }

    /**
     * Save a linkAuth.
     *
     * @param linkAuthDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LinkAuthDTO save(LinkAuthDTO linkAuthDTO) {
        log.debug("Request to save LinkAuth : {}", linkAuthDTO);
        LinkAuth linkAuth = linkAuthMapper.toEntity(linkAuthDTO);
        linkAuth = linkAuthRepository.save(linkAuth);
        return linkAuthMapper.toDto(linkAuth);
    }

    /**
     * Get all the linkAuths.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LinkAuthDTO> findAll() {
        log.debug("Request to get all LinkAuths");
        return linkAuthRepository.findAll().stream()
            .map(linkAuthMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one linkAuth by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LinkAuthDTO findOne(Long id) {
        log.debug("Request to get LinkAuth : {}", id);
        LinkAuth linkAuth = linkAuthRepository.findOne(id);
        return linkAuthMapper.toDto(linkAuth);
    }

    /**
     * Delete the linkAuth by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LinkAuth : {}", id);
        linkAuthRepository.delete(id);
    }
}

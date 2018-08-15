package com.asart.asart.service.impl;

import com.asart.asart.domain.LinkAuth;
import com.asart.asart.domain.User;
import com.asart.asart.repository.LinkAuthRepository;
import com.asart.asart.repository.UserRepository;
import com.asart.asart.service.CollaboratorService;
import com.asart.asart.domain.Collaborator;
import com.asart.asart.repository.CollaboratorRepository;
import com.asart.asart.service.dto.CollaboratorDTO;
import com.asart.asart.service.mapper.CollaboratorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Collaborator.
 */
@Service
@Transactional
public class CollaboratorServiceImpl implements CollaboratorService {

    private final Logger log = LoggerFactory.getLogger(CollaboratorServiceImpl.class);

    private final CollaboratorRepository collaboratorRepository;

    private final CollaboratorMapper collaboratorMapper;

    private final UserRepository userRepository;

    private final LinkAuthRepository linkAuthRepository;

    public CollaboratorServiceImpl(CollaboratorRepository collaboratorRepository, CollaboratorMapper collaboratorMapper, UserRepository userRepository, LinkAuthRepository linkAuthRepository) {
        this.collaboratorRepository = collaboratorRepository;
        this.collaboratorMapper = collaboratorMapper;
        this.userRepository = userRepository;
        this.linkAuthRepository = linkAuthRepository;
    }

    /**
     * Save a collaborator.
     *
     * @param collaboratorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CollaboratorDTO save(CollaboratorDTO collaboratorDTO) {
        log.debug("Request to save Collaborator : {}", collaboratorDTO);
        Collaborator collaborator = collaboratorMapper.toEntity(collaboratorDTO);
        collaborator = collaboratorRepository.save(collaborator);
        return collaboratorMapper.toDto(collaborator);
    }

    /**
     * Get all the collaborators.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CollaboratorDTO> findAll() {
        log.debug("Request to get all Collaborators");

        List<CollaboratorDTO> collaboratorDTOS = collaboratorRepository.findAll().stream()
            .map(collaboratorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        for(CollaboratorDTO collaboratorDTO : collaboratorDTOS){
            LinkAuth linkAuth = linkAuthRepository.findByIdCollaborator(collaboratorDTO.getId());
            User user = userRepository.findOne(linkAuth.getIdSession());
            collaboratorDTO.setIdUser(user.getId());
            collaboratorDTO.setActivated(user.getActivated());
            collaboratorDTO.setLogin(user.getLogin());
        }

        return collaboratorDTOS;
    }

    /**
     * Get one collaborator by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CollaboratorDTO findOne(Long id) {
        log.debug("Request to get Collaborator : {}", id);
        Collaborator collaborator = collaboratorRepository.findOne(id);
        return collaboratorMapper.toDto(collaborator);
    }

    /**
     * Delete the collaborator by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Collaborator : {}", id);
        collaboratorRepository.delete(id);
    }
}

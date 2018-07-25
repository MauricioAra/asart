package com.asart.asart.service.impl;

import com.asart.asart.domain.Collaborator;
import com.asart.asart.domain.LinkAuth;
import com.asart.asart.repository.CollaboratorRepository;
import com.asart.asart.repository.LinkAuthRepository;
import com.asart.asart.service.ProjectService;
import com.asart.asart.domain.Project;
import com.asart.asart.repository.ProjectRepository;
import com.asart.asart.service.dto.ProjectDTO;
import com.asart.asart.service.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    private final LinkAuthRepository linkAuthRepository;

    private final CollaboratorRepository collaboratorRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, LinkAuthRepository linkAuthRepository, CollaboratorRepository collaboratorRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.linkAuthRepository = linkAuthRepository;
        this.collaboratorRepository = collaboratorRepository;
    }

    /**
     * Save a project.
     *
     * @param projectDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    /**
     * Get all the projects.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> findAll() {
        log.debug("Request to get all Projects");
        return projectRepository.findAll().stream()
            .map(projectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one project by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProjectDTO findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        Project project = projectRepository.findOne(id);
        return projectMapper.toDto(project);
    }

    /**
     * Delete the project by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
    }

    @Override
    public List<ProjectDTO> findAllByIdCollaborator(Long id) {
        LinkAuth linkAuth = linkAuthRepository.findByIdSession(id);
        Collaborator collaborator = collaboratorRepository.findOne(linkAuth.getIdCollaborator());
        return projectRepository.findAllByCollaborator(collaborator).stream()
            .map(projectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}

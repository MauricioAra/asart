package com.asart.asart.service.mapper;

import com.asart.asart.domain.*;
import com.asart.asart.service.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {CollaboratorMapper.class})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {

    @Mapping(source = "collaborator.id", target = "collaboratorId")
    @Mapping(source = "collaborator.name", target = "collaboratorName")
    ProjectDTO toDto(Project project);

    @Mapping(target = "logWorks", ignore = true)
    @Mapping(source = "collaboratorId", target = "collaborator")
    Project toEntity(ProjectDTO projectDTO);

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }
}

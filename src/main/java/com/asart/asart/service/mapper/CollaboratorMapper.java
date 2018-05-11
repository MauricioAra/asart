package com.asart.asart.service.mapper;

import com.asart.asart.domain.*;
import com.asart.asart.service.dto.CollaboratorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Collaborator and its DTO CollaboratorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CollaboratorMapper extends EntityMapper<CollaboratorDTO, Collaborator> {


    @Mapping(target = "projects", ignore = true)
    Collaborator toEntity(CollaboratorDTO collaboratorDTO);

    default Collaborator fromId(Long id) {
        if (id == null) {
            return null;
        }
        Collaborator collaborator = new Collaborator();
        collaborator.setId(id);
        return collaborator;
    }
}

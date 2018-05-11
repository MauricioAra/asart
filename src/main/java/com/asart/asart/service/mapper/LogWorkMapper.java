package com.asart.asart.service.mapper;

import com.asart.asart.domain.*;
import com.asart.asart.service.dto.LogWorkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LogWork and its DTO LogWorkDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class})
public interface LogWorkMapper extends EntityMapper<LogWorkDTO, LogWork> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    LogWorkDTO toDto(LogWork logWork);

    @Mapping(source = "projectId", target = "project")
    LogWork toEntity(LogWorkDTO logWorkDTO);

    default LogWork fromId(Long id) {
        if (id == null) {
            return null;
        }
        LogWork logWork = new LogWork();
        logWork.setId(id);
        return logWork;
    }
}

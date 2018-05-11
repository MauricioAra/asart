package com.asart.asart.service.mapper;

import com.asart.asart.domain.*;
import com.asart.asart.service.dto.LinkAuthDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LinkAuth and its DTO LinkAuthDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LinkAuthMapper extends EntityMapper<LinkAuthDTO, LinkAuth> {



    default LinkAuth fromId(Long id) {
        if (id == null) {
            return null;
        }
        LinkAuth linkAuth = new LinkAuth();
        linkAuth.setId(id);
        return linkAuth;
    }
}

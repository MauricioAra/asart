package com.asart.asart.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LinkAuth entity.
 */
public class LinkAuthDTO implements Serializable {

    private Long id;

    private Long idSession;

    private Long idCollaborator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }

    public Long getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(Long idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LinkAuthDTO linkAuthDTO = (LinkAuthDTO) o;
        if(linkAuthDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), linkAuthDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LinkAuthDTO{" +
            "id=" + getId() +
            ", idSession=" + getIdSession() +
            ", idCollaborator=" + getIdCollaborator() +
            "}";
    }
}

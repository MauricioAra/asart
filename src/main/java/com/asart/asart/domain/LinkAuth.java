package com.asart.asart.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LinkAuth.
 */
@Entity
@Table(name = "link_auth")
public class LinkAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_session")
    private Long idSession;

    @Column(name = "id_collaborator")
    private Long idCollaborator;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSession() {
        return idSession;
    }

    public LinkAuth idSession(Long idSession) {
        this.idSession = idSession;
        return this;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }

    public Long getIdCollaborator() {
        return idCollaborator;
    }

    public LinkAuth idCollaborator(Long idCollaborator) {
        this.idCollaborator = idCollaborator;
        return this;
    }

    public void setIdCollaborator(Long idCollaborator) {
        this.idCollaborator = idCollaborator;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkAuth linkAuth = (LinkAuth) o;
        if (linkAuth.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), linkAuth.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LinkAuth{" +
            "id=" + getId() +
            ", idSession=" + getIdSession() +
            ", idCollaborator=" + getIdCollaborator() +
            "}";
    }
}

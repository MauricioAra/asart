package com.asart.asart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "total_hours")
    private String totalHours;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private Set<LogWork> logWorks = new HashSet<>();

    @ManyToOne
    private Collaborator collaborator;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public Project totalHours(String totalHours) {
        this.totalHours = totalHours;
        return this;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public String getStartDate() {
        return startDate;
    }

    public Project startDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Project endDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public Project status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<LogWork> getLogWorks() {
        return logWorks;
    }

    public Project logWorks(Set<LogWork> logWorks) {
        this.logWorks = logWorks;
        return this;
    }

    public Project addLogWork(LogWork logWork) {
        this.logWorks.add(logWork);
        logWork.setProject(this);
        return this;
    }

    public Project removeLogWork(LogWork logWork) {
        this.logWorks.remove(logWork);
        logWork.setProject(null);
        return this;
    }

    public void setLogWorks(Set<LogWork> logWorks) {
        this.logWorks = logWorks;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public Project collaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
        return this;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
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
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", totalHours='" + getTotalHours() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

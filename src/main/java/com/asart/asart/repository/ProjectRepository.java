package com.asart.asart.repository;

import com.asart.asart.domain.Collaborator;
import com.asart.asart.domain.Project;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByCollaborator(Collaborator collaborator);
}

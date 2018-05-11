package com.asart.asart.repository;

import com.asart.asart.domain.Collaborator;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Collaborator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

}

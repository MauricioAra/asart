package com.asart.asart.repository;

import com.asart.asart.domain.LogWork;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LogWork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogWorkRepository extends JpaRepository<LogWork, Long> {

}

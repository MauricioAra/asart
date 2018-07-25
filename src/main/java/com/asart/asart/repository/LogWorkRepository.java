package com.asart.asart.repository;

import com.asart.asart.domain.LogWork;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the LogWork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogWorkRepository extends JpaRepository<LogWork, Long> {
    List<LogWork> findAllByProjectId(Long id);
}

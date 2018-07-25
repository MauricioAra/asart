package com.asart.asart.repository;

import com.asart.asart.domain.LinkAuth;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LinkAuth entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinkAuthRepository extends JpaRepository<LinkAuth, Long> {
    LinkAuth findByIdSession(Long IdSesion);
}

package com.qs.courses_alpha.repository;

import com.qs.courses_alpha.domain.Calendario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Calendario entity.
 */
@SuppressWarnings("unused")
public interface CalendarioRepository extends JpaRepository<Calendario,Long> {

}

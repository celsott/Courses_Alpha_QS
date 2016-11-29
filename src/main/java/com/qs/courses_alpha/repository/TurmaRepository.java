package com.qs.courses_alpha.repository;

import com.qs.courses_alpha.domain.Turma;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Turma entity.
 */
@SuppressWarnings("unused")
public interface TurmaRepository extends JpaRepository<Turma,Long> {

}

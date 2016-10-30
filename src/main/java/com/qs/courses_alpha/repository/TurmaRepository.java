package com.qs.courses_alpha.repository;

import com.qs.courses_alpha.domain.Turma;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Turma entity.
 */
@SuppressWarnings("unused")
public interface TurmaRepository extends JpaRepository<Turma,Long> {

    @Query("select distinct turma from Turma turma left join fetch turma.alunos left join fetch turma.salas")
    List<Turma> findAllWithEagerRelationships();

    @Query("select turma from Turma turma left join fetch turma.alunos left join fetch turma.salas where turma.id =:id")
    Turma findOneWithEagerRelationships(@Param("id") Long id);

}

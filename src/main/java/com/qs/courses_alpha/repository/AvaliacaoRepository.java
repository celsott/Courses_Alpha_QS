package com.qs.courses_alpha.repository;

import com.qs.courses_alpha.domain.Avaliacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Avaliacao entity.
 */
@SuppressWarnings("unused")
public interface AvaliacaoRepository extends JpaRepository<Avaliacao,Long> {

    @Query("select distinct avaliacao from Avaliacao avaliacao left join fetch avaliacao.alunos")
    List<Avaliacao> findAllWithEagerRelationships();

    @Query("select avaliacao from Avaliacao avaliacao left join fetch avaliacao.alunos where avaliacao.id =:id")
    Avaliacao findOneWithEagerRelationships(@Param("id") Long id);

}

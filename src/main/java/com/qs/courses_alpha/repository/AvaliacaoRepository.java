package com.qs.courses_alpha.repository;

import com.qs.courses_alpha.domain.Avaliacao;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Avaliacao entity.
 */
@SuppressWarnings("unused")
public interface AvaliacaoRepository extends JpaRepository<Avaliacao,Long> {

}

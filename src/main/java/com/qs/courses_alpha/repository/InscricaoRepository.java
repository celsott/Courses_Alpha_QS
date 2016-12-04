package com.qs.courses_alpha.repository;

import com.qs.courses_alpha.domain.Inscricao;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Inscricao entity.
 */
@SuppressWarnings("unused")
public interface InscricaoRepository extends JpaRepository<Inscricao,Long> {

}

package com.qs.courses_alpha.service;

import com.qs.courses_alpha.service.dto.AvaliacaoDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Avaliacao.
 */
public interface AvaliacaoService {

    /**
     * Save a avaliacao.
     *
     * @param avaliacaoDTO the entity to save
     * @return the persisted entity
     */
    AvaliacaoDTO save(AvaliacaoDTO avaliacaoDTO);

    /**
     *  Get all the avaliacaos.
     *  
     *  @return the list of entities
     */
    List<AvaliacaoDTO> findAll();

    /**
     *  Get the "id" avaliacao.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AvaliacaoDTO findOne(Long id);

    /**
     *  Delete the "id" avaliacao.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

package com.qs.courses_alpha.service;

import com.qs.courses_alpha.service.dto.InscricaoDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Inscricao.
 */
public interface InscricaoService {

    /**
     * Save a inscricao.
     *
     * @param inscricaoDTO the entity to save
     * @return the persisted entity
     */
    InscricaoDTO save(InscricaoDTO inscricaoDTO);

    /**
     *  Get all the inscricaos.
     *  
     *  @return the list of entities
     */
    List<InscricaoDTO> findAll();

    /**
     *  Get the "id" inscricao.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InscricaoDTO findOne(Long id);

    /**
     *  Delete the "id" inscricao.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

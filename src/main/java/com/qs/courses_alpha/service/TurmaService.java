package com.qs.courses_alpha.service;

import com.qs.courses_alpha.service.dto.TurmaDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Turma.
 */
public interface TurmaService {

    /**
     * Save a turma.
     *
     * @param turmaDTO the entity to save
     * @return the persisted entity
     */
    TurmaDTO save(TurmaDTO turmaDTO);

    /**
     *  Get all the turmas.
     *  
     *  @return the list of entities
     */
    List<TurmaDTO> findAll();

    /**
     *  Get the "id" turma.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TurmaDTO findOne(Long id);

    /**
     *  Delete the "id" turma.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

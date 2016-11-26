package com.qs.courses_alpha.service;

import com.qs.courses_alpha.service.dto.DisciplinaDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Disciplina.
 */
public interface DisciplinaService {

    /**
     * Save a disciplina.
     *
     * @param disciplinaDTO the entity to save
     * @return the persisted entity
     */
    DisciplinaDTO save(DisciplinaDTO disciplinaDTO);

    /**
     *  Get all the disciplinas.
     *  
     *  @return the list of entities
     */
    List<DisciplinaDTO> findAll();

    /**
     *  Get the "id" disciplina.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DisciplinaDTO findOne(Long id);

    /**
     *  Delete the "id" disciplina.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

package com.qs.courses_alpha.service;

import com.qs.courses_alpha.service.dto.SalaDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Sala.
 */
public interface SalaService {

    /**
     * Save a sala.
     *
     * @param salaDTO the entity to save
     * @return the persisted entity
     */
    SalaDTO save(SalaDTO salaDTO);

    /**
     *  Get all the salas.
     *  
     *  @return the list of entities
     */
    List<SalaDTO> findAll();

    /**
     *  Get the "id" sala.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SalaDTO findOne(Long id);

    /**
     *  Delete the "id" sala.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

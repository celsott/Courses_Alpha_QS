package com.qs.courses_alpha.service.impl;

import com.qs.courses_alpha.service.SalaService;
import com.qs.courses_alpha.domain.Sala;
import com.qs.courses_alpha.repository.SalaRepository;
import com.qs.courses_alpha.service.dto.SalaDTO;
import com.qs.courses_alpha.service.mapper.SalaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Sala.
 */
@Service
@Transactional
public class SalaServiceImpl implements SalaService{

    private final Logger log = LoggerFactory.getLogger(SalaServiceImpl.class);
    
    @Inject
    private SalaRepository salaRepository;

    @Inject
    private SalaMapper salaMapper;

    /**
     * Save a sala.
     *
     * @param salaDTO the entity to save
     * @return the persisted entity
     */
    public SalaDTO save(SalaDTO salaDTO) {
        log.debug("Request to save Sala : {}", salaDTO);
        Sala sala = salaMapper.salaDTOToSala(salaDTO);
        sala = salaRepository.save(sala);
        SalaDTO result = salaMapper.salaToSalaDTO(sala);
        return result;
    }

    /**
     *  Get all the salas.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SalaDTO> findAll() {
        log.debug("Request to get all Salas");
        List<SalaDTO> result = salaRepository.findAll().stream()
            .map(salaMapper::salaToSalaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one sala by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalaDTO findOne(Long id) {
        log.debug("Request to get Sala : {}", id);
        Sala sala = salaRepository.findOne(id);
        SalaDTO salaDTO = salaMapper.salaToSalaDTO(sala);
        return salaDTO;
    }

    /**
     *  Delete the  sala by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sala : {}", id);
        salaRepository.delete(id);
    }
}

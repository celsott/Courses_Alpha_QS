package com.qs.courses_alpha.service.impl;

import com.qs.courses_alpha.service.TurmaService;
import com.qs.courses_alpha.domain.Turma;
import com.qs.courses_alpha.repository.TurmaRepository;
import com.qs.courses_alpha.service.dto.TurmaDTO;
import com.qs.courses_alpha.service.mapper.TurmaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Turma.
 */
@Service
@Transactional
public class TurmaServiceImpl implements TurmaService{

    private final Logger log = LoggerFactory.getLogger(TurmaServiceImpl.class);
    
    @Inject
    private TurmaRepository turmaRepository;

    @Inject
    private TurmaMapper turmaMapper;

    /**
     * Save a turma.
     *
     * @param turmaDTO the entity to save
     * @return the persisted entity
     */
    public TurmaDTO save(TurmaDTO turmaDTO) {
        log.debug("Request to save Turma : {}", turmaDTO);
        Turma turma = turmaMapper.turmaDTOToTurma(turmaDTO);
        turma = turmaRepository.save(turma);
        TurmaDTO result = turmaMapper.turmaToTurmaDTO(turma);
        return result;
    }

    /**
     *  Get all the turmas.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TurmaDTO> findAll() {
        log.debug("Request to get all Turmas");
        List<TurmaDTO> result = turmaRepository.findAll().stream()
            .map(turmaMapper::turmaToTurmaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one turma by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TurmaDTO findOne(Long id) {
        log.debug("Request to get Turma : {}", id);
        Turma turma = turmaRepository.findOne(id);
        TurmaDTO turmaDTO = turmaMapper.turmaToTurmaDTO(turma);
        return turmaDTO;
    }

    /**
     *  Delete the  turma by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Turma : {}", id);
        turmaRepository.delete(id);
    }
}

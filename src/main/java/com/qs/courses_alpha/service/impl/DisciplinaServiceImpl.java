package com.qs.courses_alpha.service.impl;

import com.qs.courses_alpha.service.DisciplinaService;
import com.qs.courses_alpha.domain.Disciplina;
import com.qs.courses_alpha.repository.DisciplinaRepository;
import com.qs.courses_alpha.service.dto.DisciplinaDTO;
import com.qs.courses_alpha.service.mapper.DisciplinaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Disciplina.
 */
@Service
@Transactional
public class DisciplinaServiceImpl implements DisciplinaService{

    private final Logger log = LoggerFactory.getLogger(DisciplinaServiceImpl.class);
    
    @Inject
    private DisciplinaRepository disciplinaRepository;

    @Inject
    private DisciplinaMapper disciplinaMapper;

    /**
     * Save a disciplina.
     *
     * @param disciplinaDTO the entity to save
     * @return the persisted entity
     */
    public DisciplinaDTO save(DisciplinaDTO disciplinaDTO) {
        log.debug("Request to save Disciplina : {}", disciplinaDTO);
        Disciplina disciplina = disciplinaMapper.disciplinaDTOToDisciplina(disciplinaDTO);
        disciplina = disciplinaRepository.save(disciplina);
        DisciplinaDTO result = disciplinaMapper.disciplinaToDisciplinaDTO(disciplina);
        return result;
    }

    /**
     *  Get all the disciplinas.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DisciplinaDTO> findAll() {
        log.debug("Request to get all Disciplinas");
        List<DisciplinaDTO> result = disciplinaRepository.findAll().stream()
            .map(disciplinaMapper::disciplinaToDisciplinaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one disciplina by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DisciplinaDTO findOne(Long id) {
        log.debug("Request to get Disciplina : {}", id);
        Disciplina disciplina = disciplinaRepository.findOne(id);
        DisciplinaDTO disciplinaDTO = disciplinaMapper.disciplinaToDisciplinaDTO(disciplina);
        return disciplinaDTO;
    }

    /**
     *  Delete the  disciplina by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Disciplina : {}", id);
        disciplinaRepository.delete(id);
    }
}

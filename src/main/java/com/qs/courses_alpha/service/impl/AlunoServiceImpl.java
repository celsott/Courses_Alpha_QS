package com.qs.courses_alpha.service.impl;

import com.qs.courses_alpha.service.AlunoService;
import com.qs.courses_alpha.domain.Aluno;
import com.qs.courses_alpha.repository.AlunoRepository;
import com.qs.courses_alpha.service.dto.AlunoDTO;
import com.qs.courses_alpha.service.mapper.AlunoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Aluno.
 */
@Service
@Transactional
public class AlunoServiceImpl implements AlunoService{

    private final Logger log = LoggerFactory.getLogger(AlunoServiceImpl.class);
    
    @Inject
    private AlunoRepository alunoRepository;

    @Inject
    private AlunoMapper alunoMapper;

    /**
     * Save a aluno.
     *
     * @param alunoDTO the entity to save
     * @return the persisted entity
     */
    public AlunoDTO save(AlunoDTO alunoDTO) {
        log.debug("Request to save Aluno : {}", alunoDTO);
        Aluno aluno = alunoMapper.alunoDTOToAluno(alunoDTO);
        aluno = alunoRepository.save(aluno);
        AlunoDTO result = alunoMapper.alunoToAlunoDTO(aluno);
        return result;
    }

    /**
     *  Get all the alunos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AlunoDTO> findAll() {
        log.debug("Request to get all Alunos");
        List<AlunoDTO> result = alunoRepository.findAll().stream()
            .map(alunoMapper::alunoToAlunoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one aluno by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AlunoDTO findOne(Long id) {
        log.debug("Request to get Aluno : {}", id);
        Aluno aluno = alunoRepository.findOne(id);
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);
        return alunoDTO;
    }

    /**
     *  Delete the  aluno by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Aluno : {}", id);
        alunoRepository.delete(id);
    }
}

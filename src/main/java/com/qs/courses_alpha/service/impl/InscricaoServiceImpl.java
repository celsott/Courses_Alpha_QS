package com.qs.courses_alpha.service.impl;

import com.qs.courses_alpha.service.InscricaoService;
import com.qs.courses_alpha.domain.Inscricao;
import com.qs.courses_alpha.repository.InscricaoRepository;
import com.qs.courses_alpha.service.dto.InscricaoDTO;
import com.qs.courses_alpha.service.mapper.InscricaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Inscricao.
 */
@Service
@Transactional
public class InscricaoServiceImpl implements InscricaoService{

    private final Logger log = LoggerFactory.getLogger(InscricaoServiceImpl.class);
    
    @Inject
    private InscricaoRepository inscricaoRepository;

    @Inject
    private InscricaoMapper inscricaoMapper;

    /**
     * Save a inscricao.
     *
     * @param inscricaoDTO the entity to save
     * @return the persisted entity
     */
    public InscricaoDTO save(InscricaoDTO inscricaoDTO) {
        log.debug("Request to save Inscricao : {}", inscricaoDTO);
        Inscricao inscricao = inscricaoMapper.inscricaoDTOToInscricao(inscricaoDTO);
        inscricao = inscricaoRepository.save(inscricao);
        InscricaoDTO result = inscricaoMapper.inscricaoToInscricaoDTO(inscricao);
        return result;
    }

    /**
     *  Get all the inscricaos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<InscricaoDTO> findAll() {
        log.debug("Request to get all Inscricaos");
        List<InscricaoDTO> result = inscricaoRepository.findAll().stream()
            .map(inscricaoMapper::inscricaoToInscricaoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one inscricao by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public InscricaoDTO findOne(Long id) {
        log.debug("Request to get Inscricao : {}", id);
        Inscricao inscricao = inscricaoRepository.findOne(id);
        InscricaoDTO inscricaoDTO = inscricaoMapper.inscricaoToInscricaoDTO(inscricao);
        return inscricaoDTO;
    }

    /**
     *  Delete the  inscricao by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inscricao : {}", id);
        inscricaoRepository.delete(id);
    }
}

package com.qs.courses_alpha.service.impl;

import com.qs.courses_alpha.service.AvaliacaoService;
import com.qs.courses_alpha.domain.Avaliacao;
import com.qs.courses_alpha.repository.AvaliacaoRepository;
import com.qs.courses_alpha.service.dto.AvaliacaoDTO;
import com.qs.courses_alpha.service.mapper.AvaliacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Avaliacao.
 */
@Service
@Transactional
public class AvaliacaoServiceImpl implements AvaliacaoService{

    private final Logger log = LoggerFactory.getLogger(AvaliacaoServiceImpl.class);
    
    @Inject
    private AvaliacaoRepository avaliacaoRepository;

    @Inject
    private AvaliacaoMapper avaliacaoMapper;

    /**
     * Save a avaliacao.
     *
     * @param avaliacaoDTO the entity to save
     * @return the persisted entity
     */
    public AvaliacaoDTO save(AvaliacaoDTO avaliacaoDTO) {
        log.debug("Request to save Avaliacao : {}", avaliacaoDTO);
        Avaliacao avaliacao = avaliacaoMapper.avaliacaoDTOToAvaliacao(avaliacaoDTO);
        avaliacao = avaliacaoRepository.save(avaliacao);
        AvaliacaoDTO result = avaliacaoMapper.avaliacaoToAvaliacaoDTO(avaliacao);
        return result;
    }

    /**
     *  Get all the avaliacaos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AvaliacaoDTO> findAll() {
        log.debug("Request to get all Avaliacaos");
        List<AvaliacaoDTO> result = avaliacaoRepository.findAll().stream()
            .map(avaliacaoMapper::avaliacaoToAvaliacaoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one avaliacao by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AvaliacaoDTO findOne(Long id) {
        log.debug("Request to get Avaliacao : {}", id);
        Avaliacao avaliacao = avaliacaoRepository.findOne(id);
        AvaliacaoDTO avaliacaoDTO = avaliacaoMapper.avaliacaoToAvaliacaoDTO(avaliacao);
        return avaliacaoDTO;
    }

    /**
     *  Delete the  avaliacao by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Avaliacao : {}", id);
        avaliacaoRepository.delete(id);
    }
}

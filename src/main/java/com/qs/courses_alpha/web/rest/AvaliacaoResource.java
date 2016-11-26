package com.qs.courses_alpha.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qs.courses_alpha.service.AvaliacaoService;
import com.qs.courses_alpha.web.rest.util.HeaderUtil;
import com.qs.courses_alpha.service.dto.AvaliacaoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Avaliacao.
 */
@RestController
@RequestMapping("/api")
public class AvaliacaoResource {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoResource.class);
        
    @Inject
    private AvaliacaoService avaliacaoService;

    /**
     * POST  /avaliacaos : Create a new avaliacao.
     *
     * @param avaliacaoDTO the avaliacaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avaliacaoDTO, or with status 400 (Bad Request) if the avaliacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avaliacaos")
    @Timed
    public ResponseEntity<AvaliacaoDTO> createAvaliacao(@Valid @RequestBody AvaliacaoDTO avaliacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Avaliacao : {}", avaliacaoDTO);
        if (avaliacaoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("avaliacao", "idexists", "A new avaliacao cannot already have an ID")).body(null);
        }
        AvaliacaoDTO result = avaliacaoService.save(avaliacaoDTO);
        return ResponseEntity.created(new URI("/api/avaliacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("avaliacao", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avaliacaos : Updates an existing avaliacao.
     *
     * @param avaliacaoDTO the avaliacaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avaliacaoDTO,
     * or with status 400 (Bad Request) if the avaliacaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the avaliacaoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avaliacaos")
    @Timed
    public ResponseEntity<AvaliacaoDTO> updateAvaliacao(@Valid @RequestBody AvaliacaoDTO avaliacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Avaliacao : {}", avaliacaoDTO);
        if (avaliacaoDTO.getId() == null) {
            return createAvaliacao(avaliacaoDTO);
        }
        AvaliacaoDTO result = avaliacaoService.save(avaliacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("avaliacao", avaliacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avaliacaos : get all the avaliacaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of avaliacaos in body
     */
    @GetMapping("/avaliacaos")
    @Timed
    public List<AvaliacaoDTO> getAllAvaliacaos() {
        log.debug("REST request to get all Avaliacaos");
        return avaliacaoService.findAll();
    }

    /**
     * GET  /avaliacaos/:id : get the "id" avaliacao.
     *
     * @param id the id of the avaliacaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avaliacaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/avaliacaos/{id}")
    @Timed
    public ResponseEntity<AvaliacaoDTO> getAvaliacao(@PathVariable Long id) {
        log.debug("REST request to get Avaliacao : {}", id);
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.findOne(id);
        return Optional.ofNullable(avaliacaoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /avaliacaos/:id : delete the "id" avaliacao.
     *
     * @param id the id of the avaliacaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avaliacaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        log.debug("REST request to delete Avaliacao : {}", id);
        avaliacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("avaliacao", id.toString())).build();
    }

}

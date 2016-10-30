package com.qs.courses_alpha.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qs.courses_alpha.domain.Avaliacao;

import com.qs.courses_alpha.repository.AvaliacaoRepository;
import com.qs.courses_alpha.web.rest.util.HeaderUtil;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Avaliacao.
 */
@RestController
@RequestMapping("/api")
public class AvaliacaoResource {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoResource.class);
        
    @Inject
    private AvaliacaoRepository avaliacaoRepository;

    /**
     * POST  /avaliacaos : Create a new avaliacao.
     *
     * @param avaliacao the avaliacao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avaliacao, or with status 400 (Bad Request) if the avaliacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avaliacaos")
    @Timed
    public ResponseEntity<Avaliacao> createAvaliacao(@Valid @RequestBody Avaliacao avaliacao) throws URISyntaxException {
        log.debug("REST request to save Avaliacao : {}", avaliacao);
        if (avaliacao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("avaliacao", "idexists", "A new avaliacao cannot already have an ID")).body(null);
        }
        Avaliacao result = avaliacaoRepository.save(avaliacao);
        return ResponseEntity.created(new URI("/api/avaliacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("avaliacao", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avaliacaos : Updates an existing avaliacao.
     *
     * @param avaliacao the avaliacao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avaliacao,
     * or with status 400 (Bad Request) if the avaliacao is not valid,
     * or with status 500 (Internal Server Error) if the avaliacao couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avaliacaos")
    @Timed
    public ResponseEntity<Avaliacao> updateAvaliacao(@Valid @RequestBody Avaliacao avaliacao) throws URISyntaxException {
        log.debug("REST request to update Avaliacao : {}", avaliacao);
        if (avaliacao.getId() == null) {
            return createAvaliacao(avaliacao);
        }
        Avaliacao result = avaliacaoRepository.save(avaliacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("avaliacao", avaliacao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avaliacaos : get all the avaliacaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of avaliacaos in body
     */
    @GetMapping("/avaliacaos")
    @Timed
    public List<Avaliacao> getAllAvaliacaos() {
        log.debug("REST request to get all Avaliacaos");
        List<Avaliacao> avaliacaos = avaliacaoRepository.findAllWithEagerRelationships();
        return avaliacaos;
    }

    /**
     * GET  /avaliacaos/:id : get the "id" avaliacao.
     *
     * @param id the id of the avaliacao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avaliacao, or with status 404 (Not Found)
     */
    @GetMapping("/avaliacaos/{id}")
    @Timed
    public ResponseEntity<Avaliacao> getAvaliacao(@PathVariable Long id) {
        log.debug("REST request to get Avaliacao : {}", id);
        Avaliacao avaliacao = avaliacaoRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(avaliacao)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /avaliacaos/:id : delete the "id" avaliacao.
     *
     * @param id the id of the avaliacao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avaliacaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        log.debug("REST request to delete Avaliacao : {}", id);
        avaliacaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("avaliacao", id.toString())).build();
    }

}

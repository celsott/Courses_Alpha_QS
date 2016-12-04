package com.qs.courses_alpha.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qs.courses_alpha.service.InscricaoService;
import com.qs.courses_alpha.web.rest.util.HeaderUtil;
import com.qs.courses_alpha.service.dto.InscricaoDTO;
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
 * REST controller for managing Inscricao.
 */
@RestController
@RequestMapping("/api")
public class InscricaoResource {

    private final Logger log = LoggerFactory.getLogger(InscricaoResource.class);
        
    @Inject
    private InscricaoService inscricaoService;

    /**
     * POST  /inscricaos : Create a new inscricao.
     *
     * @param inscricaoDTO the inscricaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inscricaoDTO, or with status 400 (Bad Request) if the inscricao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inscricaos")
    @Timed
    public ResponseEntity<InscricaoDTO> createInscricao(@Valid @RequestBody InscricaoDTO inscricaoDTO) throws URISyntaxException {
        log.debug("REST request to save Inscricao : {}", inscricaoDTO);
        if (inscricaoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("inscricao", "idexists", "A new inscricao cannot already have an ID")).body(null);
        }
        InscricaoDTO result = inscricaoService.save(inscricaoDTO);
        return ResponseEntity.created(new URI("/api/inscricaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("inscricao", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inscricaos : Updates an existing inscricao.
     *
     * @param inscricaoDTO the inscricaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inscricaoDTO,
     * or with status 400 (Bad Request) if the inscricaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the inscricaoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inscricaos")
    @Timed
    public ResponseEntity<InscricaoDTO> updateInscricao(@Valid @RequestBody InscricaoDTO inscricaoDTO) throws URISyntaxException {
        log.debug("REST request to update Inscricao : {}", inscricaoDTO);
        if (inscricaoDTO.getId() == null) {
            return createInscricao(inscricaoDTO);
        }
        InscricaoDTO result = inscricaoService.save(inscricaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("inscricao", inscricaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inscricaos : get all the inscricaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inscricaos in body
     */
    @GetMapping("/inscricaos")
    @Timed
    public List<InscricaoDTO> getAllInscricaos() {
        log.debug("REST request to get all Inscricaos");
        return inscricaoService.findAll();
    }

    /**
     * GET  /inscricaos/:id : get the "id" inscricao.
     *
     * @param id the id of the inscricaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inscricaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inscricaos/{id}")
    @Timed
    public ResponseEntity<InscricaoDTO> getInscricao(@PathVariable Long id) {
        log.debug("REST request to get Inscricao : {}", id);
        InscricaoDTO inscricaoDTO = inscricaoService.findOne(id);
        return Optional.ofNullable(inscricaoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inscricaos/:id : delete the "id" inscricao.
     *
     * @param id the id of the inscricaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inscricaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInscricao(@PathVariable Long id) {
        log.debug("REST request to delete Inscricao : {}", id);
        inscricaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("inscricao", id.toString())).build();
    }

}

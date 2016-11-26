package com.qs.courses_alpha.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qs.courses_alpha.service.DisciplinaService;
import com.qs.courses_alpha.web.rest.util.HeaderUtil;
import com.qs.courses_alpha.service.dto.DisciplinaDTO;
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
 * REST controller for managing Disciplina.
 */
@RestController
@RequestMapping("/api")
public class DisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaResource.class);
        
    @Inject
    private DisciplinaService disciplinaService;

    /**
     * POST  /disciplinas : Create a new disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disciplinaDTO, or with status 400 (Bad Request) if the disciplina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disciplinas")
    @Timed
    public ResponseEntity<DisciplinaDTO> createDisciplina(@Valid @RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to save Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("disciplina", "idexists", "A new disciplina cannot already have an ID")).body(null);
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity.created(new URI("/api/disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("disciplina", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disciplinas : Updates an existing disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disciplinaDTO,
     * or with status 400 (Bad Request) if the disciplinaDTO is not valid,
     * or with status 500 (Internal Server Error) if the disciplinaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disciplinas")
    @Timed
    public ResponseEntity<DisciplinaDTO> updateDisciplina(@Valid @RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to update Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() == null) {
            return createDisciplina(disciplinaDTO);
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("disciplina", disciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disciplinas : get all the disciplinas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of disciplinas in body
     */
    @GetMapping("/disciplinas")
    @Timed
    public List<DisciplinaDTO> getAllDisciplinas() {
        log.debug("REST request to get all Disciplinas");
        return disciplinaService.findAll();
    }

    /**
     * GET  /disciplinas/:id : get the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disciplinaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/disciplinas/{id}")
    @Timed
    public ResponseEntity<DisciplinaDTO> getDisciplina(@PathVariable Long id) {
        log.debug("REST request to get Disciplina : {}", id);
        DisciplinaDTO disciplinaDTO = disciplinaService.findOne(id);
        return Optional.ofNullable(disciplinaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /disciplinas/:id : delete the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disciplinas/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete Disciplina : {}", id);
        disciplinaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("disciplina", id.toString())).build();
    }

}

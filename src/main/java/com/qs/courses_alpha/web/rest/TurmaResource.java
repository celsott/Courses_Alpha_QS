package com.qs.courses_alpha.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qs.courses_alpha.service.TurmaService;
import com.qs.courses_alpha.web.rest.util.HeaderUtil;
import com.qs.courses_alpha.service.dto.TurmaDTO;
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
 * REST controller for managing Turma.
 */
@RestController
@RequestMapping("/api")
public class TurmaResource {

    private final Logger log = LoggerFactory.getLogger(TurmaResource.class);
        
    @Inject
    private TurmaService turmaService;

    /**
     * POST  /turmas : Create a new turma.
     *
     * @param turmaDTO the turmaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new turmaDTO, or with status 400 (Bad Request) if the turma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/turmas")
    @Timed
    public ResponseEntity<TurmaDTO> createTurma(@Valid @RequestBody TurmaDTO turmaDTO) throws URISyntaxException {
        log.debug("REST request to save Turma : {}", turmaDTO);
        if (turmaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("turma", "idexists", "A new turma cannot already have an ID")).body(null);
        }
        TurmaDTO result = turmaService.save(turmaDTO);
        return ResponseEntity.created(new URI("/api/turmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("turma", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /turmas : Updates an existing turma.
     *
     * @param turmaDTO the turmaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated turmaDTO,
     * or with status 400 (Bad Request) if the turmaDTO is not valid,
     * or with status 500 (Internal Server Error) if the turmaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/turmas")
    @Timed
    public ResponseEntity<TurmaDTO> updateTurma(@Valid @RequestBody TurmaDTO turmaDTO) throws URISyntaxException {
        log.debug("REST request to update Turma : {}", turmaDTO);
        if (turmaDTO.getId() == null) {
            return createTurma(turmaDTO);
        }
        TurmaDTO result = turmaService.save(turmaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("turma", turmaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /turmas : get all the turmas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of turmas in body
     */
    @GetMapping("/turmas")
    @Timed
    public List<TurmaDTO> getAllTurmas() {
        log.debug("REST request to get all Turmas");
        return turmaService.findAll();
    }

    /**
     * GET  /turmas/:id : get the "id" turma.
     *
     * @param id the id of the turmaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the turmaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/turmas/{id}")
    @Timed
    public ResponseEntity<TurmaDTO> getTurma(@PathVariable Long id) {
        log.debug("REST request to get Turma : {}", id);
        TurmaDTO turmaDTO = turmaService.findOne(id);
        return Optional.ofNullable(turmaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /turmas/:id : delete the "id" turma.
     *
     * @param id the id of the turmaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/turmas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        log.debug("REST request to delete Turma : {}", id);
        turmaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("turma", id.toString())).build();
    }

}

package com.qs.courses_alpha.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qs.courses_alpha.service.SalaService;
import com.qs.courses_alpha.web.rest.util.HeaderUtil;
import com.qs.courses_alpha.service.dto.SalaDTO;
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
 * REST controller for managing Sala.
 */
@RestController
@RequestMapping("/api")
public class SalaResource {

    private final Logger log = LoggerFactory.getLogger(SalaResource.class);
        
    @Inject
    private SalaService salaService;

    /**
     * POST  /salas : Create a new sala.
     *
     * @param salaDTO the salaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salaDTO, or with status 400 (Bad Request) if the sala has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salas")
    @Timed
    public ResponseEntity<SalaDTO> createSala(@Valid @RequestBody SalaDTO salaDTO) throws URISyntaxException {
        log.debug("REST request to save Sala : {}", salaDTO);
        if (salaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sala", "idexists", "A new sala cannot already have an ID")).body(null);
        }
        SalaDTO result = salaService.save(salaDTO);
        return ResponseEntity.created(new URI("/api/salas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sala", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salas : Updates an existing sala.
     *
     * @param salaDTO the salaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salaDTO,
     * or with status 400 (Bad Request) if the salaDTO is not valid,
     * or with status 500 (Internal Server Error) if the salaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salas")
    @Timed
    public ResponseEntity<SalaDTO> updateSala(@Valid @RequestBody SalaDTO salaDTO) throws URISyntaxException {
        log.debug("REST request to update Sala : {}", salaDTO);
        if (salaDTO.getId() == null) {
            return createSala(salaDTO);
        }
        SalaDTO result = salaService.save(salaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sala", salaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salas : get all the salas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salas in body
     */
    @GetMapping("/salas")
    @Timed
    public List<SalaDTO> getAllSalas() {
        log.debug("REST request to get all Salas");
        return salaService.findAll();
    }

    /**
     * GET  /salas/:id : get the "id" sala.
     *
     * @param id the id of the salaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/salas/{id}")
    @Timed
    public ResponseEntity<SalaDTO> getSala(@PathVariable Long id) {
        log.debug("REST request to get Sala : {}", id);
        SalaDTO salaDTO = salaService.findOne(id);
        return Optional.ofNullable(salaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /salas/:id : delete the "id" sala.
     *
     * @param id the id of the salaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salas/{id}")
    @Timed
    public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
        log.debug("REST request to delete Sala : {}", id);
        salaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sala", id.toString())).build();
    }

}

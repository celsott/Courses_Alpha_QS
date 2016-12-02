package com.qs.courses_alpha.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qs.courses_alpha.domain.Calendario;

import com.qs.courses_alpha.repository.CalendarioRepository;
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
 * REST controller for managing Calendario.
 */
@RestController
@RequestMapping("/api")
public class CalendarioResource {

    private final Logger log = LoggerFactory.getLogger(CalendarioResource.class);
        
    @Inject
    private CalendarioRepository calendarioRepository;

    /**
     * POST  /calendarios : Create a new calendario.
     *
     * @param calendario the calendario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calendario, or with status 400 (Bad Request) if the calendario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calendarios")
    @Timed
    public ResponseEntity<Calendario> createCalendario(@Valid @RequestBody Calendario calendario) throws URISyntaxException {
        log.debug("REST request to save Calendario : {}", calendario);
        if (calendario.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("calendario", "idexists", "A new calendario cannot already have an ID")).body(null);
        }
        Calendario result = calendarioRepository.save(calendario);
        return ResponseEntity.created(new URI("/api/calendarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("calendario", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calendarios : Updates an existing calendario.
     *
     * @param calendario the calendario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calendario,
     * or with status 400 (Bad Request) if the calendario is not valid,
     * or with status 500 (Internal Server Error) if the calendario couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calendarios")
    @Timed
    public ResponseEntity<Calendario> updateCalendario(@Valid @RequestBody Calendario calendario) throws URISyntaxException {
        log.debug("REST request to update Calendario : {}", calendario);
        if (calendario.getId() == null) {
            return createCalendario(calendario);
        }
        Calendario result = calendarioRepository.save(calendario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("calendario", calendario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calendarios : get all the calendarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calendarios in body
     */
    @GetMapping("/calendarios")
    @Timed
    public List<Calendario> getAllCalendarios() {
        log.debug("REST request to get all Calendarios");
        List<Calendario> calendarios = calendarioRepository.findAll();
        return calendarios;
    }

    /**
     * GET  /calendarios/:id : get the "id" calendario.
     *
     * @param id the id of the calendario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calendario, or with status 404 (Not Found)
     */
    @GetMapping("/calendarios/{id}")
    @Timed
    public ResponseEntity<Calendario> getCalendario(@PathVariable Long id) {
        log.debug("REST request to get Calendario : {}", id);
        Calendario calendario = calendarioRepository.findOne(id);
        return Optional.ofNullable(calendario)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /calendarios/:id : delete the "id" calendario.
     *
     * @param id the id of the calendario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calendarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalendario(@PathVariable Long id) {
        log.debug("REST request to delete Calendario : {}", id);
        calendarioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("calendario", id.toString())).build();
    }

}

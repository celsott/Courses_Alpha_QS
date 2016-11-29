package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Sala;
import com.qs.courses_alpha.repository.SalaRepository;
import com.qs.courses_alpha.service.SalaService;
import com.qs.courses_alpha.service.dto.SalaDTO;
import com.qs.courses_alpha.service.mapper.SalaMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SalaResource REST controller.
 *
 * @see SalaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class SalaResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACIDADE = 0;
    private static final Integer UPDATED_CAPACIDADE = 1;

    @Inject
    private SalaRepository salaRepository;

    @Inject
    private SalaMapper salaMapper;

    @Inject
    private SalaService salaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalaMockMvc;

    private Sala sala;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalaResource salaResource = new SalaResource();
        ReflectionTestUtils.setField(salaResource, "salaService", salaService);
        this.restSalaMockMvc = MockMvcBuilders.standaloneSetup(salaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sala createEntity(EntityManager em) {
        Sala sala = new Sala()
                .numero(DEFAULT_NUMERO)
                .capacidade(DEFAULT_CAPACIDADE);
        return sala;
    }

    @Before
    public void initTest() {
        sala = createEntity(em);
    }

    @Test
    @Transactional
    public void createSala() throws Exception {
        int databaseSizeBeforeCreate = salaRepository.findAll().size();

        // Create the Sala
        SalaDTO salaDTO = salaMapper.salaToSalaDTO(sala);

        restSalaMockMvc.perform(post("/api/salas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
                .andExpect(status().isCreated());

        // Validate the Sala in the database
        List<Sala> salas = salaRepository.findAll();
        assertThat(salas).hasSize(databaseSizeBeforeCreate + 1);
        Sala testSala = salas.get(salas.size() - 1);
        assertThat(testSala.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testSala.getCapacidade()).isEqualTo(DEFAULT_CAPACIDADE);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaRepository.findAll().size();
        // set the field null
        sala.setNumero(null);

        // Create the Sala, which fails.
        SalaDTO salaDTO = salaMapper.salaToSalaDTO(sala);

        restSalaMockMvc.perform(post("/api/salas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
                .andExpect(status().isBadRequest());

        List<Sala> salas = salaRepository.findAll();
        assertThat(salas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaRepository.findAll().size();
        // set the field null
        sala.setCapacidade(null);

        // Create the Sala, which fails.
        SalaDTO salaDTO = salaMapper.salaToSalaDTO(sala);

        restSalaMockMvc.perform(post("/api/salas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
                .andExpect(status().isBadRequest());

        List<Sala> salas = salaRepository.findAll();
        assertThat(salas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalas() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        // Get all the salas
        restSalaMockMvc.perform(get("/api/salas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sala.getId().intValue())))
                .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
                .andExpect(jsonPath("$.[*].capacidade").value(hasItem(DEFAULT_CAPACIDADE)));
    }

    @Test
    @Transactional
    public void getSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        // Get the sala
        restSalaMockMvc.perform(get("/api/salas/{id}", sala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sala.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.capacidade").value(DEFAULT_CAPACIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingSala() throws Exception {
        // Get the sala
        restSalaMockMvc.perform(get("/api/salas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);
        int databaseSizeBeforeUpdate = salaRepository.findAll().size();

        // Update the sala
        Sala updatedSala = salaRepository.findOne(sala.getId());
        updatedSala
                .numero(UPDATED_NUMERO)
                .capacidade(UPDATED_CAPACIDADE);
        SalaDTO salaDTO = salaMapper.salaToSalaDTO(updatedSala);

        restSalaMockMvc.perform(put("/api/salas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
                .andExpect(status().isOk());

        // Validate the Sala in the database
        List<Sala> salas = salaRepository.findAll();
        assertThat(salas).hasSize(databaseSizeBeforeUpdate);
        Sala testSala = salas.get(salas.size() - 1);
        assertThat(testSala.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testSala.getCapacidade()).isEqualTo(UPDATED_CAPACIDADE);
    }

    @Test
    @Transactional
    public void deleteSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);
        int databaseSizeBeforeDelete = salaRepository.findAll().size();

        // Get the sala
        restSalaMockMvc.perform(delete("/api/salas/{id}", sala.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Sala> salas = salaRepository.findAll();
        assertThat(salas).hasSize(databaseSizeBeforeDelete - 1);
    }
}

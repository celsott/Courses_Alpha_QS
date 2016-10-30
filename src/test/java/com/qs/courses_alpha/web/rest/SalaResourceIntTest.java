package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Sala;
import com.qs.courses_alpha.repository.SalaRepository;

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

import javax.annotation.PostConstruct;
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

    private static final String DEFAULT_ENDERECO = "AAAAA";
    private static final String UPDATED_ENDERECO = "BBBBB";

    private static final Integer DEFAULT_CAPACIDADE = 1;
    private static final Integer UPDATED_CAPACIDADE = 2;

    @Inject
    private SalaRepository salaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalaMockMvc;

    private Sala sala;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalaResource salaResource = new SalaResource();
        ReflectionTestUtils.setField(salaResource, "salaRepository", salaRepository);
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
                .endereco(DEFAULT_ENDERECO)
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

        restSalaMockMvc.perform(post("/api/salas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sala)))
                .andExpect(status().isCreated());

        // Validate the Sala in the database
        List<Sala> salas = salaRepository.findAll();
        assertThat(salas).hasSize(databaseSizeBeforeCreate + 1);
        Sala testSala = salas.get(salas.size() - 1);
        assertThat(testSala.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testSala.getCapacidade()).isEqualTo(DEFAULT_CAPACIDADE);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaRepository.findAll().size();
        // set the field null
        sala.setEndereco(null);

        // Create the Sala, which fails.

        restSalaMockMvc.perform(post("/api/salas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sala)))
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

        restSalaMockMvc.perform(post("/api/salas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sala)))
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
                .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
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
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
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
                .endereco(UPDATED_ENDERECO)
                .capacidade(UPDATED_CAPACIDADE);

        restSalaMockMvc.perform(put("/api/salas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSala)))
                .andExpect(status().isOk());

        // Validate the Sala in the database
        List<Sala> salas = salaRepository.findAll();
        assertThat(salas).hasSize(databaseSizeBeforeUpdate);
        Sala testSala = salas.get(salas.size() - 1);
        assertThat(testSala.getEndereco()).isEqualTo(UPDATED_ENDERECO);
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

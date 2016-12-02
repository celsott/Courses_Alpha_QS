package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Calendario;
import com.qs.courses_alpha.repository.CalendarioRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CalendarioResource REST controller.
 *
 * @see CalendarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class CalendarioResourceIntTest {

    private static final String DEFAULT_EVENTO = "AAAAA";
    private static final String UPDATED_EVENTO = "BBBBB";

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private CalendarioRepository calendarioRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCalendarioMockMvc;

    private Calendario calendario;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalendarioResource calendarioResource = new CalendarioResource();
        ReflectionTestUtils.setField(calendarioResource, "calendarioRepository", calendarioRepository);
        this.restCalendarioMockMvc = MockMvcBuilders.standaloneSetup(calendarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendario createEntity(EntityManager em) {
        Calendario calendario = new Calendario()
                .evento(DEFAULT_EVENTO)
                .dataInicio(DEFAULT_DATA_INICIO)
                .dataFim(DEFAULT_DATA_FIM);
        return calendario;
    }

    @Before
    public void initTest() {
        calendario = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendario() throws Exception {
        int databaseSizeBeforeCreate = calendarioRepository.findAll().size();

        // Create the Calendario

        restCalendarioMockMvc.perform(post("/api/calendarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(calendario)))
                .andExpect(status().isCreated());

        // Validate the Calendario in the database
        List<Calendario> calendarios = calendarioRepository.findAll();
        assertThat(calendarios).hasSize(databaseSizeBeforeCreate + 1);
        Calendario testCalendario = calendarios.get(calendarios.size() - 1);
        assertThat(testCalendario.getEvento()).isEqualTo(DEFAULT_EVENTO);
        assertThat(testCalendario.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testCalendario.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
    }

    @Test
    @Transactional
    public void checkEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarioRepository.findAll().size();
        // set the field null
        calendario.setEvento(null);

        // Create the Calendario, which fails.

        restCalendarioMockMvc.perform(post("/api/calendarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(calendario)))
                .andExpect(status().isBadRequest());

        List<Calendario> calendarios = calendarioRepository.findAll();
        assertThat(calendarios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalendarios() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        // Get all the calendarios
        restCalendarioMockMvc.perform(get("/api/calendarios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(calendario.getId().intValue())))
                .andExpect(jsonPath("$.[*].evento").value(hasItem(DEFAULT_EVENTO.toString())))
                .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
                .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));
    }

    @Test
    @Transactional
    public void getCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        // Get the calendario
        restCalendarioMockMvc.perform(get("/api/calendarios/{id}", calendario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calendario.getId().intValue()))
            .andExpect(jsonPath("$.evento").value(DEFAULT_EVENTO.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalendario() throws Exception {
        // Get the calendario
        restCalendarioMockMvc.perform(get("/api/calendarios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);
        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();

        // Update the calendario
        Calendario updatedCalendario = calendarioRepository.findOne(calendario.getId());
        updatedCalendario
                .evento(UPDATED_EVENTO)
                .dataInicio(UPDATED_DATA_INICIO)
                .dataFim(UPDATED_DATA_FIM);

        restCalendarioMockMvc.perform(put("/api/calendarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCalendario)))
                .andExpect(status().isOk());

        // Validate the Calendario in the database
        List<Calendario> calendarios = calendarioRepository.findAll();
        assertThat(calendarios).hasSize(databaseSizeBeforeUpdate);
        Calendario testCalendario = calendarios.get(calendarios.size() - 1);
        assertThat(testCalendario.getEvento()).isEqualTo(UPDATED_EVENTO);
        assertThat(testCalendario.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testCalendario.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void deleteCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);
        int databaseSizeBeforeDelete = calendarioRepository.findAll().size();

        // Get the calendario
        restCalendarioMockMvc.perform(delete("/api/calendarios/{id}", calendario.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Calendario> calendarios = calendarioRepository.findAll();
        assertThat(calendarios).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Turma;
import com.qs.courses_alpha.domain.Professor;
import com.qs.courses_alpha.domain.Disciplina;
import com.qs.courses_alpha.repository.TurmaRepository;
import com.qs.courses_alpha.service.TurmaService;
import com.qs.courses_alpha.service.dto.TurmaDTO;
import com.qs.courses_alpha.service.mapper.TurmaMapper;

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

<<<<<<< HEAD
=======
import javax.annotation.PostConstruct;
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TurmaResource REST controller.
 *
 * @see TurmaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class TurmaResourceIntTest {

<<<<<<< HEAD
    private static final String DEFAULT_HORARIO = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO = "BBBBBBBBBB";
=======
    private static final String DEFAULT_HORARIO = "AAAAA";
    private static final String UPDATED_HORARIO = "BBBBB";
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7

    private static final Integer DEFAULT_PERIODO = 1;
    private static final Integer UPDATED_PERIODO = 2;

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    @Inject
    private TurmaRepository turmaRepository;

    @Inject
    private TurmaMapper turmaMapper;

    @Inject
    private TurmaService turmaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTurmaMockMvc;

    private Turma turma;

<<<<<<< HEAD
    @Before
=======
    @PostConstruct
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TurmaResource turmaResource = new TurmaResource();
        ReflectionTestUtils.setField(turmaResource, "turmaService", turmaService);
        this.restTurmaMockMvc = MockMvcBuilders.standaloneSetup(turmaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turma createEntity(EntityManager em) {
        Turma turma = new Turma()
                .horario(DEFAULT_HORARIO)
                .periodo(DEFAULT_PERIODO)
                .ano(DEFAULT_ANO);
        // Add required entity
        Professor professor = ProfessorResourceIntTest.createEntity(em);
        em.persist(professor);
        em.flush();
        turma.setProfessor(professor);
        // Add required entity
        Disciplina disciplina = DisciplinaResourceIntTest.createEntity(em);
        em.persist(disciplina);
        em.flush();
        turma.setDisciplina(disciplina);
        return turma;
    }

    @Before
    public void initTest() {
        turma = createEntity(em);
    }

    @Test
    @Transactional
    public void createTurma() throws Exception {
        int databaseSizeBeforeCreate = turmaRepository.findAll().size();

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.turmaToTurmaDTO(turma);

        restTurmaMockMvc.perform(post("/api/turmas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
                .andExpect(status().isCreated());

        // Validate the Turma in the database
        List<Turma> turmas = turmaRepository.findAll();
        assertThat(turmas).hasSize(databaseSizeBeforeCreate + 1);
        Turma testTurma = turmas.get(turmas.size() - 1);
        assertThat(testTurma.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testTurma.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testTurma.getAno()).isEqualTo(DEFAULT_ANO);
    }

    @Test
    @Transactional
    public void checkHorarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setHorario(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.turmaToTurmaDTO(turma);

        restTurmaMockMvc.perform(post("/api/turmas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
                .andExpect(status().isBadRequest());

        List<Turma> turmas = turmaRepository.findAll();
        assertThat(turmas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setPeriodo(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.turmaToTurmaDTO(turma);

        restTurmaMockMvc.perform(post("/api/turmas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
                .andExpect(status().isBadRequest());

        List<Turma> turmas = turmaRepository.findAll();
        assertThat(turmas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setAno(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.turmaToTurmaDTO(turma);

        restTurmaMockMvc.perform(post("/api/turmas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
                .andExpect(status().isBadRequest());

        List<Turma> turmas = turmaRepository.findAll();
        assertThat(turmas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTurmas() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmas
        restTurmaMockMvc.perform(get("/api/turmas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(turma.getId().intValue())))
                .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())))
                .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
                .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)));
    }

    @Test
    @Transactional
    public void getTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get the turma
        restTurmaMockMvc.perform(get("/api/turmas/{id}", turma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(turma.getId().intValue()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO));
    }

    @Test
    @Transactional
    public void getNonExistingTurma() throws Exception {
        // Get the turma
        restTurmaMockMvc.perform(get("/api/turmas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Update the turma
        Turma updatedTurma = turmaRepository.findOne(turma.getId());
        updatedTurma
                .horario(UPDATED_HORARIO)
                .periodo(UPDATED_PERIODO)
                .ano(UPDATED_ANO);
        TurmaDTO turmaDTO = turmaMapper.turmaToTurmaDTO(updatedTurma);

        restTurmaMockMvc.perform(put("/api/turmas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
                .andExpect(status().isOk());

        // Validate the Turma in the database
        List<Turma> turmas = turmaRepository.findAll();
        assertThat(turmas).hasSize(databaseSizeBeforeUpdate);
        Turma testTurma = turmas.get(turmas.size() - 1);
        assertThat(testTurma.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testTurma.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testTurma.getAno()).isEqualTo(UPDATED_ANO);
    }

    @Test
    @Transactional
    public void deleteTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);
        int databaseSizeBeforeDelete = turmaRepository.findAll().size();

        // Get the turma
        restTurmaMockMvc.perform(delete("/api/turmas/{id}", turma.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Turma> turmas = turmaRepository.findAll();
        assertThat(turmas).hasSize(databaseSizeBeforeDelete - 1);
    }
}

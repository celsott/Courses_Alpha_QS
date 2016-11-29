package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Disciplina;
import com.qs.courses_alpha.repository.DisciplinaRepository;
import com.qs.courses_alpha.service.DisciplinaService;
import com.qs.courses_alpha.service.dto.DisciplinaDTO;
import com.qs.courses_alpha.service.mapper.DisciplinaMapper;

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
 * Test class for the DisciplinaResource REST controller.
 *
 * @see DisciplinaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class DisciplinaResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDITOS = 0;
    private static final Integer UPDATED_CREDITOS = 1;

    private static final Integer DEFAULT_CARGA_HORARIA = 0;
    private static final Integer UPDATED_CARGA_HORARIA = 1;

    private static final String DEFAULT_EMENTA = "AAAAAAAAAA";
    private static final String UPDATED_EMENTA = "BBBBBBBBBB";

    @Inject
    private DisciplinaRepository disciplinaRepository;

    @Inject
    private DisciplinaMapper disciplinaMapper;

    @Inject
    private DisciplinaService disciplinaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDisciplinaMockMvc;

    private Disciplina disciplina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DisciplinaResource disciplinaResource = new DisciplinaResource();
        ReflectionTestUtils.setField(disciplinaResource, "disciplinaService", disciplinaService);
        this.restDisciplinaMockMvc = MockMvcBuilders.standaloneSetup(disciplinaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplina createEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
                .codigo(DEFAULT_CODIGO)
                .nome(DEFAULT_NOME)
                .creditos(DEFAULT_CREDITOS)
                .cargaHoraria(DEFAULT_CARGA_HORARIA)
                .ementa(DEFAULT_EMENTA);
        return disciplina;
    }

    @Before
    public void initTest() {
        disciplina = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisciplina() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina
        DisciplinaDTO disciplinaDTO = disciplinaMapper.disciplinaToDisciplinaDTO(disciplina);

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
                .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeCreate + 1);
        Disciplina testDisciplina = disciplinas.get(disciplinas.size() - 1);
        assertThat(testDisciplina.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testDisciplina.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDisciplina.getCreditos()).isEqualTo(DEFAULT_CREDITOS);
        assertThat(testDisciplina.getCargaHoraria()).isEqualTo(DEFAULT_CARGA_HORARIA);
        assertThat(testDisciplina.getEmenta()).isEqualTo(DEFAULT_EMENTA);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setCodigo(null);

        // Create the Disciplina, which fails.
        DisciplinaDTO disciplinaDTO = disciplinaMapper.disciplinaToDisciplinaDTO(disciplina);

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
                .andExpect(status().isBadRequest());

        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setNome(null);

        // Create the Disciplina, which fails.
        DisciplinaDTO disciplinaDTO = disciplinaMapper.disciplinaToDisciplinaDTO(disciplina);

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
                .andExpect(status().isBadRequest());

        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditosIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setCreditos(null);

        // Create the Disciplina, which fails.
        DisciplinaDTO disciplinaDTO = disciplinaMapper.disciplinaToDisciplinaDTO(disciplina);

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
                .andExpect(status().isBadRequest());

        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCargaHorariaIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setCargaHoraria(null);

        // Create the Disciplina, which fails.
        DisciplinaDTO disciplinaDTO = disciplinaMapper.disciplinaToDisciplinaDTO(disciplina);

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
                .andExpect(status().isBadRequest());

        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setEmenta(null);

        // Create the Disciplina, which fails.
        DisciplinaDTO disciplinaDTO = disciplinaMapper.disciplinaToDisciplinaDTO(disciplina);

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
                .andExpect(status().isBadRequest());

        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisciplinas() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinas
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId().intValue())))
                .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].creditos").value(hasItem(DEFAULT_CREDITOS)))
                .andExpect(jsonPath("$.[*].cargaHoraria").value(hasItem(DEFAULT_CARGA_HORARIA)))
                .andExpect(jsonPath("$.[*].ementa").value(hasItem(DEFAULT_EMENTA.toString())));
    }

    @Test
    @Transactional
    public void getDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disciplina.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.creditos").value(DEFAULT_CREDITOS))
            .andExpect(jsonPath("$.cargaHoraria").value(DEFAULT_CARGA_HORARIA))
            .andExpect(jsonPath("$.ementa").value(DEFAULT_EMENTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDisciplina() throws Exception {
        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Update the disciplina
        Disciplina updatedDisciplina = disciplinaRepository.findOne(disciplina.getId());
        updatedDisciplina
                .codigo(UPDATED_CODIGO)
                .nome(UPDATED_NOME)
                .creditos(UPDATED_CREDITOS)
                .cargaHoraria(UPDATED_CARGA_HORARIA)
                .ementa(UPDATED_EMENTA);
        DisciplinaDTO disciplinaDTO = disciplinaMapper.disciplinaToDisciplinaDTO(updatedDisciplina);

        restDisciplinaMockMvc.perform(put("/api/disciplinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
                .andExpect(status().isOk());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeUpdate);
        Disciplina testDisciplina = disciplinas.get(disciplinas.size() - 1);
        assertThat(testDisciplina.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testDisciplina.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDisciplina.getCreditos()).isEqualTo(UPDATED_CREDITOS);
        assertThat(testDisciplina.getCargaHoraria()).isEqualTo(UPDATED_CARGA_HORARIA);
        assertThat(testDisciplina.getEmenta()).isEqualTo(UPDATED_EMENTA);
    }

    @Test
    @Transactional
    public void deleteDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);
        int databaseSizeBeforeDelete = disciplinaRepository.findAll().size();

        // Get the disciplina
        restDisciplinaMockMvc.perform(delete("/api/disciplinas/{id}", disciplina.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        assertThat(disciplinas).hasSize(databaseSizeBeforeDelete - 1);
    }
}

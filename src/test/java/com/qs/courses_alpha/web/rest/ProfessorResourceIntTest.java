package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Professor;
import com.qs.courses_alpha.repository.ProfessorRepository;
import com.qs.courses_alpha.service.ProfessorService;
import com.qs.courses_alpha.service.dto.ProfessorDTO;
import com.qs.courses_alpha.service.mapper.ProfessorMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.qs.courses_alpha.domain.enumeration.Sexo;
/**
 * Test class for the ProfessorResource REST controller.
 *
 * @see ProfessorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class ProfessorResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Sexo DEFAULT_SEXO = Sexo.M;
    private static final Sexo UPDATED_SEXO = Sexo.F;

    @Inject
    private ProfessorRepository professorRepository;

    @Inject
    private ProfessorMapper professorMapper;

    @Inject
    private ProfessorService professorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProfessorMockMvc;

    private Professor professor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfessorResource professorResource = new ProfessorResource();
        ReflectionTestUtils.setField(professorResource, "professorService", professorService);
        this.restProfessorMockMvc = MockMvcBuilders.standaloneSetup(professorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professor createEntity(EntityManager em) {
        Professor professor = new Professor()
                .nome(DEFAULT_NOME)
                .sobrenome(DEFAULT_SOBRENOME)
                .cpf(DEFAULT_CPF)
                .dataNascimento(DEFAULT_DATA_NASCIMENTO)
                .sexo(DEFAULT_SEXO);
        return professor;
    }

    @Before
    public void initTest() {
        professor = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessor() throws Exception {
        int databaseSizeBeforeCreate = professorRepository.findAll().size();

        // Create the Professor
        ProfessorDTO professorDTO = professorMapper.professorToProfessorDTO(professor);

        restProfessorMockMvc.perform(post("/api/professors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorDTO)))
                .andExpect(status().isCreated());

        // Validate the Professor in the database
        List<Professor> professors = professorRepository.findAll();
        assertThat(professors).hasSize(databaseSizeBeforeCreate + 1);
        Professor testProfessor = professors.get(professors.size() - 1);
        assertThat(testProfessor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProfessor.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testProfessor.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testProfessor.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testProfessor.getSexo()).isEqualTo(DEFAULT_SEXO);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = professorRepository.findAll().size();
        // set the field null
        professor.setNome(null);

        // Create the Professor, which fails.
        ProfessorDTO professorDTO = professorMapper.professorToProfessorDTO(professor);

        restProfessorMockMvc.perform(post("/api/professors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorDTO)))
                .andExpect(status().isBadRequest());

        List<Professor> professors = professorRepository.findAll();
        assertThat(professors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSobrenomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = professorRepository.findAll().size();
        // set the field null
        professor.setSobrenome(null);

        // Create the Professor, which fails.
        ProfessorDTO professorDTO = professorMapper.professorToProfessorDTO(professor);

        restProfessorMockMvc.perform(post("/api/professors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorDTO)))
                .andExpect(status().isBadRequest());

        List<Professor> professors = professorRepository.findAll();
        assertThat(professors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = professorRepository.findAll().size();
        // set the field null
        professor.setCpf(null);

        // Create the Professor, which fails.
        ProfessorDTO professorDTO = professorMapper.professorToProfessorDTO(professor);

        restProfessorMockMvc.perform(post("/api/professors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorDTO)))
                .andExpect(status().isBadRequest());

        List<Professor> professors = professorRepository.findAll();
        assertThat(professors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = professorRepository.findAll().size();
        // set the field null
        professor.setDataNascimento(null);

        // Create the Professor, which fails.
        ProfessorDTO professorDTO = professorMapper.professorToProfessorDTO(professor);

        restProfessorMockMvc.perform(post("/api/professors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorDTO)))
                .andExpect(status().isBadRequest());

        List<Professor> professors = professorRepository.findAll();
        assertThat(professors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = professorRepository.findAll().size();
        // set the field null
        professor.setSexo(null);

        // Create the Professor, which fails.
        ProfessorDTO professorDTO = professorMapper.professorToProfessorDTO(professor);

        restProfessorMockMvc.perform(post("/api/professors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorDTO)))
                .andExpect(status().isBadRequest());

        List<Professor> professors = professorRepository.findAll();
        assertThat(professors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfessors() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professors
        restProfessorMockMvc.perform(get("/api/professors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(professor.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME.toString())))
                .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
                .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
                .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())));
    }

    @Test
    @Transactional
    public void getProfessor() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get the professor
        restProfessorMockMvc.perform(get("/api/professors/{id}", professor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(professor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfessor() throws Exception {
        // Get the professor
        restProfessorMockMvc.perform(get("/api/professors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessor() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);
        int databaseSizeBeforeUpdate = professorRepository.findAll().size();

        // Update the professor
        Professor updatedProfessor = professorRepository.findOne(professor.getId());
        updatedProfessor
                .nome(UPDATED_NOME)
                .sobrenome(UPDATED_SOBRENOME)
                .cpf(UPDATED_CPF)
                .dataNascimento(UPDATED_DATA_NASCIMENTO)
                .sexo(UPDATED_SEXO);
        ProfessorDTO professorDTO = professorMapper.professorToProfessorDTO(updatedProfessor);

        restProfessorMockMvc.perform(put("/api/professors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(professorDTO)))
                .andExpect(status().isOk());

        // Validate the Professor in the database
        List<Professor> professors = professorRepository.findAll();
        assertThat(professors).hasSize(databaseSizeBeforeUpdate);
        Professor testProfessor = professors.get(professors.size() - 1);
        assertThat(testProfessor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProfessor.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testProfessor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testProfessor.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testProfessor.getSexo()).isEqualTo(UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void deleteProfessor() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);
        int databaseSizeBeforeDelete = professorRepository.findAll().size();

        // Get the professor
        restProfessorMockMvc.perform(delete("/api/professors/{id}", professor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Professor> professors = professorRepository.findAll();
        assertThat(professors).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Aluno;
import com.qs.courses_alpha.repository.AlunoRepository;
import com.qs.courses_alpha.service.AlunoService;
import com.qs.courses_alpha.service.dto.AlunoDTO;
import com.qs.courses_alpha.service.mapper.AlunoMapper;

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
 * Test class for the AlunoResource REST controller.
 *
 * @see AlunoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class AlunoResourceIntTest {

    private static final String DEFAULT_DRE = "AAAAAAAAA";
    private static final String UPDATED_DRE = "BBBBBBBBB";

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
    private AlunoRepository alunoRepository;

    @Inject
    private AlunoMapper alunoMapper;

    @Inject
    private AlunoService alunoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlunoMockMvc;

    private Aluno aluno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlunoResource alunoResource = new AlunoResource();
        ReflectionTestUtils.setField(alunoResource, "alunoService", alunoService);
        this.restAlunoMockMvc = MockMvcBuilders.standaloneSetup(alunoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createEntity(EntityManager em) {
        Aluno aluno = new Aluno()
                .dre(DEFAULT_DRE)
                .nome(DEFAULT_NOME)
                .sobrenome(DEFAULT_SOBRENOME)
                .cpf(DEFAULT_CPF)
                .dataNascimento(DEFAULT_DATA_NASCIMENTO)
                .sexo(DEFAULT_SEXO);
        return aluno;
    }

    @Before
    public void initTest() {
        aluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAluno() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isCreated());

        // Validate the Aluno in the database
        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeCreate + 1);
        Aluno testAluno = alunos.get(alunos.size() - 1);
        assertThat(testAluno.getDre()).isEqualTo(DEFAULT_DRE);
        assertThat(testAluno.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAluno.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testAluno.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testAluno.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testAluno.getSexo()).isEqualTo(DEFAULT_SEXO);
    }

    @Test
    @Transactional
    public void checkDreIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setDre(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNome(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSobrenomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setSobrenome(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setCpf(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setDataNascimento(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setSexo(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isBadRequest());

        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlunos() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunos
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
                .andExpect(jsonPath("$.[*].dre").value(hasItem(DEFAULT_DRE.toString())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME.toString())))
                .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
                .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
                .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())));
    }

    @Test
    @Transactional
    public void getAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aluno.getId().intValue()))
            .andExpect(jsonPath("$.dre").value(DEFAULT_DRE.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAluno() throws Exception {
        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);
        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Update the aluno
        Aluno updatedAluno = alunoRepository.findOne(aluno.getId());
        updatedAluno
                .dre(UPDATED_DRE)
                .nome(UPDATED_NOME)
                .sobrenome(UPDATED_SOBRENOME)
                .cpf(UPDATED_CPF)
                .dataNascimento(UPDATED_DATA_NASCIMENTO)
                .sexo(UPDATED_SEXO);
        AlunoDTO alunoDTO = alunoMapper.alunoToAlunoDTO(updatedAluno);

        restAlunoMockMvc.perform(put("/api/alunos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
                .andExpect(status().isOk());

        // Validate the Aluno in the database
        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeUpdate);
        Aluno testAluno = alunos.get(alunos.size() - 1);
        assertThat(testAluno.getDre()).isEqualTo(UPDATED_DRE);
        assertThat(testAluno.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAluno.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testAluno.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testAluno.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testAluno.getSexo()).isEqualTo(UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void deleteAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);
        int databaseSizeBeforeDelete = alunoRepository.findAll().size();

        // Get the aluno
        restAlunoMockMvc.perform(delete("/api/alunos/{id}", aluno.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Aluno> alunos = alunoRepository.findAll();
        assertThat(alunos).hasSize(databaseSizeBeforeDelete - 1);
    }
}

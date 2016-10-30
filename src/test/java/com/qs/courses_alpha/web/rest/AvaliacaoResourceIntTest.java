package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Avaliacao;
import com.qs.courses_alpha.domain.Aluno;
import com.qs.courses_alpha.domain.Disciplina;
import com.qs.courses_alpha.repository.AvaliacaoRepository;

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
 * Test class for the AvaliacaoResource REST controller.
 *
 * @see AvaliacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class AvaliacaoResourceIntTest {

    private static final String DEFAULT_NOTA = "A";
    private static final String UPDATED_NOTA = "B";

    private static final Integer DEFAULT_FALTAS = 1;
    private static final Integer UPDATED_FALTAS = 2;

    @Inject
    private AvaliacaoRepository avaliacaoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAvaliacaoMockMvc;

    private Avaliacao avaliacao;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AvaliacaoResource avaliacaoResource = new AvaliacaoResource();
        ReflectionTestUtils.setField(avaliacaoResource, "avaliacaoRepository", avaliacaoRepository);
        this.restAvaliacaoMockMvc = MockMvcBuilders.standaloneSetup(avaliacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avaliacao createEntity(EntityManager em) {
        Avaliacao avaliacao = new Avaliacao()
                .nota(DEFAULT_NOTA)
                .faltas(DEFAULT_FALTAS);
        // Add required entity
        Aluno aluno = AlunoResourceIntTest.createEntity(em);
        em.persist(aluno);
        em.flush();
        avaliacao.getAlunos().add(aluno);
        // Add required entity
        Disciplina disciplina = DisciplinaResourceIntTest.createEntity(em);
        em.persist(disciplina);
        em.flush();
        avaliacao.setDisciplina(disciplina);
        return avaliacao;
    }

    @Before
    public void initTest() {
        avaliacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvaliacao() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoRepository.findAll().size();

        // Create the Avaliacao

        restAvaliacaoMockMvc.perform(post("/api/avaliacaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(avaliacao)))
                .andExpect(status().isCreated());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaos = avaliacaoRepository.findAll();
        assertThat(avaliacaos).hasSize(databaseSizeBeforeCreate + 1);
        Avaliacao testAvaliacao = avaliacaos.get(avaliacaos.size() - 1);
        assertThat(testAvaliacao.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testAvaliacao.getFaltas()).isEqualTo(DEFAULT_FALTAS);
    }

    @Test
    @Transactional
    public void checkNotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoRepository.findAll().size();
        // set the field null
        avaliacao.setNota(null);

        // Create the Avaliacao, which fails.

        restAvaliacaoMockMvc.perform(post("/api/avaliacaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(avaliacao)))
                .andExpect(status().isBadRequest());

        List<Avaliacao> avaliacaos = avaliacaoRepository.findAll();
        assertThat(avaliacaos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFaltasIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoRepository.findAll().size();
        // set the field null
        avaliacao.setFaltas(null);

        // Create the Avaliacao, which fails.

        restAvaliacaoMockMvc.perform(post("/api/avaliacaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(avaliacao)))
                .andExpect(status().isBadRequest());

        List<Avaliacao> avaliacaos = avaliacaoRepository.findAll();
        assertThat(avaliacaos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvaliacaos() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaos
        restAvaliacaoMockMvc.perform(get("/api/avaliacaos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacao.getId().intValue())))
                .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.toString())))
                .andExpect(jsonPath("$.[*].faltas").value(hasItem(DEFAULT_FALTAS)));
    }

    @Test
    @Transactional
    public void getAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get the avaliacao
        restAvaliacaoMockMvc.perform(get("/api/avaliacaos/{id}", avaliacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avaliacao.getId().intValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.toString()))
            .andExpect(jsonPath("$.faltas").value(DEFAULT_FALTAS));
    }

    @Test
    @Transactional
    public void getNonExistingAvaliacao() throws Exception {
        // Get the avaliacao
        restAvaliacaoMockMvc.perform(get("/api/avaliacaos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Update the avaliacao
        Avaliacao updatedAvaliacao = avaliacaoRepository.findOne(avaliacao.getId());
        updatedAvaliacao
                .nota(UPDATED_NOTA)
                .faltas(UPDATED_FALTAS);

        restAvaliacaoMockMvc.perform(put("/api/avaliacaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAvaliacao)))
                .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaos = avaliacaoRepository.findAll();
        assertThat(avaliacaos).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaos.get(avaliacaos.size() - 1);
        assertThat(testAvaliacao.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testAvaliacao.getFaltas()).isEqualTo(UPDATED_FALTAS);
    }

    @Test
    @Transactional
    public void deleteAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);
        int databaseSizeBeforeDelete = avaliacaoRepository.findAll().size();

        // Get the avaliacao
        restAvaliacaoMockMvc.perform(delete("/api/avaliacaos/{id}", avaliacao.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Avaliacao> avaliacaos = avaliacaoRepository.findAll();
        assertThat(avaliacaos).hasSize(databaseSizeBeforeDelete - 1);
    }
}

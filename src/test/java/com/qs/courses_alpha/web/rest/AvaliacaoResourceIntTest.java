package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Avaliacao;
import com.qs.courses_alpha.domain.Turma;
import com.qs.courses_alpha.domain.Aluno;
import com.qs.courses_alpha.repository.AvaliacaoRepository;
import com.qs.courses_alpha.service.AvaliacaoService;
import com.qs.courses_alpha.service.dto.AvaliacaoDTO;
import com.qs.courses_alpha.service.mapper.AvaliacaoMapper;

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

import com.qs.courses_alpha.domain.enumeration.Nota;
/**
 * Test class for the AvaliacaoResource REST controller.
 *
 * @see AvaliacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class AvaliacaoResourceIntTest {

    private static final Nota DEFAULT_NOTA = Nota.A;
    private static final Nota UPDATED_NOTA = Nota.B;

    private static final Float DEFAULT_FREQUENCIA = 0F;
    private static final Float UPDATED_FREQUENCIA = 1F;

    @Inject
    private AvaliacaoRepository avaliacaoRepository;

    @Inject
    private AvaliacaoMapper avaliacaoMapper;

    @Inject
    private AvaliacaoService avaliacaoService;

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
        ReflectionTestUtils.setField(avaliacaoResource, "avaliacaoService", avaliacaoService);
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
                .frequencia(DEFAULT_FREQUENCIA);
        // Add required entity
        Turma turma = TurmaResourceIntTest.createEntity(em);
        em.persist(turma);
        em.flush();
        avaliacao.setTurma(turma);
        // Add required entity
        Aluno aluno = AlunoResourceIntTest.createEntity(em);
        em.persist(aluno);
        em.flush();
        avaliacao.setAluno(aluno);
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
        AvaliacaoDTO avaliacaoDTO = avaliacaoMapper.avaliacaoToAvaliacaoDTO(avaliacao);

        restAvaliacaoMockMvc.perform(post("/api/avaliacaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(avaliacaoDTO)))
                .andExpect(status().isCreated());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaos = avaliacaoRepository.findAll();
        assertThat(avaliacaos).hasSize(databaseSizeBeforeCreate + 1);
        Avaliacao testAvaliacao = avaliacaos.get(avaliacaos.size() - 1);
        assertThat(testAvaliacao.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testAvaliacao.getFrequencia()).isEqualTo(DEFAULT_FREQUENCIA);
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
                .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA.doubleValue())));
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
            .andExpect(jsonPath("$.frequencia").value(DEFAULT_FREQUENCIA.doubleValue()));
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
                .frequencia(UPDATED_FREQUENCIA);
        AvaliacaoDTO avaliacaoDTO = avaliacaoMapper.avaliacaoToAvaliacaoDTO(updatedAvaliacao);

        restAvaliacaoMockMvc.perform(put("/api/avaliacaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(avaliacaoDTO)))
                .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaos = avaliacaoRepository.findAll();
        assertThat(avaliacaos).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaos.get(avaliacaos.size() - 1);
        assertThat(testAvaliacao.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testAvaliacao.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
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

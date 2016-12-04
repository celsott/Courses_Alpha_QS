package com.qs.courses_alpha.web.rest;

import com.qs.courses_alpha.CoursesAlphaQsApp;

import com.qs.courses_alpha.domain.Inscricao;
import com.qs.courses_alpha.domain.Turma;
import com.qs.courses_alpha.domain.Aluno;
import com.qs.courses_alpha.repository.InscricaoRepository;
import com.qs.courses_alpha.service.InscricaoService;
import com.qs.courses_alpha.service.dto.InscricaoDTO;
import com.qs.courses_alpha.service.mapper.InscricaoMapper;

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

import com.qs.courses_alpha.domain.enumeration.Situacao;
import com.qs.courses_alpha.domain.enumeration.Nota;
/**
 * Test class for the InscricaoResource REST controller.
 *
 * @see InscricaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoursesAlphaQsApp.class)
public class InscricaoResourceIntTest {

    private static final Situacao DEFAULT_SITUACAO = Situacao.A;
    private static final Situacao UPDATED_SITUACAO = Situacao.B;

    private static final Nota DEFAULT_NOTA = Nota.A;
    private static final Nota UPDATED_NOTA = Nota.B;

    private static final Float DEFAULT_FREQUENCIA = 0F;
    private static final Float UPDATED_FREQUENCIA = 1F;

    @Inject
    private InscricaoRepository inscricaoRepository;

    @Inject
    private InscricaoMapper inscricaoMapper;

    @Inject
    private InscricaoService inscricaoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInscricaoMockMvc;

    private Inscricao inscricao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InscricaoResource inscricaoResource = new InscricaoResource();
        ReflectionTestUtils.setField(inscricaoResource, "inscricaoService", inscricaoService);
        this.restInscricaoMockMvc = MockMvcBuilders.standaloneSetup(inscricaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscricao createEntity(EntityManager em) {
        Inscricao inscricao = new Inscricao()
                .situacao(DEFAULT_SITUACAO)
                .nota(DEFAULT_NOTA)
                .frequencia(DEFAULT_FREQUENCIA);
        // Add required entity
        Turma turma = TurmaResourceIntTest.createEntity(em);
        em.persist(turma);
        em.flush();
        inscricao.setTurma(turma);
        // Add required entity
        Aluno aluno = AlunoResourceIntTest.createEntity(em);
        em.persist(aluno);
        em.flush();
        inscricao.setAluno(aluno);
        return inscricao;
    }

    @Before
    public void initTest() {
        inscricao = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscricao() throws Exception {
        int databaseSizeBeforeCreate = inscricaoRepository.findAll().size();

        // Create the Inscricao
        InscricaoDTO inscricaoDTO = inscricaoMapper.inscricaoToInscricaoDTO(inscricao);

        restInscricaoMockMvc.perform(post("/api/inscricaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inscricaoDTO)))
                .andExpect(status().isCreated());

        // Validate the Inscricao in the database
        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        assertThat(inscricaos).hasSize(databaseSizeBeforeCreate + 1);
        Inscricao testInscricao = inscricaos.get(inscricaos.size() - 1);
        assertThat(testInscricao.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testInscricao.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testInscricao.getFrequencia()).isEqualTo(DEFAULT_FREQUENCIA);
    }

    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = inscricaoRepository.findAll().size();
        // set the field null
        inscricao.setSituacao(null);

        // Create the Inscricao, which fails.
        InscricaoDTO inscricaoDTO = inscricaoMapper.inscricaoToInscricaoDTO(inscricao);

        restInscricaoMockMvc.perform(post("/api/inscricaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inscricaoDTO)))
                .andExpect(status().isBadRequest());

        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        assertThat(inscricaos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInscricaos() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);

        // Get all the inscricaos
        restInscricaoMockMvc.perform(get("/api/inscricaos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(inscricao.getId().intValue())))
                .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
                .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.toString())))
                .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA.doubleValue())));
    }

    @Test
    @Transactional
    public void getInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);

        // Get the inscricao
        restInscricaoMockMvc.perform(get("/api/inscricaos/{id}", inscricao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inscricao.getId().intValue()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.toString()))
            .andExpect(jsonPath("$.frequencia").value(DEFAULT_FREQUENCIA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInscricao() throws Exception {
        // Get the inscricao
        restInscricaoMockMvc.perform(get("/api/inscricaos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);
        int databaseSizeBeforeUpdate = inscricaoRepository.findAll().size();

        // Update the inscricao
        Inscricao updatedInscricao = inscricaoRepository.findOne(inscricao.getId());
        updatedInscricao
                .situacao(UPDATED_SITUACAO)
                .nota(UPDATED_NOTA)
                .frequencia(UPDATED_FREQUENCIA);
        InscricaoDTO inscricaoDTO = inscricaoMapper.inscricaoToInscricaoDTO(updatedInscricao);

        restInscricaoMockMvc.perform(put("/api/inscricaos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inscricaoDTO)))
                .andExpect(status().isOk());

        // Validate the Inscricao in the database
        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        assertThat(inscricaos).hasSize(databaseSizeBeforeUpdate);
        Inscricao testInscricao = inscricaos.get(inscricaos.size() - 1);
        assertThat(testInscricao.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testInscricao.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testInscricao.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
    }

    @Test
    @Transactional
    public void deleteInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);
        int databaseSizeBeforeDelete = inscricaoRepository.findAll().size();

        // Get the inscricao
        restInscricaoMockMvc.perform(delete("/api/inscricaos/{id}", inscricao.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Inscricao> inscricaos = inscricaoRepository.findAll();
        assertThat(inscricaos).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.qs.courses_alpha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Avaliacao.
 */
@Entity
@Table(name = "avaliacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 1)
    @Pattern(regexp = "(^[A-D])")
    @Column(name = "nota", length = 1, nullable = false)
    private String nota;

    @NotNull
    @Column(name = "faltas", nullable = false)
    private Integer faltas;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "avaliacao_aluno",
               joinColumns = @JoinColumn(name="avaliacaos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="alunos_id", referencedColumnName="ID"))
    private Set<Aluno> alunos = new HashSet<>();

    @OneToMany(mappedBy = "avaliacao")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Disciplina> disciplinas = new HashSet<>();

    @ManyToOne
    @NotNull
    private Disciplina disciplina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public Avaliacao nota(String nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public Avaliacao faltas(Integer faltas) {
        this.faltas = faltas;
        return this;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public Avaliacao alunos(Set<Aluno> alunos) {
        this.alunos = alunos;
        return this;
    }

    public Avaliacao addAluno(Aluno aluno) {
        alunos.add(aluno);
        aluno.getAvaliacaos().add(this);
        return this;
    }

    public Avaliacao removeAluno(Aluno aluno) {
        alunos.remove(aluno);
        aluno.getAvaliacaos().remove(this);
        return this;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public Avaliacao disciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
        return this;
    }

    public Avaliacao addDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
        disciplina.setAvaliacao(this);
        return this;
    }

    public Avaliacao removeDisciplina(Disciplina disciplina) {
        disciplinas.remove(disciplina);
        disciplina.setAvaliacao(null);
        return this;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Avaliacao disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Avaliacao avaliacao = (Avaliacao) o;
        if(avaliacao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, avaliacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
            "id=" + id +
            ", nota='" + nota + "'" +
            ", faltas='" + faltas + "'" +
            '}';
    }
}

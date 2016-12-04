package com.qs.courses_alpha.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.qs.courses_alpha.domain.enumeration.Situacao;

import com.qs.courses_alpha.domain.enumeration.Nota;

/**
 * A Inscricao.
 */
@Entity
@Table(name = "inscricao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inscricao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private Situacao situacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "nota")
    private Nota nota;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    @Column(name = "frequencia")
    private Float frequencia;

    @ManyToOne
    @NotNull
    private Turma turma;

    @ManyToOne
    @NotNull
    private Aluno aluno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public Inscricao situacao(Situacao situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Nota getNota() {
        return nota;
    }

    public Inscricao nota(Nota nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Float getFrequencia() {
        return frequencia;
    }

    public Inscricao frequencia(Float frequencia) {
        this.frequencia = frequencia;
        return this;
    }

    public void setFrequencia(Float frequencia) {
        this.frequencia = frequencia;
    }

    public Turma getTurma() {
        return turma;
    }

    public Inscricao turma(Turma turma) {
        this.turma = turma;
        return this;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Inscricao aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inscricao inscricao = (Inscricao) o;
        if(inscricao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, inscricao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Inscricao{" +
            "id=" + id +
            ", situacao='" + situacao + "'" +
            ", nota='" + nota + "'" +
            ", frequencia='" + frequencia + "'" +
            '}';
    }
}

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
 * A Turma.
 */
@Entity
@Table(name = "turma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "horario", nullable = false)
    private String horario;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    @Column(name = "periodo", nullable = false)
    private Integer periodo;

    @NotNull
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @OneToMany(mappedBy = "turma")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscricao> inscricaos = new HashSet<>();

    @ManyToOne
    @NotNull
    private Professor professor;

    @ManyToOne
    @NotNull
    private Disciplina disciplina;

    @ManyToOne
    private Sala sala;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHorario() {
        return horario;
    }

    public Turma horario(String horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public Turma periodo(Integer periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getAno() {
        return ano;
    }

    public Turma ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Set<Inscricao> getInscricaos() {
        return inscricaos;
    }

    public Turma inscricaos(Set<Inscricao> inscricaos) {
        this.inscricaos = inscricaos;
        return this;
    }

    public Turma addInscricao(Inscricao inscricao) {
        inscricaos.add(inscricao);
        inscricao.setTurma(this);
        return this;
    }

    public Turma removeInscricao(Inscricao inscricao) {
        inscricaos.remove(inscricao);
        inscricao.setTurma(null);
        return this;
    }

    public void setInscricaos(Set<Inscricao> inscricaos) {
        this.inscricaos = inscricaos;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Turma professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Turma disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Sala getSala() {
        return sala;
    }

    public Turma sala(Sala sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Turma turma = (Turma) o;
        if(turma.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, turma.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Turma{" +
            "id=" + id +
            ", horario='" + horario + "'" +
            ", periodo='" + periodo + "'" +
            ", ano='" + ano + "'" +
            '}';
    }
}

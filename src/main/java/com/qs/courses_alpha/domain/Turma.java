package com.qs.courses_alpha.domain;

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
    @Column(name = "codigoturma", nullable = false)
    private String codigoturma;

    @NotNull
    @Column(name = "horario", nullable = false)
    private String horario;

    @ManyToOne
    @NotNull
    private Disciplina disciplina;

    @ManyToOne
    @NotNull
    private Professor professor;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "turma_aluno",
               joinColumns = @JoinColumn(name="turmas_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="alunos_id", referencedColumnName="ID"))
    private Set<Aluno> alunos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "turma_sala",
               joinColumns = @JoinColumn(name="turmas_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="salas_id", referencedColumnName="ID"))
    private Set<Sala> salas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoturma() {
        return codigoturma;
    }

    public Turma codigoturma(String codigoturma) {
        this.codigoturma = codigoturma;
        return this;
    }

    public void setCodigoturma(String codigoturma) {
        this.codigoturma = codigoturma;
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

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public Turma alunos(Set<Aluno> alunos) {
        this.alunos = alunos;
        return this;
    }

    public Turma addAluno(Aluno aluno) {
        alunos.add(aluno);
        aluno.getTurmas().add(this);
        return this;
    }

    public Turma removeAluno(Aluno aluno) {
        alunos.remove(aluno);
        aluno.getTurmas().remove(this);
        return this;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Set<Sala> getSalas() {
        return salas;
    }

    public Turma salas(Set<Sala> salas) {
        this.salas = salas;
        return this;
    }

    public Turma addSala(Sala sala) {
        salas.add(sala);
        sala.getTurmas().add(this);
        return this;
    }

    public Turma removeSala(Sala sala) {
        salas.remove(sala);
        sala.getTurmas().remove(this);
        return this;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
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
            ", codigoturma='" + codigoturma + "'" +
            ", horario='" + horario + "'" +
            '}';
    }
}

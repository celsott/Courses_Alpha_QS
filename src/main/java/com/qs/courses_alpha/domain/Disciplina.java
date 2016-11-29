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
 * A Disciplina.
 */
@Entity
@Table(name = "disciplina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Min(value = 0)
    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @NotNull
    @Min(value = 0)
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @NotNull
    @Column(name = "ementa", nullable = false)
    private String ementa;

    @OneToMany(mappedBy = "disciplina")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turma> turmas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Disciplina codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Disciplina nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public Disciplina creditos(Integer creditos) {
        this.creditos = creditos;
        return this;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public Disciplina cargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
        return this;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getEmenta() {
        return ementa;
    }

    public Disciplina ementa(String ementa) {
        this.ementa = ementa;
        return this;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public Disciplina turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public Disciplina addTurma(Turma turma) {
        turmas.add(turma);
        turma.setDisciplina(this);
        return this;
    }

    public Disciplina removeTurma(Turma turma) {
        turmas.remove(turma);
        turma.setDisciplina(null);
        return this;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disciplina disciplina = (Disciplina) o;
        if(disciplina.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, disciplina.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Disciplina{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", nome='" + nome + "'" +
            ", creditos='" + creditos + "'" +
            ", cargaHoraria='" + cargaHoraria + "'" +
            ", ementa='" + ementa + "'" +
            '}';
    }
}

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
 * A Sala.
 */
@Entity
@Table(name = "sala")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Min(value = 0)
    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;

    @OneToMany(mappedBy = "sala")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turma> turmas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Sala numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public Sala capacidade(Integer capacidade) {
        this.capacidade = capacidade;
        return this;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public Sala turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public Sala addTurma(Turma turma) {
        turmas.add(turma);
        turma.setSala(this);
        return this;
    }

    public Sala removeTurma(Turma turma) {
        turmas.remove(turma);
        turma.setSala(null);
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
        Sala sala = (Sala) o;
        if(sala.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sala.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sala{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", capacidade='" + capacidade + "'" +
            '}';
    }
}

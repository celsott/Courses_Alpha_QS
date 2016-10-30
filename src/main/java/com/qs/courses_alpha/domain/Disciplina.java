package com.qs.courses_alpha.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
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
    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @NotNull
    @Column(name = "cargahoraria", nullable = false)
    private Integer cargahoraria;

    @NotNull
    @Size(min = 4, max = 512)
    @Column(name = "ementa", length = 512, nullable = false)
    private String ementa;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

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

    public Integer getCargahoraria() {
        return cargahoraria;
    }

    public Disciplina cargahoraria(Integer cargahoraria) {
        this.cargahoraria = cargahoraria;
        return this;
    }

    public void setCargahoraria(Integer cargahoraria) {
        this.cargahoraria = cargahoraria;
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
            ", creditos='" + creditos + "'" +
            ", cargahoraria='" + cargahoraria + "'" +
            ", ementa='" + ementa + "'" +
            ", nome='" + nome + "'" +
            '}';
    }
}

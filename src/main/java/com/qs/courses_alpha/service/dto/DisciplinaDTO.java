package com.qs.courses_alpha.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Disciplina entity.
 */
public class DisciplinaDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigo;

    @NotNull
    private String nome;

    @NotNull
    @Min(value = 0)
    private Integer creditos;

    @NotNull
    @Min(value = 0)
    private Integer cargaHoraria;

    @NotNull
    private String ementa;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DisciplinaDTO disciplinaDTO = (DisciplinaDTO) o;

        if ( ! Objects.equals(id, disciplinaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DisciplinaDTO{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", nome='" + nome + "'" +
            ", creditos='" + creditos + "'" +
            ", cargaHoraria='" + cargaHoraria + "'" +
            ", ementa='" + ementa + "'" +
            '}';
    }
}

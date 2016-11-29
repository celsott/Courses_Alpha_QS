package com.qs.courses_alpha.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Sala entity.
 */
public class SalaDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    @NotNull
    @Min(value = 0)
    private Integer capacidade;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalaDTO salaDTO = (SalaDTO) o;

        if ( ! Objects.equals(id, salaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalaDTO{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", capacidade='" + capacidade + "'" +
            '}';
    }
}

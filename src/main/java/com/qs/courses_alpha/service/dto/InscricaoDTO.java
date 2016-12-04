package com.qs.courses_alpha.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.qs.courses_alpha.domain.enumeration.Situacao;
import com.qs.courses_alpha.domain.enumeration.Nota;

/**
 * A DTO for the Inscricao entity.
 */
public class InscricaoDTO implements Serializable {

    private Long id;

    @NotNull
    private Situacao situacao;

    private Nota nota;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private Float frequencia;


    private Long turmaId;
    
    private Long alunoId;
    

    private String alunoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }
    public Float getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Float frequencia) {
        this.frequencia = frequencia;
    }

    public Long getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Long turmaId) {
        this.turmaId = turmaId;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }


    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InscricaoDTO inscricaoDTO = (InscricaoDTO) o;

        if ( ! Objects.equals(id, inscricaoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InscricaoDTO{" +
            "id=" + id +
            ", situacao='" + situacao + "'" +
            ", nota='" + nota + "'" +
            ", frequencia='" + frequencia + "'" +
            '}';
    }
}

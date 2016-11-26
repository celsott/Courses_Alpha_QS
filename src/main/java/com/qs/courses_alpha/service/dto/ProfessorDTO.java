package com.qs.courses_alpha.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.qs.courses_alpha.domain.enumeration.Sexo;

/**
 * A DTO for the Professor entity.
 */
public class ProfessorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String sobrenome;

    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;

    @NotNull
    private Sexo sexo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessorDTO professorDTO = (ProfessorDTO) o;

        if ( ! Objects.equals(id, professorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProfessorDTO{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", sobrenome='" + sobrenome + "'" +
            ", cpf='" + cpf + "'" +
            ", dataNascimento='" + dataNascimento + "'" +
            ", sexo='" + sexo + "'" +
            '}';
    }
}

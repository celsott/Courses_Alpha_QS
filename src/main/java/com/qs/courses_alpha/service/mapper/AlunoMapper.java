package com.qs.courses_alpha.service.mapper;

import com.qs.courses_alpha.domain.*;
import com.qs.courses_alpha.service.dto.AlunoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Aluno and its DTO AlunoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlunoMapper {

    AlunoDTO alunoToAlunoDTO(Aluno aluno);

    List<AlunoDTO> alunosToAlunoDTOs(List<Aluno> alunos);

    @Mapping(target = "avaliacaos", ignore = true)
    Aluno alunoDTOToAluno(AlunoDTO alunoDTO);

    List<Aluno> alunoDTOsToAlunos(List<AlunoDTO> alunoDTOs);
}

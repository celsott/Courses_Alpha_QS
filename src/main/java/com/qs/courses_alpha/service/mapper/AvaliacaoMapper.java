package com.qs.courses_alpha.service.mapper;

import com.qs.courses_alpha.domain.*;
import com.qs.courses_alpha.service.dto.AvaliacaoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Avaliacao and its DTO AvaliacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AvaliacaoMapper {

    @Mapping(source = "turma.id", target = "turmaId")
    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.nome", target = "alunoNome")
    AvaliacaoDTO avaliacaoToAvaliacaoDTO(Avaliacao avaliacao);

    List<AvaliacaoDTO> avaliacaosToAvaliacaoDTOs(List<Avaliacao> avaliacaos);

    @Mapping(source = "turmaId", target = "turma")
    @Mapping(source = "alunoId", target = "aluno")
    Avaliacao avaliacaoDTOToAvaliacao(AvaliacaoDTO avaliacaoDTO);

    List<Avaliacao> avaliacaoDTOsToAvaliacaos(List<AvaliacaoDTO> avaliacaoDTOs);

    default Turma turmaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Turma turma = new Turma();
        turma.setId(id);
        return turma;
    }

    default Aluno alunoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Aluno aluno = new Aluno();
        aluno.setId(id);
        return aluno;
    }
}

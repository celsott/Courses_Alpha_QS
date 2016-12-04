package com.qs.courses_alpha.service.mapper;

import com.qs.courses_alpha.domain.*;
import com.qs.courses_alpha.service.dto.InscricaoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Inscricao and its DTO InscricaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InscricaoMapper {

    @Mapping(source = "turma.id", target = "turmaId")
    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.nome", target = "alunoNome")
    InscricaoDTO inscricaoToInscricaoDTO(Inscricao inscricao);

    List<InscricaoDTO> inscricaosToInscricaoDTOs(List<Inscricao> inscricaos);

    @Mapping(source = "turmaId", target = "turma")
    @Mapping(source = "alunoId", target = "aluno")
    Inscricao inscricaoDTOToInscricao(InscricaoDTO inscricaoDTO);

    List<Inscricao> inscricaoDTOsToInscricaos(List<InscricaoDTO> inscricaoDTOs);

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

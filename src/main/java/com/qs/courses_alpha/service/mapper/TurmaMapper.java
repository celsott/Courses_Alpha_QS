package com.qs.courses_alpha.service.mapper;

import com.qs.courses_alpha.domain.*;
import com.qs.courses_alpha.service.dto.TurmaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Turma and its DTO TurmaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TurmaMapper {

    @Mapping(source = "professor.id", target = "professorId")
    @Mapping(source = "professor.nome", target = "professorNome")
    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    @Mapping(source = "sala.id", target = "salaId")
    TurmaDTO turmaToTurmaDTO(Turma turma);

    List<TurmaDTO> turmasToTurmaDTOs(List<Turma> turmas);

    @Mapping(target = "avaliacaos", ignore = true)
    @Mapping(source = "professorId", target = "professor")
    @Mapping(source = "disciplinaId", target = "disciplina")
    @Mapping(source = "salaId", target = "sala")
    Turma turmaDTOToTurma(TurmaDTO turmaDTO);

    List<Turma> turmaDTOsToTurmas(List<TurmaDTO> turmaDTOs);

    default Professor professorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Professor professor = new Professor();
        professor.setId(id);
        return professor;
    }

    default Disciplina disciplinaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Disciplina disciplina = new Disciplina();
        disciplina.setId(id);
        return disciplina;
    }

    default Sala salaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Sala sala = new Sala();
        sala.setId(id);
        return sala;
    }
}

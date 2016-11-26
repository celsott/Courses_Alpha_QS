package com.qs.courses_alpha.service.mapper;

import com.qs.courses_alpha.domain.*;
import com.qs.courses_alpha.service.dto.DisciplinaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Disciplina and its DTO DisciplinaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DisciplinaMapper {

    DisciplinaDTO disciplinaToDisciplinaDTO(Disciplina disciplina);

    List<DisciplinaDTO> disciplinasToDisciplinaDTOs(List<Disciplina> disciplinas);

    @Mapping(target = "turmas", ignore = true)
    Disciplina disciplinaDTOToDisciplina(DisciplinaDTO disciplinaDTO);

    List<Disciplina> disciplinaDTOsToDisciplinas(List<DisciplinaDTO> disciplinaDTOs);
}

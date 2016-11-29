package com.qs.courses_alpha.service.mapper;

import com.qs.courses_alpha.domain.*;
import com.qs.courses_alpha.service.dto.ProfessorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Professor and its DTO ProfessorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfessorMapper {

    ProfessorDTO professorToProfessorDTO(Professor professor);

    List<ProfessorDTO> professorsToProfessorDTOs(List<Professor> professors);

    @Mapping(target = "turmas", ignore = true)
    Professor professorDTOToProfessor(ProfessorDTO professorDTO);

    List<Professor> professorDTOsToProfessors(List<ProfessorDTO> professorDTOs);
}

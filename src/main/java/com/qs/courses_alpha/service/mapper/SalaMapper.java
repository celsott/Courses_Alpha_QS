package com.qs.courses_alpha.service.mapper;

import com.qs.courses_alpha.domain.*;
import com.qs.courses_alpha.service.dto.SalaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Sala and its DTO SalaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalaMapper {

    SalaDTO salaToSalaDTO(Sala sala);

    List<SalaDTO> salasToSalaDTOs(List<Sala> salas);

    @Mapping(target = "turmas", ignore = true)
    Sala salaDTOToSala(SalaDTO salaDTO);

    List<Sala> salaDTOsToSalas(List<SalaDTO> salaDTOs);
}

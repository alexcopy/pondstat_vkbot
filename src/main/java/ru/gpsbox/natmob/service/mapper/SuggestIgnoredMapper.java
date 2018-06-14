package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.SuggestIgnoredDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SuggestIgnored and its DTO SuggestIgnoredDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SuggestIgnoredMapper extends EntityMapper<SuggestIgnoredDTO, SuggestIgnored> {


}

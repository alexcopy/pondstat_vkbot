package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.SuggestIgnoredDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SuggestIgnored and its DTO SuggestIgnoredDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SuggestIgnoredMapper extends EntityMapper<SuggestIgnoredDTO, SuggestIgnored> {


}

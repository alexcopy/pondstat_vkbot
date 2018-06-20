package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.SelectedKeyWordsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SelectedKeyWords and its DTO SelectedKeyWordsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SelectedKeyWordsMapper extends EntityMapper<SelectedKeyWordsDTO, SelectedKeyWords> {


}

package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.SelectedKeyWordsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SelectedKeyWords and its DTO SelectedKeyWordsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SelectedKeyWordsMapper extends EntityMapper<SelectedKeyWordsDTO, SelectedKeyWords> {


}

package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.TrainedModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TrainedModel and its DTO TrainedModelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrainedModelMapper extends EntityMapper<TrainedModelDTO, TrainedModel> {


}

package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.ModelTrainingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ModelTraining and its DTO ModelTrainingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModelTrainingMapper extends EntityMapper<ModelTrainingDTO, ModelTraining> {


}

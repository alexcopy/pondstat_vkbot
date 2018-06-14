package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.ModelTrainingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ModelTraining and its DTO ModelTrainingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModelTrainingMapper extends EntityMapper<ModelTrainingDTO, ModelTraining> {


}

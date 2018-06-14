package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.TrainedModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TrainedModel and its DTO TrainedModelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrainedModelMapper extends EntityMapper<TrainedModelDTO, TrainedModel> {


}

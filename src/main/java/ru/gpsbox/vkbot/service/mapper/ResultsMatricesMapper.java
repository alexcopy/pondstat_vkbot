package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.ResultsMatricesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ResultsMatrices and its DTO ResultsMatricesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResultsMatricesMapper extends EntityMapper<ResultsMatricesDTO, ResultsMatrices> {


}

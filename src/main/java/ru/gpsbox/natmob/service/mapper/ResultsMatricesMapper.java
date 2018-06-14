package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.ResultsMatricesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ResultsMatrices and its DTO ResultsMatricesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResultsMatricesMapper extends EntityMapper<ResultsMatricesDTO, ResultsMatrices> {


}

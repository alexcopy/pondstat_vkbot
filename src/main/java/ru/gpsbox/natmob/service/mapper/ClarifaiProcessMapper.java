package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.ClarifaiProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClarifaiProcess and its DTO ClarifaiProcessDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClarifaiProcessMapper extends EntityMapper<ClarifaiProcessDTO, ClarifaiProcess> {


}

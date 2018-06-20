package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.ClarifaiProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClarifaiProcess and its DTO ClarifaiProcessDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClarifaiProcessMapper extends EntityMapper<ClarifaiProcessDTO, ClarifaiProcess> {


}

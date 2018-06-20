package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.ClarifaisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Clarifais and its DTO ClarifaisDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClarifaisMapper extends EntityMapper<ClarifaisDTO, Clarifais> {


}

package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.ClarifaisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Clarifais and its DTO ClarifaisDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClarifaisMapper extends EntityMapper<ClarifaisDTO, Clarifais> {


}

package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.PictureRecognitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PictureRecognition and its DTO PictureRecognitionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PictureRecognitionMapper extends EntityMapper<PictureRecognitionDTO, PictureRecognition> {


}

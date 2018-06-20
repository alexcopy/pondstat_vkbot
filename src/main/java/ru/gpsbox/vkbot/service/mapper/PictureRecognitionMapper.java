package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.PictureRecognitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PictureRecognition and its DTO PictureRecognitionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PictureRecognitionMapper extends EntityMapper<PictureRecognitionDTO, PictureRecognition> {


}

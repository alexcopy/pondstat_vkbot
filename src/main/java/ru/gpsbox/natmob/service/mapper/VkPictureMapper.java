package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.VkPictureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VkPicture and its DTO VkPictureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VkPictureMapper extends EntityMapper<VkPictureDTO, VkPicture> {


}

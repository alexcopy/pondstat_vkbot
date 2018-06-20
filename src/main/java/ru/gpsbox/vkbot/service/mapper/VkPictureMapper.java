package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.VkPictureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VkPicture and its DTO VkPictureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VkPictureMapper extends EntityMapper<VkPictureDTO, VkPicture> {


}

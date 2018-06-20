package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.VkGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VkGroup and its DTO VkGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VkGroupMapper extends EntityMapper<VkGroupDTO, VkGroup> {


}

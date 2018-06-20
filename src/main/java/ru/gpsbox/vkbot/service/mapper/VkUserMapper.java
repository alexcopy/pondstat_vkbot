package ru.gpsbox.vkbot.service.mapper;

import ru.gpsbox.vkbot.domain.*;
import ru.gpsbox.vkbot.service.dto.VkUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VkUser and its DTO VkUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VkUserMapper extends EntityMapper<VkUserDTO, VkUser> {


}

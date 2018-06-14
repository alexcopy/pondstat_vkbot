package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.VkUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VkUser and its DTO VkUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VkUserMapper extends EntityMapper<VkUserDTO, VkUser> {


}

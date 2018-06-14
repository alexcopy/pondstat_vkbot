package ru.gpsbox.natmob.service.mapper;

import ru.gpsbox.natmob.domain.*;
import ru.gpsbox.natmob.service.dto.VkGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VkGroup and its DTO VkGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VkGroupMapper extends EntityMapper<VkGroupDTO, VkGroup> {


}

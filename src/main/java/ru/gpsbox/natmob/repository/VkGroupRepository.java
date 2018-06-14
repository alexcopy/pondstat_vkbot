package ru.gpsbox.natmob.repository;

import ru.gpsbox.natmob.domain.VkGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the VkGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VkGroupRepository extends MongoRepository<VkGroup, String> {

}

package ru.gpsbox.vkbot.repository;

import ru.gpsbox.vkbot.domain.VkUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the VkUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VkUserRepository extends MongoRepository<VkUser, String> {

}

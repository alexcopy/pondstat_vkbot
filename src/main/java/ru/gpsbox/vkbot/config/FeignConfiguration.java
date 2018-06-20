package ru.gpsbox.vkbot.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "ru.gpsbox.vkbot")
public class FeignConfiguration {

}

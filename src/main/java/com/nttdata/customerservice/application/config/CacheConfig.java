package com.nttdata.customerservice.application.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {
  public static final String CUSTOMER_CACHE = "customer-cache";

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
    redisCacheConfigurationMap.put(CUSTOMER_CACHE, createConfig(1, ChronoUnit.MINUTES));

    return RedisCacheManager
      .builder(redisConnectionFactory)
      .withInitialCacheConfigurations(redisCacheConfigurationMap)
      .build();
  }

  private static RedisCacheConfiguration createConfig(long time, ChronoUnit temporalUnit) {
    return RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(Duration.of(time, temporalUnit));
  }
}

package ru.mastkey.vkbackendtest.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Value("${cache.max-size}")
    private int maximumSize;

    @Value("${cache.life-time-in-minutes}")
    private int lifeTime;
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterAccess(lifeTime, TimeUnit.MINUTES)
                .initialCapacity(maximumSize);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeineConfig) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeineConfig);
        return caffeineCacheManager;
    }
}

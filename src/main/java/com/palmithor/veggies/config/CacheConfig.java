package com.palmithor.veggies.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configurations for development mode
 * <p>
 * Provides beans wih ConcurrentMapCacheManager
 *
 * @author palmithor
 * @since 8.2.2017.
 */
@Configuration
public class CacheConfig {

    private static final Logger log = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public CacheManager concurrentMapCacheManager() {
        log.debug("Cache manager is concurrentMapCacheManager");
        return new ConcurrentMapCacheManager(
                // MusicBrainzCacheService.CACHE_KEY,
        );
    }

}

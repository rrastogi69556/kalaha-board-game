package com.bol.interview.kalahaapi.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.hazelcast.client.config.ClientConfig;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("hazelcast:5701");
        return new HazelcastCacheManager(HazelcastClient
                .newHazelcastClient(clientConfig));
    }
}

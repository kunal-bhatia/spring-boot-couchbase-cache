package com.tutorial.couchbase.demo.config;

import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.java.diagnostics.WaitUntilReadyOptions;
import com.couchbase.client.java.manager.query.CreatePrimaryQueryIndexOptions;
import com.tutorial.couchbase.demo.handler.CouchbaseCacheErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;

import java.time.Duration;
import java.util.Set;

@Slf4j
@EnableCaching
@Configuration
@Profile("!test")
public class CacheConfig extends CachingConfigurerSupport {

    public final static String COUNTRIES = "countries";

    @Bean
    public CouchbaseCacheManager cacheManager(CouchbaseClientFactory couchbaseClientFactory, CouchbaseProperties couchbaseProperties) {
        final CouchbaseCacheManager cacheManager = CouchbaseCacheManager
                .builder(couchbaseClientFactory)
                .cacheDefaults(
                        CouchbaseCacheConfiguration
                                .defaultCacheConfig()
                                .entryExpiry(couchbaseProperties.getExpiryDuration()))
                .initialCacheNames(Set.of(COUNTRIES))
                .disableCreateOnMissingCache() // to reduce/visualize mistakes
                .build();
        cacheManager.setTransactionAware(false);
        cacheManager.afterPropertiesSet();

        log.info("Bucket name: {}", couchbaseClientFactory.getBucket().name());
        log.info("Pinging bucket: {}", couchbaseClientFactory.getBucket().ping());

        createIndex(couchbaseClientFactory);
        waitForKvReady(couchbaseClientFactory);

        log.info("Initialized Couchbase caches: {}", cacheManager.getCacheNames());

        return cacheManager;
    }

    private void waitForKvReady(CouchbaseClientFactory couchbaseClientFactory) {
        log.info("Wait for bucket to be ready for KV & QUERY operations ... this force detection of bad config at deploy time ...");
        couchbaseClientFactory.getBucket().waitUntilReady(
                Duration.ofSeconds(20),
                WaitUntilReadyOptions.waitUntilReadyOptions().serviceTypes(Set.of(ServiceType.KV, ServiceType.QUERY)));
        log.info("Bucket now ready for KV & QUERY operations: {} ", couchbaseClientFactory.getBucket().core().diagnostics().toArray());
        log.info("Cluster diagnostics: {} ", couchbaseClientFactory.getCluster().diagnostics());
    }

    private void createIndex(CouchbaseClientFactory couchbaseClientFactory) {
        log.info("Creating primary index if not already exists");
        couchbaseClientFactory
                .getCluster()
                .queryIndexes()
                .createPrimaryIndex(
                        couchbaseClientFactory.getBucket().name(),
                        CreatePrimaryQueryIndexOptions.createPrimaryQueryIndexOptions()
                                .ignoreIfExists(true));
    }

    @Override
    public CouchbaseCacheErrorHandler errorHandler() {
        return new CouchbaseCacheErrorHandler();
    }

}

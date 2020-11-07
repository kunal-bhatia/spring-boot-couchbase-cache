package com.tutorial.couchbase.demo.repository;

import com.tutorial.couchbase.demo.config.CacheConfig;
import com.tutorial.couchbase.demo.domain.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountryRepository {

    @Cacheable(value = CacheConfig.COUNTRIES, key = "#code")
    public Country findByCode(String code) {
        log.info("---> Loading country with code={}", code);
        return new Country(code);
    }

    @CacheEvict(value = CacheConfig.COUNTRIES, key = "#code")
    public int deleteCode(String code) {
        log.info("---> Deleting country with code={}", code);
        return 0;
    }
}

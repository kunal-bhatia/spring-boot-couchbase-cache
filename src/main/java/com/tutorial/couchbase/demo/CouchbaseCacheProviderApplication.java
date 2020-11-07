package com.tutorial.couchbase.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.getenv;

@Slf4j
@SpringBootApplication
public class CouchbaseCacheProviderApplication {

    public static void main(String[] args) {
        final String profiles = getenv("SPRING_PROFILES_ACTIVE");
        log.info("Running boot with SPRING_PROFILES_ACTIVE={}", profiles);
        final Runtime.Version version = Runtime.version();
        final int processors = Runtime.getRuntime().availableProcessors();
        final double maxMemory = Runtime.getRuntime().maxMemory() / (1_024.0 * 1_024.0 * 1_024.0);
        final String javaToolOptions = getenv("JAVA_TOOL_OPTIONS");
        final String springConfigLocation = getenv("SPRING_CONFIG_LOCATION");
        final long pid = ProcessHandle.current().pid();
        log.info("Some environment data: jvm={}, processors={}, max-memory={} GB, JAVA_TOOL_OPTIONS={}, SPRING_CONFIG_LOCATION={}, pid={}.",
                version,
                processors,
                String.format("%.2f", maxMemory),
                javaToolOptions,
                springConfigLocation,
                pid);
        SpringApplication.run(CouchbaseCacheProviderApplication.class, args);
    }
}


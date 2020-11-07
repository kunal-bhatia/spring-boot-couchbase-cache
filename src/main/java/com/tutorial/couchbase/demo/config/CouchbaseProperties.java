package com.tutorial.couchbase.demo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

/**
 * Magic via @{@link ConfigurationProperties} gives us validation.
 */
@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.data.couchbase")
public class CouchbaseProperties {

    @NotBlank
    private String connectionString;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String bucketName;
    @NotNull
    private Duration expiryDuration;

    @PostConstruct
    public void postConstruct() {
        log.info("Will use: {}", this);
    }

    @Override
    public String toString() {
        return "CouchbaseProperties{" +
                "connectionString='" + connectionString + '\'' +
                ", userName='" + userName + '\'' +
                ", password='****'" +
                ", bucketName='" + bucketName + '\'' +
                ", expiryDuration=" + expiryDuration +
                '}';
    }
}

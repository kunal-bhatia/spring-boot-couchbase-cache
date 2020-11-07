package com.tutorial.couchbase.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
@Import(CouchbaseProperties.class)
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {
    private final CouchbaseProperties properties;

    public CouchbaseConfig(CouchbaseProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getConnectionString() {
        return properties.getConnectionString();
    }

    @Override
    public String getUserName() {
        return properties.getUserName();
    }

    @Override
    public String getPassword() {
        return properties.getPassword();
    }

    @Override
    public String getBucketName() {
        return properties.getBucketName();
    }
}

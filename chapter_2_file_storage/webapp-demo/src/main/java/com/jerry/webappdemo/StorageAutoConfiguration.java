package com.jerry.webappdemo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {

    @Bean
    public StoregeService storegeService(StorageProperties properties) {
        boolean localConfigured = properties.getLocal().isConfigured();
        boolean seaweedConfigured = properties.getSeaweedfs().isConfigured();
        boolean aliyunConfigured = properties.getAliyun().isConfigured();

        List<String> configured = new ArrayList<>();
        if (localConfigured) {
            configured.add("local");
        }
        if (seaweedConfigured) {
            configured.add("seaweedfs");
        }
        if (aliyunConfigured) {
            configured.add("aliyun");
        }

        if (configured.size() > 1) {
            throw new IllegalStateException("Only one storage implementation can be configured, but found: " + configured);
        }

        if (configured.isEmpty()) {
            String root = System.getProperty("user.dir");
            return new LocalFileService(root);
        }

        if (seaweedConfigured) {
            return new SeaweedFSService(properties.getSeaweedfs().getDomain());
        }

        if (aliyunConfigured) {
            throw new IllegalStateException("Aliyun storage is configured but no implementation exists in this project yet.");
        }

        return new LocalFileService(properties.getLocal().getRoot());
    }
}

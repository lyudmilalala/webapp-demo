package com.jerry.webappdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

    private final Local local = new Local();
    private final Seaweedfs seaweedfs = new Seaweedfs();
    private final Aliyun aliyun = new Aliyun();
    
    public Local getLocal() {
        return local;
    }

    public Seaweedfs getSeaweedfs() {
        return seaweedfs;
    }

    public Aliyun getAliyun() {
        return aliyun;
    }

    public static class Local {
        private String root;

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }

        public boolean isConfigured() {
            return root != null && !root.trim().isEmpty();
        }
    }

    public static class Seaweedfs {
        private String domain;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public boolean isConfigured() {
            return domain != null && !domain.trim().isEmpty();
        }
    }

    public static class Aliyun {
        private String accessKeyId;
        private String accessKeySecret;
        private String region;
        private String bucket;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public boolean isConfigured() {
            return notBlank(accessKeyId)
                    && notBlank(accessKeySecret)
                    && notBlank(region)
                    && notBlank(bucket);
        }

        private boolean notBlank(String s) {
            return s != null && !s.trim().isEmpty();
        }
    }
}

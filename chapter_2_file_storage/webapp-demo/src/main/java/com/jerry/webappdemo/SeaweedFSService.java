package com.jerry.webappdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class SeaweedFSService implements StoregeService {
    private static String FILER_SERVER;
    @Value("${storage.seaweedfs.domain}")
    public void setFilerServer(String root_dir) {
        FILER_SERVER = root_dir;
    }

    @Override
    public void save(String prefix, String filename, byte[] content) {
            // 模拟mockRes(bitNum)逻辑，将content转换为需要存储的数据结构
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String fileUrl = "http://" + FILER_SERVER + "/" + prefix + "/" + filename;
                log.info("fileUrl = {}", fileUrl);

                HttpPost post = new HttpPost(fileUrl);
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addBinaryBody("file", new ByteArrayInputStream(content),
                                ContentType.APPLICATION_OCTET_STREAM, filename)
                        .build();
                post.setEntity(entity);
                post.setHeader("accept", "application/json");

                try (CloseableHttpResponse response = httpClient.execute(post)) {
                    String responseString = EntityUtils.toString(response.getEntity());
                    log.info("responseString = {}", responseString);
                }
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
    }

    @Override
    public byte[] load(String prefix, String filename) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String fileUrl = "http://" + FILER_SERVER + "/" + prefix + "/" + filename;
            log.info("fileUrl = {}", fileUrl);
            HttpGet get = new HttpGet(fileUrl);
            try (CloseableHttpResponse response = httpClient.execute(get)) {
                return EntityUtils.toByteArray(response.getEntity());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}

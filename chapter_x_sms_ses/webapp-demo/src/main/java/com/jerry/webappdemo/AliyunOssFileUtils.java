package com.jerry.webappdemo;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

@Slf4j
@Service
public class AliyunOssFileUtils {
    /**
     * @description TODO
     * @author Yuchen Sun
     * @createDate 2022-04-26
     */

    private static String ALIYUN_OSS_ACCESS_KEY = "my_acess_key";
    private static String ALIYUN_OSS_SECRET_KEY = "my_acess_secret";
    OSS ossClient = null;

    public AliyunOssFileUtils() {}

    @PostConstruct
    public void initAuthAndManager() {
        DefaultCredentialProvider provider = new DefaultCredentialProvider(ALIYUN_OSS_ACCESS_KEY, ALIYUN_OSS_SECRET_KEY);
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        ossClient = OSSClientBuilder.create()
                .endpoint("oss-cn-shenzhen.aliyuncs.com")
                .credentialsProvider(provider)
                .clientConfiguration(clientBuilderConfiguration)
                .region("cn-shenzhen")
                .build();
    }

    public String createUploadToken(String bucketName, String filepath, Long timeout) {
        Date expiration = new Date(new Date().getTime() + timeout * 1000L);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(
                bucketName, filepath, HttpMethod.PUT);
        request.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(request);
        return url.toString();
    }

    public InputStream downloadFileStream(String bucketName, String filepath) {
        OSSObject ossObject = ossClient.getObject(bucketName, filepath);
        return ossObject.getObjectContent();
    }

    public String generateDownloadLink(String bucketName, String filepath, Long timeout) throws OSSException, ClientException, UnsupportedEncodingException {
        // timeout in seconds
        Date expiration = new Date(new Date().getTime() + timeout * 1000L);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(
                bucketName, filepath, HttpMethod.GET);
        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
        request.getResponseHeaders().setContentDisposition("attachment; filename=" + URLEncoder.encode(filename,"UTF-8"));
        request.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(request);
        return url.toString();
    }
}

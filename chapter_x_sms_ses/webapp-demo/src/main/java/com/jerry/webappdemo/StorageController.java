package com.jerry.webappdemo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping(value="/aliyunOss")
public class StorageController {

    @Autowired
    private AliyunOssFileUtils aliyunOssFileUtils;

    @RequestMapping(value="/getUploadUrl", method= RequestMethod.GET)
    public ResponseEntity<CommonEntityResponse<String>> getUploadUrl(@RequestHeader("token") String token,
                                                       @RequestParam("bucket") String bucket,
                                                       @RequestParam("filepath") String filepath,
                                                       @RequestParam(value = "timeout", required = false) Long timeout) {
        if (!token.equals("spinq0827")) {
            return new ResponseEntity<>(new CommonEntityResponse<>(HttpStatus.UNAUTHORIZED.value(), "Invalid token"), HttpStatus.UNAUTHORIZED);
        }
        String uploadUrl = aliyunOssFileUtils.createUploadToken(bucket, filepath, timeout);
        return new ResponseEntity<>(new CommonEntityResponse<>(uploadUrl), HttpStatus.OK);
    }

    @RequestMapping(value="/getDownloadUrl", method= RequestMethod.GET)
    public ResponseEntity<CommonEntityResponse<String>> getDownloadUrl(@RequestParam("bucket") String bucket,
                                                                       @RequestParam("filepath") String filepath,
                                                                       @RequestParam(value = "timeout", required = false) Long timeout) throws UnsupportedEncodingException {
        String downloadUrl = aliyunOssFileUtils.generateDownloadLink(bucket, filepath, timeout);
        return new ResponseEntity<>(new CommonEntityResponse<>(downloadUrl), HttpStatus.OK);
    }

}

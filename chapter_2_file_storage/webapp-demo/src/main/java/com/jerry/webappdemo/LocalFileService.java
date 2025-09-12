package com.jerry.webappdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class LocalFileService implements StoregeService {

    private static String ROOT_DIR;
    @Value("${storage.local.root}")
    public void setRootDir(String root_dir) {
        ROOT_DIR = root_dir;
    }


    @Override
    public void save(String prefix, String filename, byte[] content) {
        BufferedOutputStream outputStream = null;
        try {
            String filepath = ROOT_DIR + "/" + prefix + "/" + filename;
            log.info("filePath = {}", filepath);
            File file = new File(filepath);
            if (!file.exists()) {
                log.info("File " + filepath + " does not exist");
            } else {
                log.info("File " + filepath + " exists");
            }
            outputStream = new BufferedOutputStream(new FileOutputStream(filepath));
            outputStream.write(content);
            outputStream.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public byte[] load(String prefix, String filename) {
        try {
            String filepath = ROOT_DIR + "/" + prefix + "/" + filename;
            log.info("filePath = {}", filepath);
            File f = new File(filepath);
            if (!f.exists()) {
                log.error("No file.");
            } else {
                FileInputStream fs = new FileInputStream(f);
                FileChannel channel = fs.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
                while ((channel.read(byteBuffer)) > 0) {
                    // do nothing
                    // System.out.println("reading");
                }
                return byteBuffer.array();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}

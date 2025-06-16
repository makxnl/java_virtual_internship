package org.javaguru.travel.insurance;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonFileReader {

    public void readJsonFromFile(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        InputStream inputStream = resource.getInputStream();
        String content = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println(content);
    }

//    public void readJsonFromFile(String filePath) {
//        try {
//            File file = ResourceUtils.getFile("classpath:" + filePath);
////            return new String(Files.readAllBytes(file.toPath()));
//            System.out.println(new String(Files.readAllBytes(file.toPath())));
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }

}
package com.godov.crudskyweaver.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@AllArgsConstructor
public class ResourseUtils {
    private ObjectMapper objectMapper;

    public String getJsonFromResources(String resourcesPath, Class<?> targetClass) {
        InputStream resourceAsStream = getInputStreamFromResources(resourcesPath);
        try {
            Object reportInstance = objectMapper.readValue(resourceAsStream, targetClass);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(reportInstance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public InputStream getInputStreamFromResources(String resourcesPath) {
        return this.getClass().getClassLoader().getResourceAsStream(resourcesPath);
    }
}

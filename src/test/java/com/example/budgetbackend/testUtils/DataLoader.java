package com.example.budgetbackend.testUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataLoader {
    public static <T> List<T> loadMockData(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try (InputStream jsonStream = DataLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (jsonStream == null) {
                throw new IllegalArgumentException(filePath + " not found");
            }
            return objectMapper.readValue(jsonStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        }
    }
}

package com.glara.infrastructure.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SQLFileReader {

    private static final String BASE_PATH = "sql/";

    private SQLFileReader() {
    }

    public static String readSQL(String folder, String fileName) {
        String filePath = BASE_PATH + folder + "/" + fileName;
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Archivo SQL no encontrado: " + filePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer archivo SQL: " + filePath, e);
        }
    }
}

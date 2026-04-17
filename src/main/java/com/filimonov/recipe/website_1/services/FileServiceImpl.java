package com.filimonov.recipe.website_1.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Value("${data.package.path}")
    private String dataPath;

    @Override
    public boolean saveToFile(String json, String fileName) {
        try {
            Path path = Path.of(dataPath, fileName);
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            Files.writeString(path, json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile(String fileName) {
        Path path = Path.of(dataPath, fileName);

        if (!Files.exists(path)) {
            return null;
        }
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

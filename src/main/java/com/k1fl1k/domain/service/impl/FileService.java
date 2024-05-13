package com.k1fl1k.domain.service.impl;

import com.k1fl1k.domain.exception.ImageNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    public Path getPathFromResource(String resourceName) {
        URL resourceUrl = getClass().getClassLoader().getResource(resourceName);
        if (resourceUrl == null) {
            throw new IllegalArgumentException(STR."Resource not found: \{resourceName}");
        }

        try {
            return Paths.get(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(STR."Invalid URI syntax: \{resourceUrl}", e);
        }
    }

    public byte[] getBytes(Path path) {
        byte[] fileBytes = null;
        try {
            if (Objects.nonNull(path)) {
                fileBytes = Files.readAllBytes(path);
            }
        } catch (IOException e) {
            throw new ImageNotFoundException();
        }
        return fileBytes;
    }
}

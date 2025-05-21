package com.example.csvtoapi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.csvtoapi.util.CSVUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CSVService {

    private final InMemoryCSVStorage storage;
    private final S3Service s3Service;

    public void processCSV(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();

        if (originalName == null || !originalName.endsWith(".csv")) {
            throw new IllegalArgumentException("Invalid or missing CSV filename.");
        }

        String baseName = originalName.replace(".csv", "");

        s3Service.uploadFile(originalName, file);

        List<Map<String, String>> records = CSVUtils.parse(file.getInputStream());
        storage.save(baseName, records);
    }
}

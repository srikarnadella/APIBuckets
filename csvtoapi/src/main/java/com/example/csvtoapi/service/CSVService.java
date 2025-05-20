package com.example.csvtoapi.service;

import com.example.csvtoapi.util.CSVUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

        // Use original name directly for both S3 key and in-memory map
        String baseName = originalName.replace(".csv", "");

        // Upload to S3 with original filename
        s3Service.uploadFile(originalName, file);

        // Parse and store in memory using baseName
        List<Map<String, String>> records = CSVUtils.parse(file.getInputStream());
        storage.save(baseName, records);
    }
}

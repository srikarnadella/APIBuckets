package com.example.csvtoapi.service;

import com.example.csvtoapi.util.CSVUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StartupLoader implements CommandLineRunner {

    private final S3Service s3Service;
    private final InMemoryCSVStorage storage;

    @Override
    public void run(String... args) {
        List<String> keys = s3Service.listAllCsvKeys();
        for (String key : keys) {
            try (InputStream inputStream = s3Service.downloadFile(key)) {
                String baseName = key.replace(".csv", "").replaceAll("^\\d+_", "");
                List<Map<String, String>> records = CSVUtils.parse(inputStream);
                storage.save(baseName, records);
                System.out.println("Loaded " + baseName + " from S3 (" + records.size() + " rows)");
            } catch (Exception e) {
                System.err.println("Failed to load " + key + ": " + e.getMessage());
            }
        }
    }
}

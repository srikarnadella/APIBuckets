package com.example.csvtoapi.controller;

import com.example.csvtoapi.service.InMemoryCSVStorage;
import com.example.csvtoapi.util.CSVUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DynamicDataController {

    private final InMemoryCSVStorage storage;

    @GetMapping("/{filename}")
    public List<Map<String, String>> getAllRows(
            @PathVariable String filename,
            @RequestParam Map<String, String> filters
    ) {
        System.out.println("=== Incoming Filters ===");
        filters.forEach((k, v) -> System.out.println("Column: " + k + ", Value: " + v));
        List<Map<String, String>> data = storage.getAll(filename);
        if (!data.isEmpty()) {
            System.out.println("=== Actual CSV Column Keys ===");
            data.get(0).keySet().forEach(System.out::println);
        }

        if (filters.containsKey("column") && filters.containsKey("value")) {
            String column = filters.get("column").toLowerCase();
            String rawValue = filters.get("value");
            Map<String, String> customFilter = Map.of(column, rawValue);
            return storage.filterByColumns(filename, customFilter);
        } else if (!filters.isEmpty()) {
            Map<String, String> normalized = new HashMap<>();
            filters.forEach((k, v) -> normalized.put(k.toLowerCase(), v));
            return storage.filterByColumns(filename, normalized);
        } else {
            return storage.getAll(filename);
        }

    }
    @GetMapping("/{filename}/columns")
    public Set<String> getColumnHeaders(@PathVariable String filename) {
        List<Map<String, String>> rows = storage.getAll(filename);
        if (rows.isEmpty()) return Collections.emptySet();
        return rows.get(0).keySet();
    }

    @GetMapping(value = "/{filename}/export", produces = "text/csv")
    public void exportFilteredCSV(
            @PathVariable String filename,
            @RequestParam Map<String, String> filters,
            HttpServletResponse response
    ) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "_filtered.csv\"");
        List<Map<String, String>> filtered = getAllRows(filename, filters);
        CSVUtils.writeCSV(response.getWriter(), filtered);
    }





    @GetMapping("/{filename}/{index}")
    public Map<String, String> getRow(@PathVariable String filename, @PathVariable int index) {
        return storage.getByIndex(filename, index);
    }

    @GetMapping
    public Set<String> listUploadedFiles() {
        return storage.getLoadedFilenames();
    }

    
}

package com.example.csvtoapi.controller;

import com.example.csvtoapi.service.InMemoryCSVStorage;
import com.example.csvtoapi.util.CSVUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.util.*;

@Tag(name = "Dynamic Data API", description = "Endpoints for interacting with uploaded CSV datasets")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DynamicDataController {

    private final InMemoryCSVStorage storage;

    @Operation(
            summary = "List all available uploaded datasets",
            description = "Returns a list of filenames corresponding to currently loaded CSV datasets."
    )
    @GetMapping
    public Set<String> listUploadedFiles() {
        return storage.getLoadedFilenames();
    }

    @Operation(
            summary = "Get all rows from a dataset",
            description = """
            Retrieves all rows from the specified dataset.
            You can also apply filters using query parameters.

            **Filtering Examples**:
            - `?genre=dance` — Exact match for genre = "dance"
            - `?bpm=>120` — Numeric filter for bpm > 120
            - `?bpm=120-130` — Range filter between 120 and 130 (inclusive)
            - `?vibe_score<=1.5` — Filter by vibe_score ≤ 1.5

            Note:
            - Filtering keys are case-insensitive.
            - If both `column` and `value` are provided, they are treated as a single filter.
            - You can also supply multiple column-based filters directly as query params.
            """
    )
    @GetMapping("/{filename}")
    public List<Map<String, String>> getAllRows(
            @Parameter(description = "Name of the uploaded CSV file (without extension)")
            @PathVariable String filename,

            @Parameter(description = "Optional filters for columns (e.g. ?genre=dance&bpm=>120)")
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

    @Operation(
            summary = "Get column names for a dataset",
            description = "Returns the list of column headers for the specified dataset."
    )
    @GetMapping("/{filename}/columns")
    public Set<String> getColumnHeaders(
            @Parameter(description = "Name of the uploaded CSV file (without extension)")
            @PathVariable String filename
    ) {
        List<Map<String, String>> rows = storage.getAll(filename);
        if (rows.isEmpty()) return Collections.emptySet();
        return rows.get(0).keySet();
    }

    @Operation(
            summary = "Export filtered dataset as CSV",
            description = """
            Exports the specified dataset as a downloadable CSV file.
            You can apply filters to restrict the rows included.

            Example filters:
            - `?genre=house`
            - `?bpm=120-128`
            - `?vibe_score>=1.2`

            The returned CSV will be named: `{filename}_filtered.csv`
            """
    )
    @GetMapping(value = "/{filename}/export", produces = "text/csv")
    public void exportFilteredCSV(
            @Parameter(description = "Name of the uploaded CSV file (without extension)")
            @PathVariable String filename,

            @Parameter(description = "Optional filters to export only matching rows")
            @RequestParam Map<String, String> filters,

            HttpServletResponse response
    ) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "_filtered.csv\"");
        List<Map<String, String>> filtered = getAllRows(filename, filters);
        CSVUtils.writeCSV(response.getWriter(), filtered);
    }

    @Operation(
            summary = "Get a specific row by index",
            description = "Returns a single row from the dataset at the specified index (0-based)."
    )
    @GetMapping("/{filename}/{index}")
    public Map<String, String> getRow(
            @Parameter(description = "Name of the uploaded CSV file (without extension)")
            @PathVariable String filename,

            @Parameter(description = "Zero-based index of the row to retrieve")
            @PathVariable int index
    ) {
        return storage.getByIndex(filename, index);
    }
}

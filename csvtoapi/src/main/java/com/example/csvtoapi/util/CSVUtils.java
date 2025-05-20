package com.example.csvtoapi.util;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CSVUtils {

    public static List<Map<String, String>> parse(InputStream inputStream) throws IOException {
        List<Map<String, String>> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String[] headers = reader.readLine().split(",");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i].trim().toLowerCase(), i < values.length ? values[i].trim() : null);
                }
                result.add(row);
            }
        }
        return result;
    }

    public static void writeCSV(Writer writer, List<Map<String, String>> rows) throws IOException {
        if (rows.isEmpty()) return;

        BufferedWriter bw = new BufferedWriter(writer);
        Set<String> headers = rows.get(0).keySet();
        bw.write(String.join(",", headers));
        bw.newLine();

        for (Map<String, String> row : rows) {
            bw.write(headers.stream().map(h -> row.getOrDefault(h, "")).collect(Collectors.joining(",")));
            bw.newLine();
        }

        bw.flush();
    }
}

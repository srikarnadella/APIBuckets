package com.example.csvtoapi.service;

import com.example.csvtoapi.model.FilterCondition;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InMemoryCSVStorage {

    // filename → List of rows (row = Map<column, value>)
    private final Map<String, List<Map<String, String>>> storage = new HashMap<>();

    public void save(String filename, List<Map<String, String>> data) {
        storage.put(filename, data);
    }

    public List<Map<String, String>> getAll(String filename) {
        return storage.getOrDefault(filename, new ArrayList<>());
    }

    public Map<String, String> getByIndex(String filename, int index) {
        List<Map<String, String>> rows = storage.get(filename);
        if (rows == null || index < 0 || index >= rows.size()) {
            throw new IndexOutOfBoundsException("Invalid row index");
        }
        return rows.get(index);
    }

    public Set<String> getLoadedFilenames() {
        return storage.keySet();
    }
    public List<Map<String, String>> filterByColumns(String filename, Map<String, String> rawFilters) {
        List<Map<String, String>> rows = storage.getOrDefault(filename, new ArrayList<>());

        List<FilterCondition> conditions = rawFilters.entrySet().stream()
                .map(entry -> FilterCondition.from(entry.getKey(), entry.getValue()))
                .toList();

        return rows.stream()
                .filter(row -> conditions.stream().allMatch(cond -> cond.matches(row)))
                .collect(Collectors.toList());
    }



}

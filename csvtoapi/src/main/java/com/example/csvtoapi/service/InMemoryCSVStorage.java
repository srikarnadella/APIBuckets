package com.example.csvtoapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.csvtoapi.model.FilterCondition;

@Service
public class InMemoryCSVStorage {

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

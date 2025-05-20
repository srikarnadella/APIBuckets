package com.example.csvtoapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCSVStorageTest {

    private InMemoryCSVStorage storage;

    @BeforeEach
    void setUp() {
        storage = new InMemoryCSVStorage();
        storage.save("test", List.of(
                Map.of("bpm", "128", "genre", "Dance"),
                Map.of("bpm", "124", "genre", "House")
        ));
    }

    @Test
    void testGetAll() {
        List<Map<String, String>> all = storage.getAll("test");
        assertEquals(2, all.size());
    }

    @Test
    void testGetByIndex() {
        Map<String, String> row = storage.getByIndex("test", 0);
        assertEquals("128", row.get("bpm"));
    }

    @Test
    void testFilterByColumns() {
        List<Map<String, String>> filtered = storage.filterByColumns("test", Map.of("genre", "House"));
        assertEquals(1, filtered.size());
        assertEquals("124", filtered.get(0).get("bpm"));
    }
}

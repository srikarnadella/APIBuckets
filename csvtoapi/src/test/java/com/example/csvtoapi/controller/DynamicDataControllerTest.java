package com.example.csvtoapi.controller;

import com.example.csvtoapi.service.InMemoryCSVStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class DynamicDataControllerUnitTest {

    @Mock
    private InMemoryCSVStorage storage;

    @InjectMocks
    private DynamicDataController controller;

    @BeforeEach
    void setup() {
        when(storage.getAll("test")).thenReturn(List.of(
                Map.of("bpm", "128", "genre", "Dance")
        ));
    }

    @Test
    void testGetAll() {
        List<Map<String, String>> result = controller.getAllRows("test", Map.of());
        assertEquals("128", result.get(0).get("bpm"));
    }
}

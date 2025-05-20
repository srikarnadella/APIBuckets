package com.example.csvtoapi.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FilterConditionTest {

    @Test
    void testEqualsMatch() {
        FilterCondition cond = FilterCondition.from("genre", "Dance");
        assertTrue(cond.matches(Map.of("genre", "Dance")));
    }

    @Test
    void testCaseInsensitive() {
        FilterCondition cond = FilterCondition.from("genre", "dance");
        assertTrue(cond.matches(Map.of("genre", "DANCE")));
    }

    @Test
    void testNumericGreaterThan() {
        FilterCondition cond = FilterCondition.from("bpm", ">125");
        assertTrue(cond.matches(Map.of("bpm", "128")));
    }

    @Test
    void testRangeMatch() {
        FilterCondition cond = FilterCondition.from("vibe_score", "1.05-1.2");
        assertTrue(cond.matches(Map.of("vibe_score", "1.1")));
    }

    @Test
    void testInvalidMatch() {
        FilterCondition cond = FilterCondition.from("key", "9B");
        assertFalse(cond.matches(Map.of("genre", "Dance"))); // key doesn't exist
    }
}

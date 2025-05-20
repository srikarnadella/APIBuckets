package com.example.csvtoapi.util;

import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVUtilsTest {

    @Test
    void testParseValidCSV() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("saved_setlists.csv");
        assertNotNull(is, "CSV file not found in resources");

        List<Map<String, String>> records = CSVUtils.parse(is);
        assertEquals(48, records.size(), "Expected 48 records in the CSV");

        Map<String, String> row = records.get(0);
        assertEquals("Have It All", row.get("track_title"));
        assertEquals("128", row.get("bpm"));
        assertEquals("ANDREA CALABRIA", row.get("artist"));
        assertEquals("Dance", row.get("genre"));
        assertEquals("1.05516436", row.get("vibe_score"));
        assertEquals("2A", row.get("key"));
    }
}

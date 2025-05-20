package com.example.csvtoapi.model;

import java.util.Map;

public class FilterCondition {

    private final String key;
    private final String operator;
    private final String value;
    private String valueEnd;

    public FilterCondition(String key, String operator, String value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
        this.valueEnd = null;
    }

    public static FilterCondition from(String column, String rawValue) {
        if (rawValue.contains("-")) {
            String[] parts = rawValue.split("-", 2);
            if (parts.length == 2 && isNumeric(parts[0]) && isNumeric(parts[1])) {
                return FilterCondition.range(column, parts[0], parts[1]);
            }
        }

        // Add ~= for partial match
        String[] ops = {">=", "<=", ">", "<", "=", "~="};
        for (String op : ops) {
            if (rawValue.startsWith(op)) {
                return new FilterCondition(column, op, rawValue.substring(op.length()).trim());
            }
        }

        // Default to equals
        return new FilterCondition(column, "=", rawValue);
    }

    public boolean matches(Map<String, String> row) {
        if (!row.containsKey(key)) return false;

        String rowVal = row.get(key);
        if (rowVal == null) return false;

        try {
            // Numeric comparison block
            double actual = Double.parseDouble(rowVal);
            double expected = Double.parseDouble(value);

            return switch (operator) {
                case ">" -> actual > expected;
                case "<" -> actual < expected;
                case ">=" -> actual >= expected;
                case "<=" -> actual <= expected;
                case "=" -> actual == expected;
                case "range" -> {
                    double max = Double.parseDouble(valueEnd);
                    yield actual >= expected && actual <= max;
                }
                default -> false;
            };

        } catch (NumberFormatException e) {
            // Fallback to string comparisons if not a number
            return switch (operator) {
                case "=" -> rowVal.equalsIgnoreCase(value);
                case "~=" -> rowVal.toLowerCase().contains(value.toLowerCase()); // ✅ partial match
                default -> false;
            };
        }
    }


    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static FilterCondition range(String key, String valueStart, String valueEnd) {
        FilterCondition cond = new FilterCondition(key, "range", valueStart);
        cond.valueEnd = valueEnd;
        return cond;
    }
}

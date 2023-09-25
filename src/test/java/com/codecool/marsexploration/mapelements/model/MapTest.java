package com.codecool.marsexploration.mapelements.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    Map map;

    @Test
    void setSuccessfullyGeneratedSetsTrueValueCorrectly() {
        map = new Map(new String[32][32]);
        map.setSuccessfullyGenerated(true);
        assertTrue(map.isSuccessfullyGenerated());
    }

    @Test
    void setSuccessfullyGeneratedSetsFalseValueCorrectly() {
        map = new Map(new String[32][32]);
        map.setSuccessfullyGenerated(false);
        assertFalse(map.isSuccessfullyGenerated());
    }

    @Test
    void testToString() {
        String[][] representation = {
                {"A", "B"},
                {"C", null}
        };

        map = new Map(representation);
        String expectedString = "AB" + System.lineSeparator() + "C " + System.lineSeparator();

        assertEquals(expectedString, map.toString());
    }
}
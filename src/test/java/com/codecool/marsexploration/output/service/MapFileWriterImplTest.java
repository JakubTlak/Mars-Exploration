package com.codecool.marsexploration.output.service;

import com.codecool.marsexploration.mapelements.model.Map;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class MapFileWriterImplTest {

    @Test
    public void testWriteMapFileNotFoundException() {
        // Create a map that will cause an IOException when converted to a string
        String[][] emptyMap = new String[10][10];
        Map map = new Map(emptyMap);

        // Provide a file path that might not be accessible or valid
        String invalidFilePath = "C:\\Users\\kiza\\Documents\\Codecool\\m3\\TW 4 Mars\\mars-exploration-1-1q2023-java-JakubTlak\\src\\mainmap_1.txt";

        // Use assertThrows to check for an IOException
        assertThrowsExactly(FileNotFoundException.class, () -> {
            MapFileWriter mapFileWriter = new MapFileWriterImpl();
            mapFileWriter.writeMapFile(map, invalidFilePath);
        });
    }
}
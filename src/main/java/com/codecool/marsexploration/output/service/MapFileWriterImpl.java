package com.codecool.marsexploration.output.service;

import com.codecool.marsexploration.mapelements.model.Map;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MapFileWriterImpl implements MapFileWriter {
    @Override
    public void writeMapFile(Map map, String file) throws IOException {
        String mapRepresentation = map.toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(mapRepresentation);
            writer.flush();
        }
    }
}

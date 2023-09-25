package com.codecool.marsexploration.output.service;

import com.codecool.marsexploration.mapelements.model.Map;

import java.io.IOException;

public interface MapFileWriter
{
    void writeMapFile(Map map, String file) throws IOException;
}
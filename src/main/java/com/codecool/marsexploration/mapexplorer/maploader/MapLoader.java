package com.codecool.marsexploration.mapexplorer.maploader;


import com.codecool.marsexploration.mapelements.model.Map;

public interface MapLoader {
    Map load(String mapFile);
}

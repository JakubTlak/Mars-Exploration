package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.List;

public interface GenerateRandom {
    public Coordinate getRandomMineralCoordinates(List<Coordinate> scannedResourcesCoordinates);
}

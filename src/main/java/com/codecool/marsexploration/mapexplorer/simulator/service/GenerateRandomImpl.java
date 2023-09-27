package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class GenerateRandomImpl implements GenerateRandom{
    private final Random random = new Random();

    @Override
    public Coordinate getRandomMineralCoordinates(List<Coordinate> scannedResourcesCoordinates){
        int randomIndex = random.nextInt(scannedResourcesCoordinates.size());
        return scannedResourcesCoordinates.get(randomIndex);
    }
}

package com.codecool.marsexploration.mapexplorer.configuration.model;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.List;

public record SimulationConfiguration(
        String mapFilePath,
        Coordinate landingCoordinate,
        List<String> symbolsToScan,
        int simulationStepsToTimeout) {
}

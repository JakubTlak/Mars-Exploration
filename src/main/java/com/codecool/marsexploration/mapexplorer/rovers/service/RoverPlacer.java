package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.rovers.model.RoverType;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RoverPlacer {
    Random random = new Random();
    private final SimulationConfiguration configuration;
    private final CoordinateCalculator coordinateCalculator;
    private Map map;
    private final int dimension;
    private final RoverType roverType;


    public RoverPlacer(SimulationConfiguration configuration,
                       CoordinateCalculator coordinateCalculator,
                       Map map,
                       int dimension,
                       RoverType roverType) {
        this.configuration = configuration;
        this.coordinateCalculator = coordinateCalculator;
        this.map = map;
        this.dimension = dimension;
        this.roverType = roverType;
    }

    private Coordinate getPlacementPosition() {
        Iterable<Coordinate> adjacentCoordinates = coordinateCalculator.getAdjacentCoordinates(configuration.landingCoordinate(), dimension);

        Stream<Coordinate> coordinateStream = StreamSupport.stream(adjacentCoordinates.spliterator(), false);

        List<Coordinate> coordinateList = coordinateStream
                .filter(coordinate -> map.isEmpty(coordinate))
                .toList();

        if (coordinateList.isEmpty()) {
            throw new IllegalStateException("No available placement positions");
        }

        int randomIndex = random.nextInt(coordinateList.size());
        return coordinateList.get(randomIndex);
    }

    public MarsRover placeRover(int sight) {
        return new MarsRover(getPlacementPosition(), sight, roverType);
    }

    public void setMap(Map map) {
        this.map = map;
    }
}

package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

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


    public RoverPlacer(SimulationConfiguration configuration, Map map, int dimension) {
        this.configuration = configuration;
        this.map = map;
        this.dimension = dimension;
        this.coordinateCalculator = new CoordinateCalculatorImpl();
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

        return new MarsRover(getPlacementPosition(), sight);
    }

    public void setMap(Map map) {
        this.map = map;
    }
}

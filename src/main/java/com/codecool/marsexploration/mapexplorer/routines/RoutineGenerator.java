package com.codecool.marsexploration.mapexplorer.routines;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.mapelements.model.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RoutineGenerator {

    private Map map;

    private final CoordinateCalculator coordinateCalculator = new CoordinateCalculatorImpl();

    private final int dimension;

    private final List<Coordinate> visitedCoordinates = new ArrayList<>();


    public RoutineGenerator(Map map, int dimension) {
        this.map = map;
        this.dimension = dimension;
    }


    public List<Coordinate> generateSearchRoutine(Coordinate startCoordinate, int steps) {
        List<Coordinate> routine = new ArrayList<>();
        Coordinate currentCoordinate = startCoordinate;
        visitedCoordinates.add(startCoordinate);

        for (int i = 0; i < steps; i++) {
            Coordinate nextCoordinate = getNextCoordinate(currentCoordinate);

            if (nextCoordinate != null) {
                routine.add(nextCoordinate);
                visitedCoordinates.add(nextCoordinate);
                currentCoordinate = nextCoordinate;
            } else {
                break;
            }
        }

        visitedCoordinates.clear();
        return routine;
    }

    public List<Coordinate> generateReturnRoutine(List<Coordinate> exploredRoute) {
        Collections.reverse(exploredRoute);
        return exploredRoute;
    }

    private Coordinate getNextCoordinate(Coordinate currentCoordinate) {
        Iterable<Coordinate> adjacentCoordinates = coordinateCalculator.getAdjacentCoordinates(currentCoordinate, dimension);
        List<Coordinate> adjacentEmptyCoordinates = new ArrayList<>();
        Coordinate nextMove = null;
        adjacentCoordinates.forEach(adjacentEmptyCoordinates::add);

        Collections.shuffle(adjacentEmptyCoordinates);

        for (Coordinate nextCoordinate : adjacentEmptyCoordinates) {
            if (map.isEmpty(nextCoordinate) && !visitedCoordinates.contains(nextCoordinate)) {
                nextMove = nextCoordinate;
                break;
            }
        }
        if (nextMove == null){
            for (Coordinate nextCoordinate : adjacentEmptyCoordinates) {
                if (map.isEmpty(nextCoordinate)) {
                    nextMove = nextCoordinate;
                    break;
                }
            }
        }
        return nextMove;
    }
    public void setMap(Map map) {
        this.map = map;
    }

}

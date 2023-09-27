package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.List;

public interface RoverBehaviour {
    void executeRoverRoutine(List<Coordinate> allMovesRoutine,
                             List<Coordinate> movesDone,
                             MarsRover rover,
                             Map map,
                             Coordinate coordinateOfLastMaterial);
}

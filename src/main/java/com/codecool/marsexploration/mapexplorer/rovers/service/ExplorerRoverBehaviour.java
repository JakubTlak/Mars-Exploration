package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.List;

public interface ExplorerRoverBehaviour {
    void executeExplorerRoverBehaviour(List<Coordinate> allMovesRoutine,
                                       List<Coordinate> movesDone,
                                       MarsRover rover);
}

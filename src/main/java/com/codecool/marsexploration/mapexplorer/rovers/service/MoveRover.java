package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

public interface MoveRover {
    public void moveRover(MarsRover rover, Coordinate coordinateToMove);
}

package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.List;

public interface RoverBehaviour {
    void exploreMap(List<Coordinate> allMovesRoutine,
                    List<Coordinate> movesDone,
                    MarsRover rover);
    void moveRoverBackToShip(List<Coordinate> movesDone, MarsRover rover);
}

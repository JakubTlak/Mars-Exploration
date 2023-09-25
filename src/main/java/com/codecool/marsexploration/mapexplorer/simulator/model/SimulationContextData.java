package com.codecool.marsexploration.mapexplorer.simulator.model;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.List;

/**
 * @param steps The number of steps
 * @param stepsToTimeout The number of steps required to reach a timeout
 * @param marsRover The rover
 * @param spaceshipLocation The location of the spaceship
 * @param map The map
 * @param symbolsToScan The symbols of resources to monitor
 */
public record SimulationContextData(int steps,
                                    int stepsToTimeout,
                                    MarsRover marsRover,
                                    Coordinate spaceshipLocation,
                                    Map map,
                                    List<String> symbolsToScan) {
}

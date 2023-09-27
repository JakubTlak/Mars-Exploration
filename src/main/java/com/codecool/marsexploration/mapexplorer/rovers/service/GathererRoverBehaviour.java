package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

public interface GathererRoverBehaviour {
    public void executeGathererRoverBehaviour(MarsRover marsRover, Coordinate resourcePosition, Map map);
}

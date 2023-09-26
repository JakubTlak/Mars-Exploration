package com.codecool.marsexploration.colonization.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.rovers.model.RoverType;

import java.util.List;

public interface ColonyServices {
    MarsRover buildRover(Coordinate colonyCoordinates,
                         List<String> colonyResources,
                         List<String> resourcesNeededToBuildRover,
                         String[][] map,
                         RoverType roverType);
}

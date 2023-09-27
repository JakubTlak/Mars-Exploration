package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.List;

public interface BuilderRoverBehaviour {
    public void executeBuilderRoverBehaviour(MarsRover rover,Map map, Coordinate coordinateOfLastMaterial);
}

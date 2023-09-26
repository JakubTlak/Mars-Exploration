package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

public interface SimulationMessages {
    public void displayCurrentStep(MarsRover rover, String event);
    public void displayInitialMessages(Coordinate spaceshipLocation);
}

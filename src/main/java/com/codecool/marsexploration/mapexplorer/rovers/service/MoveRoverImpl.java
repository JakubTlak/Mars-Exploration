package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.simulator.model.SimulationSteps;
import com.codecool.marsexploration.mapexplorer.simulator.service.SimulationMessages;

public class MoveRoverImpl implements MoveRover{
    private final SimulationMessages simulationMessages;

    public MoveRoverImpl(SimulationMessages simulationMessages) {
        this.simulationMessages = simulationMessages;
    }

    public void moveRover(MarsRover rover, Coordinate coordinateToMove) {
        rover.setCurrentPosition(coordinateToMove);
        simulationMessages.displayCurrentStep(rover, SimulationSteps.POSITION.name());
    }
}

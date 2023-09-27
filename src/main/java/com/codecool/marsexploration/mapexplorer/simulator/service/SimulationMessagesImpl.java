package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.logger.service.FileLoggerImpl;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

public class SimulationMessagesImpl implements SimulationMessages{
    private final FileLoggerImpl fileLogger;

    public SimulationMessagesImpl(FileLoggerImpl fileLogger) {
        this.fileLogger = fileLogger;
    }

    public void displayCurrentStep(MarsRover rover, String event) {
        fileLogger.logInfo("STEP: " + rover.getCurrentStep() + "," +
                " EVENT: " + event + "," +
                " UNIT: " + rover.getRover_id() + "," +
                "POSITION [" + rover.getCurrentPosition().x() + "," +
                rover.getCurrentPosition().y() + "]"
                + "," + " TYPE: " + rover.getRoverType().name());
    }

    public void displayInitialMessages(Coordinate spaceshipLocation) {
        fileLogger.logInfo("Simulation started");
        fileLogger.logInfo(String.format("The ship landed at %s", spaceshipLocation));
    }
}

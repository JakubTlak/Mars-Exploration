package com.codecool.marsexploration.mapexplorer.configuration.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.logger.service.ConsoleLogger;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoader;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SimulationConfigurationValidatorImplTest {

    MapLoader mapLoader = new MapLoaderImpl();
    ConsoleLogger consoleLogger = new ConsoleLoggerImpl();
    String mapFilePath = "src/main/resources/exploration-0.map";

    SimulationConfiguration simulationConfiguration;
    Coordinate landingCoordinate;
    ArrayList<String> symbolsToScan = new ArrayList<>();
    int simulationStepsToTimeout;
    SimulationConfigurationValidator simulationConfigurationValidator;


    @BeforeEach
    void beforeEach() {
        simulationConfigurationValidator =
                new SimulationConfigurationValidatorImpl(mapLoader, consoleLogger, mapFilePath);
    }

    @Test
    void validationTrue() {
        symbolsToScan.add("#");
        simulationStepsToTimeout = 100;
        simulationConfiguration =
                new SimulationConfiguration(mapFilePath, new Coordinate(6, 6), symbolsToScan, simulationStepsToTimeout);
        assertTrue(simulationConfigurationValidator.validate(simulationConfiguration));
    }

    @Test
    void validationFalseWhenLandingSpotIsNotEmpty() {
        symbolsToScan.add("#");
        simulationStepsToTimeout = 100;
        simulationConfiguration =
                new SimulationConfiguration(mapFilePath, new Coordinate(16, 7), symbolsToScan, simulationStepsToTimeout);
        assertFalse(simulationConfigurationValidator.validate(simulationConfiguration));
    }

    @Test
    void validationFalseWhenHasLandingSpotEmptyAdjacentField() {
        symbolsToScan.add("#");
        simulationStepsToTimeout = 100;
        simulationConfiguration =
                new SimulationConfiguration(mapFilePath, new Coordinate(16, 7), symbolsToScan, simulationStepsToTimeout);
        assertFalse(simulationConfigurationValidator.validate(simulationConfiguration));
    }

    @Test
    void validationFalseWhenMapFileIsEmpty() {
        symbolsToScan.add("#");
        simulationStepsToTimeout = 100;
        simulationConfiguration =
                new SimulationConfiguration("src/test/emptyFileForTest.txt", new Coordinate(6, 6), symbolsToScan, simulationStepsToTimeout);
        assertFalse(simulationConfigurationValidator.validate(simulationConfiguration));
    }

    @Test
    void validationFalseWhenResourceIsNotSpecified() {
        simulationStepsToTimeout = 100;
        simulationConfiguration =
                new SimulationConfiguration(mapFilePath, new Coordinate(6, 6), symbolsToScan, simulationStepsToTimeout);
        assertFalse(simulationConfigurationValidator.validate(simulationConfiguration));
    }
    @Test
    void validationFalseWhenTimeout() {
        symbolsToScan.add("#");
        simulationStepsToTimeout = 0;
        simulationConfiguration =
                new SimulationConfiguration(mapFilePath, new Coordinate(6, 6), symbolsToScan, simulationStepsToTimeout);
        assertFalse(simulationConfigurationValidator.validate(simulationConfiguration));
    }


}
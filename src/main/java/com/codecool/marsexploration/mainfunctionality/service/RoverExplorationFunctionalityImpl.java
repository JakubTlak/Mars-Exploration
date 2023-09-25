package com.codecool.marsexploration.mainfunctionality.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import com.codecool.marsexploration.logger.service.FileLoggerImpl;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzerImpl;
import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapexplorer.configuration.service.SimulationConfigurationValidatorImpl;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoader;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoaderImpl;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.service.RoverPlacer;
import com.codecool.marsexploration.mapexplorer.simulator.service.ExplorationSimulator;
import com.codecool.marsexploration.mapexplorer.simulator.service.ExplorationSimulatorImpl;
import com.codecool.marsexploration.mapexplorer.simulator.service.ExplorationSimulatorScannerImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RoverExplorationFunctionalityImpl implements RoverExplorationFunctionality{

    private final Coordinate landingSpot = ConstantValues.LANDING_SPOT;
    private final List<String> toScan = ConstantValues.ELEMENTS_TO_SCAN;
    String loggFile = ConstantValues.EXPLORATION_LOG_PATH;
    private final ConsoleLoggerImpl consoleLogger;
    private final CoordinateCalculator coordinateCalculator;

    public RoverExplorationFunctionalityImpl(ConsoleLoggerImpl consoleLogger, CoordinateCalculator coordinateCalculator) {
        this.consoleLogger = consoleLogger;
        this.coordinateCalculator = coordinateCalculator;
    }

    public void startRoverExploration(){
        int simulationSteps = ConstantValues.SIMULATION_STEPS;
        int totalSteps = ConstantValues.TOTAL_STEPS;
        int roverSight = ConstantValues.ROVER_SIGHT;
        int mapDimension = ConstantValues.MAP_DIMENSION;
        String mapFile = ConstantValues.MAP_TO_LOAD_PATH;
        String mineralSymbol = ConstantValues.MINERAL_SYMBOL;
        String waterSymbol = ConstantValues.WATER_SYMBOL;

        clearTextFile(loggFile);

        SimulationConfiguration simulation = new SimulationConfiguration(mapFile, landingSpot, toScan, simulationSteps);
        MapLoader mapLoader = new MapLoaderImpl();
        Map map = mapLoader.load(mapFile);
        RoutineGenerator routineGenerator = new RoutineGenerator(map, mapDimension);
        RoverPlacer roverPlacer = new RoverPlacer(simulation, map, mapDimension);
        OutcomeAnalyzer outcomeAnalyzer = new OutcomeAnalyzerImpl();
        SimulationConfigurationValidatorImpl simulationConfigurationValidator =
                new SimulationConfigurationValidatorImpl(mapLoader, consoleLogger, mapFile);
        ExplorationSimulatorScannerImpl simulatorScanner = new ExplorationSimulatorScannerImpl(coordinateCalculator,
                map);
        FileLoggerImpl fileLogger = new FileLoggerImpl(loggFile);

        ExplorationSimulator explorationSimulator = new ExplorationSimulatorImpl(
                totalSteps,
                roverSight,
                mineralSymbol,
                waterSymbol,
                mapLoader,
                roverPlacer,
                outcomeAnalyzer,
                simulation,
                simulationConfigurationValidator,
                routineGenerator,
                simulatorScanner,
                fileLogger);

        explorationSimulator.simulateRoverExploration();
    }

    private void clearTextFile(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path, false);
            fileWriter.close();
            System.out.println("File cleared successfully.");
        } catch (IOException e) {
            System.err.println("Error clearing the file: " + e.getMessage());
        }
    }
}

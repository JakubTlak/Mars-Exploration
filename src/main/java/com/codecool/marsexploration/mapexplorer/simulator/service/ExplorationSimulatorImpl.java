package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.logger.service.FileLoggerImpl;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapexplorer.configuration.service.SimulationConfigurationValidatorImpl;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoader;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.rovers.service.MoveRover;
import com.codecool.marsexploration.mapexplorer.rovers.service.RoverBehaviour;
import com.codecool.marsexploration.mapexplorer.rovers.service.RoverBehaviourImpl;
import com.codecool.marsexploration.mapexplorer.rovers.service.RoverPlacer;
import com.codecool.marsexploration.mapexplorer.simulator.model.SimulationContextData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExplorationSimulatorImpl implements ExplorationSimulator {
    private final Random random = new Random();

    private final int totalSteps;
    private final int roverSight;
    private final MapLoader mapLoader;
    private final RoverPlacer roverPlacer;
    private final OutcomeAnalyzer outcomeAnalyzer;
    private final SimulationConfiguration simulationConfiguration;
    private final SimulationConfigurationValidatorImpl simulationConfigurationValidator;
    private final RoutineGenerator routineGenerator;
    private final ExplorationSimulatorScannerImpl simulatorScanner;
    private final FileLoggerImpl fileLogger;
    private final MoveRover moveRover;
    private final BuildColony buildColony;
    private final GatheringService gatheringService;
    private final GenerateRandom generateRandom;

    public ExplorationSimulatorImpl(int totalSteps,
                                    int roverSight,
                                    MapLoader mapLoader,
                                    RoverPlacer roverPlacer,
                                    OutcomeAnalyzer outcomeAnalyzer,
                                    SimulationConfiguration simulationConfiguration,
                                    SimulationConfigurationValidatorImpl simulationConfigurationValidator,
                                    RoutineGenerator routineGenerator,
                                    ExplorationSimulatorScannerImpl simulatorScanner,
                                    FileLoggerImpl fileLogger, MoveRover moveRover, BuildColony buildColony, GatheringService gatheringService, GenerateRandom generateRandom) {
        this.totalSteps = totalSteps;
        this.roverSight = roverSight;
        this.mapLoader = mapLoader;
        this.roverPlacer = roverPlacer;
        this.outcomeAnalyzer = outcomeAnalyzer;
        this.simulationConfiguration = simulationConfiguration;
        this.simulationConfigurationValidator = simulationConfigurationValidator;
        this.routineGenerator = routineGenerator;
        this.simulatorScanner = simulatorScanner;
        this.fileLogger = fileLogger;
        this.moveRover = moveRover;
        this.buildColony = buildColony;
        this.gatheringService = gatheringService;
        this.generateRandom = generateRandom;
    }

    @Override
    public void simulateRoverExploration() {
        boolean configValid = isConfigValid();
        if (!configValid) {
            return;
        }

        SimulationMessages simulationMessages = new SimulationMessagesImpl(fileLogger);
        RoverBehaviour roverBehaviour = new RoverBehaviourImpl(
                simulatorScanner,
                routineGenerator,
                outcomeAnalyzer,
                simulationMessages,
                moveRover,
                buildColony,
                gatheringService,
                generateRandom);

        SimulationContextData contextData = generateContext(roverSight);
        Coordinate shipLocation = contextData.spaceshipLocation();
        MarsRover roverExplorer = contextData.marsRover();
        Coordinate roverStartingPosition = roverExplorer.getCurrentPosition();
        List<Coordinate> allMovesRoutine = routineGenerator.generateSearchRoutine(roverStartingPosition, totalSteps);
        List<Coordinate> movesDone = new ArrayList<>();


        simulationMessages.displayInitialMessages(shipLocation);
        simulationMessages.displayCurrentStep(roverExplorer, "The rover has been deployed");

        Coordinate explorerCoordinate = new Coordinate(1,1);
        roverBehaviour.executeRoverRoutine(allMovesRoutine, movesDone, roverExplorer, loadMap(), explorerCoordinate);

        buildColony.build(roverExplorer, loadMap());
    }

    /**
     * @return SimulationContextData object
     */
    private SimulationContextData generateContext(int roverSight) {
        int stepsToTimeout = simulationConfiguration.simulationStepsToTimeout();
        Coordinate spaceshipCoordinates = simulationConfiguration.landingCoordinate();
        List<String> symbolsToScan = simulationConfiguration.symbolsToScan();
        MarsRover rover = roverPlacer.placeRover(roverSight);


        return new SimulationContextData(totalSteps
                , stepsToTimeout
                , rover
                , spaceshipCoordinates
                , loadMap()
                , symbolsToScan);
    }

    /**
     * @return true if simulation config is valid, false otherwise
     */
    private boolean isConfigValid() {
        return simulationConfigurationValidator.validate(simulationConfiguration);
    }

    /**
     * @return Map class object
     */
    private Map loadMap() {
        String mapPath = simulationConfiguration.mapFilePath();
        return mapLoader.load(mapPath);
    }

}

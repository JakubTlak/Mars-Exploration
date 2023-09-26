package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.logger.service.FileLoggerImpl;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapexplorer.configuration.service.SimulationConfigurationValidatorImpl;
import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoader;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.rovers.service.RoverPlacer;
import com.codecool.marsexploration.mapexplorer.simulator.model.SimulationContextData;
import com.codecool.marsexploration.mapexplorer.simulator.model.SimulationSteps;

import java.util.ArrayList;
import java.util.List;

public class ExplorationSimulatorImpl implements ExplorationSimulator {
    private boolean meetsFinishCondition = false;
    private int currentStep = 0;

    private final int totalSteps;
    private final int roverSight;
    private final String mineralSymbol;
    private final String waterSymbol;
    private final MapLoader mapLoader;
    private final RoverPlacer roverPlacer;
    private final OutcomeAnalyzer outcomeAnalyzer;
    private final SimulationConfiguration simulationConfiguration;
    private final SimulationConfigurationValidatorImpl simulationConfigurationValidator;
    private final RoutineGenerator routineGenerator;
    private final ExplorationSimulatorScannerImpl simulatorScanner;
    private final FileLoggerImpl fileLogger;

    public ExplorationSimulatorImpl(int totalSteps,
                                    int roverSight,
                                    String mineralSymbol,
                                    String waterSymbol,
                                    MapLoader mapLoader,
                                    RoverPlacer roverPlacer,
                                    OutcomeAnalyzer outcomeAnalyzer,
                                    SimulationConfiguration simulationConfiguration,
                                    SimulationConfigurationValidatorImpl simulationConfigurationValidator,
                                    RoutineGenerator routineGenerator,
                                    ExplorationSimulatorScannerImpl simulatorScanner,
                                    FileLoggerImpl fileLogger) {
        this.totalSteps = totalSteps;
        this.roverSight = roverSight;
        this.mineralSymbol = mineralSymbol;
        this.waterSymbol = waterSymbol;
        this.mapLoader = mapLoader;
        this.roverPlacer = roverPlacer;
        this.outcomeAnalyzer = outcomeAnalyzer;
        this.simulationConfiguration = simulationConfiguration;
        this.simulationConfigurationValidator = simulationConfigurationValidator;
        this.routineGenerator = routineGenerator;
        this.simulatorScanner = simulatorScanner;
        this.fileLogger = fileLogger;
    }

    @Override
    public void simulateRoverExploration() {
        boolean configValid = isConfigValid();
        if (!configValid) {
            return;
        }

        SimulationContextData contextData = generateContext(roverSight);
        Coordinate shipLocation = contextData.spaceshipLocation();
        MarsRover rover = contextData.marsRover();
        Coordinate roverStartingPosition = rover.getCurrentPosition();
        List<Coordinate> allMovesRoutine = routineGenerator.generateSearchRoutine(roverStartingPosition,totalSteps);
        List<Coordinate> movesDone = new ArrayList<>();


        displayInitialMessages(shipLocation);
        displayCurrentStep(rover, "The rover has been deployed");
        exploreMap(allMovesRoutine, movesDone, rover);
        moveRoverBackToShip(movesDone, rover);
    }

    private void exploreMap(List<Coordinate> allMovesRoutine,
                            List<Coordinate> movesDone,
                            MarsRover rover){
        while (!meetsFinishCondition) {
            Coordinate coordinateToMove = allMovesRoutine.get(currentStep);
            movesDone.add(coordinateToMove);
            simulateRoverBehaviourForOneStep(rover, coordinateToMove);
            currentStep += 1;
        }
    }

    private void moveRoverBackToShip(List<Coordinate> movesDone, MarsRover rover){
        List<Coordinate> returnMoves = routineGenerator.generateReturnRoutine(movesDone);
        int returnStep = 0;
        while (returnStep < returnMoves.size()){
            moveRover(rover,returnMoves.get(returnStep));
            returnStep++;
            currentStep += 1;
        }
    }

    private void simulateRoverBehaviourForOneStep(MarsRover rover, Coordinate coordinateToMove) {
        moveRover(rover, coordinateToMove);
        scanTheArea(rover);
        analyseInformation(rover);
    }

    private void moveRover(MarsRover rover, Coordinate coordinateToMove) {
        rover.setCurrentPosition(coordinateToMove);
        displayCurrentStep(rover, SimulationSteps.POSITION.name());
    }

    private void scanTheArea(MarsRover rover) {
        simulatorScanner.scanForResources(rover);
        displayCurrentStep(rover, SimulationSteps.SCAN.name());
    }

    private void analyseInformation(MarsRover rover) {
        hasReachedTimeoutCheck(rover);
        meetsColonizationConditionsCheck(rover);
        hasNotEnoughResourcesCheck(rover);
    }

    private void hasReachedTimeoutCheck(MarsRover rover){
        boolean hasReachedTimeout = outcomeAnalyzer.hasReachedTimeout(currentStep);
        if (hasReachedTimeout) {
            displayCurrentStep(rover,
                    SimulationSteps.OUTCOME.name() + " " + ExplorationOutcome.TIMEOUT.name());
            setMeetsFinishCondition(true);
        }
    }

    private void meetsColonizationConditionsCheck(MarsRover rover){
        boolean meetsColonizationConditions = outcomeAnalyzer.meetsColonizationConditions(rover);
        if (meetsColonizationConditions) {
            displayCurrentStep(rover,
                    SimulationSteps.OUTCOME.name() + " " + ExplorationOutcome.COLONIZABLE.name());
            setMeetsFinishCondition(true);
        }
    }

    private void hasNotEnoughResourcesCheck(MarsRover rover){
        boolean hasNotEnoughResources = outcomeAnalyzer.hasNotEnoughResources(rover, currentStep);
        if (hasNotEnoughResources) {
            displayCurrentStep(rover,
                    SimulationSteps.OUTCOME.name() + " " + ExplorationOutcome.ABORT.name());
            setMeetsFinishCondition(true);
        }
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

    private void displayCurrentStep(MarsRover rover, String event) {
        fileLogger.logInfo("STEP: " + currentStep + "," +
                " EVENT: " + event + "," +
                " UNIT: " + rover.getRover_id() + "," +
                "POSITION [" + rover.getCurrentPosition().x() + "," +
                rover.getCurrentPosition().y() + "]");
    }

    private void displayInitialMessages(Coordinate spaceshipLocation) {
        fileLogger.logInfo("Simulation started");
        fileLogger.logInfo(String.format("The ship landed at %s", spaceshipLocation));
    }

    private void setMeetsFinishCondition(boolean meetsFinishCondition) {
        this.meetsFinishCondition = meetsFinishCondition;
    }

}

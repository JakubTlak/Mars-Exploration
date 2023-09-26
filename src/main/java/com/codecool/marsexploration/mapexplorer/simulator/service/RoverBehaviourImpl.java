package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.simulator.model.SimulationSteps;

import java.util.List;

public class RoverBehaviourImpl implements RoverBehaviour{
    private ExplorationOutcome outcome = null;

    private final ExplorationSimulatorScannerImpl simulatorScanner;
    private final RoutineGenerator routineGenerator;
    private final OutcomeAnalyzer outcomeAnalyzer;
    private final SimulationMessages simulationMessages;

    public RoverBehaviourImpl(ExplorationSimulatorScannerImpl simulatorScanner,
                              RoutineGenerator routineGenerator,
                              OutcomeAnalyzer outcomeAnalyzer,
                              SimulationMessages simulationMessages) {
        this.simulatorScanner = simulatorScanner;
        this.routineGenerator = routineGenerator;
        this.outcomeAnalyzer = outcomeAnalyzer;
        this.simulationMessages = simulationMessages;
    }

    @Override
    public void executeRoverRoutine(List<Coordinate> allMovesRoutine, List<Coordinate> movesDone, MarsRover rover) {
        exploreMap(allMovesRoutine, movesDone, rover);

        if(outcome != ExplorationOutcome.COLONIZABLE){
            moveRoverBackToShip(movesDone,rover);
        }
    }

    private void exploreMap(List<Coordinate> allMovesRoutine,
                            List<Coordinate> movesDone,
                            MarsRover rover){
        while (outcome == null) {
            Coordinate coordinateToMove = allMovesRoutine.get(rover.getCurrentStep());
            movesDone.add(coordinateToMove);
            simulateRoverBehaviourForOneStep(rover, coordinateToMove);
            rover.setCurrentStep(rover.getCurrentStep() + 1);
        }
    }

    private void moveRoverBackToShip(List<Coordinate> movesDone, MarsRover rover){
        List<Coordinate> returnMoves = routineGenerator.generateReturnRoutine(movesDone);
        int returnStep = 0;
        while (returnStep < returnMoves.size()){
            moveRover(rover,returnMoves.get(returnStep));
            returnStep++;
            rover.setCurrentStep(rover.getCurrentStep() + 1);
        }
    }

    private void simulateRoverBehaviourForOneStep(MarsRover rover, Coordinate coordinateToMove) {
        moveRover(rover, coordinateToMove);
        scanTheArea(rover);
        analyseInformation(rover);
    }

    private void moveRover(MarsRover rover, Coordinate coordinateToMove) {
        rover.setCurrentPosition(coordinateToMove);
        simulationMessages.displayCurrentStep(rover, SimulationSteps.POSITION.name());
    }

    private void scanTheArea(MarsRover rover) {
        simulatorScanner.scanForResources(rover);
        simulationMessages.displayCurrentStep(rover, SimulationSteps.SCAN.name());
    }

    private void analyseInformation(MarsRover rover) {
        hasReachedTimeoutCheck(rover);
        meetsColonizationConditionsCheck(rover);
        hasNotEnoughResourcesCheck(rover);
    }

    private void hasReachedTimeoutCheck(MarsRover rover){
        boolean hasReachedTimeout = outcomeAnalyzer.hasReachedTimeout(rover.getCurrentStep());
        if (hasReachedTimeout) {
            simulationMessages.displayCurrentStep(rover,
                    SimulationSteps.OUTCOME.name() + " " + ExplorationOutcome.TIMEOUT.name());
            outcome = ExplorationOutcome.TIMEOUT;
        }
    }

    private void meetsColonizationConditionsCheck(MarsRover rover){
        boolean meetsColonizationConditions = outcomeAnalyzer.meetsColonizationConditions(rover);
        if (meetsColonizationConditions) {
            simulationMessages.displayCurrentStep(rover,
                    SimulationSteps.OUTCOME.name() + " " + ExplorationOutcome.COLONIZABLE.name());
            outcome = ExplorationOutcome.COLONIZABLE;
        }
    }

    private void hasNotEnoughResourcesCheck(MarsRover rover){
        boolean hasNotEnoughResources = outcomeAnalyzer.hasNotEnoughResources(rover, rover.getCurrentStep());
        if (hasNotEnoughResources) {
            simulationMessages.displayCurrentStep(rover,
                    SimulationSteps.OUTCOME.name() + " " + ExplorationOutcome.ABORT.name());
            outcome = ExplorationOutcome.ABORT;
        }
    }
}

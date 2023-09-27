package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.simulator.model.SimulationSteps;
import com.codecool.marsexploration.mapexplorer.simulator.service.ExplorationSimulatorScannerImpl;
import com.codecool.marsexploration.mapexplorer.simulator.service.SimulationMessages;

import java.util.List;

public class ExplorerRoverBehaviourImpl implements ExplorerRoverBehaviour{
    private ExplorationOutcome outcome = null;

    private final RoutineGenerator routineGenerator;
    private final MoveRover moveRover;
    private final OutcomeAnalyzer outcomeAnalyzer;
    private final SimulationMessages simulationMessages;
    private final ExplorationSimulatorScannerImpl simulatorScanner;

    public ExplorerRoverBehaviourImpl(RoutineGenerator routineGenerator,
                                      MoveRover moveRover,
                                      OutcomeAnalyzer outcomeAnalyzer,
                                      SimulationMessages simulationMessages,
                                      ExplorationSimulatorScannerImpl simulatorScanner) {
        this.routineGenerator = routineGenerator;
        this.moveRover = moveRover;
        this.outcomeAnalyzer = outcomeAnalyzer;
        this.simulationMessages = simulationMessages;
        this.simulatorScanner = simulatorScanner;
    }

    @Override
    public void executeExplorerRoverBehaviour(List<Coordinate> allMovesRoutine,
                                              List<Coordinate> movesDone,
                                              MarsRover rover) {

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
            moveRover.moveRover(rover,returnMoves.get(returnStep));
            returnStep++;
            rover.setCurrentStep(rover.getCurrentStep() + 1);
        }
    }

    private void simulateRoverBehaviourForOneStep(MarsRover rover, Coordinate coordinateToMove) {
        moveRover.moveRover(rover, coordinateToMove);
        scanTheArea(rover);
        analyseInformation(rover);
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

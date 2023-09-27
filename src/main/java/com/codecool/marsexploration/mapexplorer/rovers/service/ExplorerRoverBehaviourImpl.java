package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.simulator.model.SimulationSteps;
import com.codecool.marsexploration.mapexplorer.simulator.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExplorerRoverBehaviourImpl implements ExplorerRoverBehaviour{
    private ExplorationOutcome outcome = null;

    private final RoutineGenerator routineGenerator;
    private final MoveRover moveRover;
    private final OutcomeAnalyzer outcomeAnalyzer;
    private final SimulationMessages simulationMessages;
    private final ExplorationSimulatorScannerImpl simulatorScanner;
    private final GatheringService gatheringService;
    private final GenerateRandom generateRandom;

    private final TransformMethods transformMethods = new TransformMethodsImpl();

    public ExplorerRoverBehaviourImpl(RoutineGenerator routineGenerator,
                                      MoveRover moveRover,
                                      OutcomeAnalyzer outcomeAnalyzer,
                                      SimulationMessages simulationMessages,
                                      ExplorationSimulatorScannerImpl simulatorScanner, GatheringService gatheringService, GenerateRandom generateRandom) {
        this.routineGenerator = routineGenerator;
        this.moveRover = moveRover;
        this.outcomeAnalyzer = outcomeAnalyzer;
        this.simulationMessages = simulationMessages;
        this.simulatorScanner = simulatorScanner;
        this.gatheringService = gatheringService;
        this.generateRandom = generateRandom;
    }

    @Override
    public void executeExplorerRoverBehaviour(List<Coordinate> allMovesRoutine,
                                              List<Coordinate> movesDone,
                                              MarsRover rover,
                                              Map map) {

        exploreMap(allMovesRoutine, movesDone, rover);

        if(outcome != ExplorationOutcome.COLONIZABLE){
            moveRoverBackToShip(movesDone,rover);
            return;
        }
        getResourcesToCreateBase(rover,map);
        System.out.println(rover.getGatheredResources());
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

    private void getResourcesToCreateBase(MarsRover rover,
                                          Map map){
        final String mineralSymbol = ConstantValues.MINERAL_SYMBOL;
        final String waterSymbol = ConstantValues.WATER_SYMBOL;
        final int neededMinerals = ConstantValues.MINERALS_TO_SET_UP_COLONY;
        final int neededWater = ConstantValues.WATER_TO_SET_UP_COLONY;

        GathererRoverBehaviour gathererRoverBehaviour = new GathererRoverBehaviourImpl(
                gatheringService,
                routineGenerator,
                moveRover);

        int amountOfWater = 0;
        int amountOfMinerals = 0;

        List<Coordinate> scannedResourcesCoordinates = transformMethods.mapStringSetOfCoordinatesToListOfCoordinates(
                rover.getResourceCoordinates());

        while (amountOfWater < neededWater && amountOfMinerals < neededMinerals){
            if(!scannedResourcesCoordinates.isEmpty()) {
                Coordinate resourcePosition = generateRandom.getRandomMineralCoordinates(scannedResourcesCoordinates);
                scannedResourcesCoordinates.remove(resourcePosition);

                gathererRoverBehaviour.executeGathererRoverBehaviour(rover, resourcePosition, map);

                amountOfWater = countStringElementsInList(rover.getGatheredResources(), waterSymbol);
                amountOfMinerals = countStringElementsInList(rover.getGatheredResources(), mineralSymbol);
            }else{
                break;
            }
        }
    }

    private int countStringElementsInList(List<String> stringList, String element){
        return (int) stringList.stream()
                .filter(s -> s.equals(element))
                .count();
    }
}

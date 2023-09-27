package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.simulator.service.*;

import java.util.List;

public class RoverBehaviourImpl implements RoverBehaviour{
    private final ExplorationSimulatorScannerImpl simulatorScanner;
    private final RoutineGenerator routineGenerator;
    private final OutcomeAnalyzer outcomeAnalyzer;
    private final SimulationMessages simulationMessages;
    private final MoveRover moveRover;
    private final BuildColony buildColony;
    private final GatheringService gatheringService;
    private final GenerateRandom generateRandom;

    public RoverBehaviourImpl(ExplorationSimulatorScannerImpl simulatorScanner,
                              RoutineGenerator routineGenerator,
                              OutcomeAnalyzer outcomeAnalyzer,
                              SimulationMessages simulationMessages,
                              MoveRover moveRover, BuildColony buildColony, GatheringService gatheringService, GenerateRandom generateRandom) {
        this.simulatorScanner = simulatorScanner;
        this.routineGenerator = routineGenerator;
        this.outcomeAnalyzer = outcomeAnalyzer;
        this.simulationMessages = simulationMessages;
        this.moveRover = moveRover;
        this.buildColony = buildColony;
        this.gatheringService = gatheringService;
        this.generateRandom = generateRandom;
    }

    @Override
    public void executeRoverRoutine(List<Coordinate> allMovesRoutine,
                                    List<Coordinate> movesDone,
                                    MarsRover rover,
                                    Map map,
                                    Coordinate destinationPlace) {
        switch (rover.getRoverType()){
            case EXPLORER -> executeExplorerRoverRoutine(allMovesRoutine,movesDone,rover, map);
            case BUILDER -> executeBuilderRoverRoutine(rover, map, destinationPlace);
            case GATHERER -> executeGathererRoverRoutine(rover, destinationPlace, map);
        }
    }

    private void executeExplorerRoverRoutine(List<Coordinate> allMovesRoutine,
                                             List<Coordinate> movesDone,
                                             MarsRover rover,
                                             Map map){
        ExplorerRoverBehaviour explorerRoverBehaviour = new ExplorerRoverBehaviourImpl(
                routineGenerator,
                moveRover,
                outcomeAnalyzer,
                simulationMessages,
                simulatorScanner,
                gatheringService,
                generateRandom);

        explorerRoverBehaviour.executeExplorerRoverBehaviour(allMovesRoutine,movesDone,rover, map);
    }

    private void executeBuilderRoverRoutine(MarsRover rover, Map map, Coordinate coordinateOfLastMaterial){
        BuilderRoverBehaviour builderRoverBehaviour = new BuilderRoverBehaviourImpl(
                buildColony,
                moveRover,
                routineGenerator);

        builderRoverBehaviour.executeBuilderRoverBehaviour(rover, map, coordinateOfLastMaterial);
    }

    private void executeGathererRoverRoutine(MarsRover marsRover, Coordinate resourcePosition, Map map){
        GathererRoverBehaviour gathererRoverBehaviour = new GathererRoverBehaviourImpl(gatheringService,
                routineGenerator,
                moveRover);

        gathererRoverBehaviour.executeGathererRoverBehaviour(marsRover, resourcePosition, map);
    }

}

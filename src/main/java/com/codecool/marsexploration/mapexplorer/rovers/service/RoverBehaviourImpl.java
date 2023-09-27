package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.simulator.service.ExplorationSimulatorScannerImpl;
import com.codecool.marsexploration.mapexplorer.simulator.service.SimulationMessages;

import java.util.List;

public class RoverBehaviourImpl implements RoverBehaviour{
    private final ExplorationSimulatorScannerImpl simulatorScanner;
    private final RoutineGenerator routineGenerator;
    private final OutcomeAnalyzer outcomeAnalyzer;
    private final SimulationMessages simulationMessages;
    private final MoveRover moveRover;

    public RoverBehaviourImpl(ExplorationSimulatorScannerImpl simulatorScanner,
                              RoutineGenerator routineGenerator,
                              OutcomeAnalyzer outcomeAnalyzer,
                              SimulationMessages simulationMessages,
                              MoveRover moveRover) {
        this.simulatorScanner = simulatorScanner;
        this.routineGenerator = routineGenerator;
        this.outcomeAnalyzer = outcomeAnalyzer;
        this.simulationMessages = simulationMessages;
        this.moveRover = moveRover;
    }

    @Override
    public void executeRoverRoutine(List<Coordinate> allMovesRoutine, List<Coordinate> movesDone, MarsRover rover) {
        switch (rover.getRoverType()){
            case EXPLORER -> executeExplorerRoverRoutine(allMovesRoutine,movesDone,rover);
            case BUILDER -> executeBuilderRoverRoutine();
            case GATHERER -> executeGathererRoverRoutine();
        }
    }

    private void executeExplorerRoverRoutine(List<Coordinate> allMovesRoutine,
                                             List<Coordinate> movesDone,
                                             MarsRover rover){
        ExplorerRoverBehaviour explorerRoverBehaviour = new ExplorerRoverBehaviourImpl(
                routineGenerator,
                moveRover,
                outcomeAnalyzer,
                simulationMessages,
                simulatorScanner);

        explorerRoverBehaviour.executeExplorerRoverBehaviour(allMovesRoutine,movesDone,rover);
    }

    private void executeBuilderRoverRoutine(){

    }

    private void executeGathererRoverRoutine(){

    }

}

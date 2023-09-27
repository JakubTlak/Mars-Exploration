package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.simulator.service.GatheringService;
import com.codecool.marsexploration.mapexplorer.simulator.service.GenerateRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GathererRoverBehaviourImpl implements GathererRoverBehaviour{
    private final GatheringService gatheringService;
    private final RoutineGenerator routineGenerator;
    private final MoveRover moveRover;

    public GathererRoverBehaviourImpl(GatheringService gatheringService,
                                      RoutineGenerator routineGenerator,
                                      MoveRover moveRover) {
        this.gatheringService = gatheringService;
        this.routineGenerator = routineGenerator;
        this.moveRover = moveRover;
    }

    @Override
    public void executeGathererRoverBehaviour(MarsRover marsRover, Coordinate resourcePosition, Map map) {
        Coordinate roverStartingPosition = marsRover.getCurrentPosition();

        List<Coordinate> moveList = routineGenerator.generateGatherRoutine(roverStartingPosition,
                resourcePosition);

        for (Coordinate coordinate: moveList) {
            moveRover.moveRover(marsRover, coordinate);
        }

        gatheringService.gatherResource(marsRover, resourcePosition, map);

    }

    private void transportMaterialsToBase(MarsRover marsRover, List<Coordinate> moveList){
        Collections.reverse(moveList);
        for (Coordinate coordinate: moveList){
            moveRover.moveRover(marsRover, coordinate);
        }
    }
}

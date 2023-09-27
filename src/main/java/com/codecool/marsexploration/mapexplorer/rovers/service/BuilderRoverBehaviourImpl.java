package com.codecool.marsexploration.mapexplorer.rovers.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.simulator.service.BuildColony;
import com.codecool.marsexploration.mapelements.model.Map;

import java.util.List;

public class BuilderRoverBehaviourImpl implements BuilderRoverBehaviour{
    private final BuildColony buildColony;
    private final MoveRover moveRover;
    private final RoutineGenerator routineGenerator;

    public BuilderRoverBehaviourImpl(BuildColony buildColony, MoveRover moveRover, RoutineGenerator routineGenerator) {
        this.buildColony = buildColony;
        this.moveRover = moveRover;
        this.routineGenerator = routineGenerator;
    }

    @Override
    public void executeBuilderRoverBehaviour(MarsRover rover,Map map, Coordinate coordinateOfLastMaterial) {
        if(buildColony.canBuild(rover)){
            moveToConstructionSite(coordinateOfLastMaterial, rover);
            buildColony.build(rover, map);
        }
    }

    private void moveToConstructionSite(Coordinate coordinateOfLastMaterial, MarsRover rover){
        List<Coordinate> moveList = routineGenerator.generateGatherRoutine(rover.getCurrentPosition(),
                buildColony.constructionPlace(coordinateOfLastMaterial));

        for (Coordinate coordinate: moveList) {
            moveRover.moveRover(rover,coordinate);
        }
    }
}

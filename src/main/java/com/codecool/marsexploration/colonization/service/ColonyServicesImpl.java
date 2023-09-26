package com.codecool.marsexploration.colonization.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.rovers.model.RoverType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColonyServicesImpl implements ColonyServices{
    private final Random random = new Random();
    private final CoordinateCalculator coordinateCalculator;

    public ColonyServicesImpl(CoordinateCalculator coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }

    @Override
    public MarsRover buildRover(Coordinate colonyCoordinates,
                                List<MapElement> colonyResources,
                                List<MapElement> resourcesNeededToBuildRover,
                                String[][] map,
                                RoverType roverType) {

        int sight = ConstantValues.ROVER_SIGHT;
        removeResourcesFromColonyStock(colonyResources, resourcesNeededToBuildRover);
        Coordinate deployPosition = getDeployPosition(colonyCoordinates, map);

        return new MarsRover(deployPosition, sight, roverType);
    }

    private void removeResourcesFromColonyStock(List<MapElement> colonyResources,
                                                List<MapElement> resourcesNeededToBuildRover){

        if(colonyResources.containsAll(resourcesNeededToBuildRover)) {
            for (MapElement mapElement : resourcesNeededToBuildRover) {
                colonyResources.remove(mapElement);
            }
        }
    }

    private Coordinate getDeployPosition(Coordinate colonyCoordinates, String[][] map){
        int dimension = ConstantValues.ROVER_DEPLOY_DIMENSION;

        Iterable<Coordinate> adjacentCoordinates = coordinateCalculator.getAdjacentCoordinates(
                colonyCoordinates,
                dimension);

        List<Coordinate> coordinateList = new ArrayList<>();
        for (Coordinate coordinate : adjacentCoordinates) {
            coordinateList.add(coordinate);
        }

        while(!coordinateList.isEmpty()){
            int randomIndex = random.nextInt(coordinateList.size());
            Coordinate deployCoordinate = coordinateList.get(randomIndex);

            if(canPlaceRover(deployCoordinate, map)){
                return deployCoordinate;
            }
            coordinateList.remove(randomIndex);
        }
        return null;
    }

    private boolean canPlaceRover(Coordinate colonyCoordinates, String[][] map){
        return map[colonyCoordinates.x()][colonyCoordinates.y()].equals(ConstantValues.EMPTY_FIELD_SYMBOL);
    }
}

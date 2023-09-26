package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.colonization.model.Colony;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.rovers.model.RoverType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class BuildColonyImpl implements BuildColony{
    private Map mapx;
    Random random = new Random();
    private CoordinateCalculator coordinateCalculator;
    @Override
    public boolean canBuild(MarsRover rover) {
        int amountOfWater = 0;
        int amountOfMinerals = 0;

        for(int i =0;i<rover.getGatheredResources().size();i++){
            if(rover.getGatheredResources().get(i) == "*"){
                amountOfWater ++;
            }else if(rover.getGatheredResources().get(i) == "%"){
                amountOfMinerals++;
            }
        }

        if(amountOfWater >= 3 && amountOfMinerals >= 3 && rover.getRoverType() == RoverType.BUILDER){
            return true;
        }
        return false;
    }

    @Override
    public Coordinate construcionSite(Coordinate coordinateOfLastMaterial) {
        List<Coordinate> adjacent = (List<Coordinate>) coordinateCalculator.getAdjacentCoordinates(coordinateOfLastMaterial,64);

        Coordinate toReturn = null;

        for(int i = 0 ;i< adjacent.size();i++){
            if(mapx.isEmpty(adjacent.get(i))){
                toReturn = adjacent.get(i);
                break;
            }
        }
        return toReturn;
    }

    @Override
    public Colony build(MarsRover rover, Map map) {
        Set<Coordinate> materialsCoordinate = rover.getResourceCoordinates().get(random.nextInt(1));

        Coordinate[] materialsCoordinateArray = materialsCoordinate.toArray(new Coordinate[materialsCoordinate.size()]);

        if(materialsCoordinateArray.length > 0){
            System.out.println("odpowiednio przeksztalcone");
        }

        Coordinate lastCoordinate = materialsCoordinateArray[materialsCoordinateArray.length - 1];
        Coordinate coordinateToPlace = construcionSite(lastCoordinate);
        String[][] representation = map.getRepresentation();


        if(canBuild(rover)){
            representation[coordinateToPlace.x()][coordinateToPlace.y()] = "♛";
            Colony newColony = new Colony(coordinateToPlace);
            return newColony;
        }
        return null;
    }
}

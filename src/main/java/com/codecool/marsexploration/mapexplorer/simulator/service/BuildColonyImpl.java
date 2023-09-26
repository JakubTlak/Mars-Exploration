package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
            }else if{
                amountOfMinerals++;
            }
        }

        if(amountOfWater >= 3 && amountOfMinerals >= 3 && rover.getType() == "builder"){
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
    public void build(MarsRover rover, Map map) {
        List<Coordinate> materialsCoordinate = rover.getResourceCoordinates().get(random.nextInt(1));
        Coordinate lastCoordinate = materialsCoordinate.get(materialsCoordinate.size());

        if(canBuild(rover)){
            Coordinate coordinateToPlace = construcionSite(lastCoordinate);

            String[][] representation = map.getRepresentation();


        }
    }
}

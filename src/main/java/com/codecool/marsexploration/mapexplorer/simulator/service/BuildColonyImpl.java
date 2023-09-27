package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.colonization.model.Colony;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapexplorer.rovers.model.RoverType;

import java.util.List;

public class BuildColonyImpl implements BuildColony{
    private final GenerateRandom generateRandom;
    private final CoordinateCalculator coordinateCalculator;
    private final Map map;
    private final TransformMethods transformMethods = new TransformMethodsImpl();

    public BuildColonyImpl(GenerateRandom generateRandom,
                           CoordinateCalculator coordinateCalculator,
                           Map map) {
        this.generateRandom = generateRandom;
        this.coordinateCalculator = coordinateCalculator;
        this.map = map;
    }

    @Override
    public boolean canBuild(MarsRover rover) {
        final String mineralSymbol = ConstantValues.MINERAL_SYMBOL;
        final String waterSymbol = ConstantValues.WATER_SYMBOL;
        final int neededMinerals = ConstantValues.MINERALS_TO_SET_UP_COLONY;
        final int neededWater = ConstantValues.WATER_TO_SET_UP_COLONY;

        int amountOfWater = 0;
        int amountOfMinerals = 0;

        for(int i =0;i<rover.getGatheredResources().size();i++){
            if(rover.getGatheredResources().get(i) == waterSymbol){
                amountOfWater ++;
            }else if(rover.getGatheredResources().get(i) == mineralSymbol){
                amountOfMinerals++;
            }
        }

        if(amountOfWater >= neededWater && amountOfMinerals >= neededMinerals
                && rover.getRoverType() != RoverType.GATHERER){
            return true;
        }
        return false;
    }

    @Override
    public Coordinate constructionPlace(Coordinate coordinateOfLastMaterial) {
        List<Coordinate> adjacent = (List<Coordinate>) coordinateCalculator.getAdjacentCoordinates(coordinateOfLastMaterial,64);

        Coordinate toReturn = null;

        for(int i = 0 ;i< adjacent.size();i++){
            if(map.isEmpty(adjacent.get(i))){
                toReturn = adjacent.get(i);
                break;
            }
        }
        return toReturn;
    }

    @Override
    public Colony build(MarsRover rover, Map map) {

//        Set<Coordinate> materialsCoordinate = rover.getResourceCoordinates().get(random.nextInt(1));
//
//        Coordinate[] materialsCoordinateArray = materialsCoordinate.toArray(new Coordinate[materialsCoordinate.size()]);
//
//        if(materialsCoordinateArray.length > 0){
//            System.out.println("odpowiednio przeksztalcone");
//        }
//
//        Coordinate lastCoordinate = materialsCoordinateArray[materialsCoordinateArray.length - 1];
        List<Coordinate> scannedResourcesCoordinates = transformMethods.mapStringSetOfCoordinatesToListOfCoordinates(
                rover.getResourceCoordinates());

        Coordinate coordinateToPlace = constructionPlace(generateRandom.getRandomMineralCoordinates(scannedResourcesCoordinates));
        String[][] representation = map.getRepresentation();


        if(canBuild(rover)){
            representation[coordinateToPlace.x()][coordinateToPlace.y()] = "♛";
            Colony newColony = new Colony(coordinateToPlace);
            System.out.println(map);
            return newColony;
        }
        return null;
    }

}

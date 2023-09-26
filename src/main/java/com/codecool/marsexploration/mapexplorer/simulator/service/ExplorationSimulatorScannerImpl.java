package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapelements.model.Map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ExplorationSimulatorScannerImpl implements ExplorationSimulatorScanner{

    private final CoordinateCalculator coordinateCalculator;
    private final Map map;

    public ExplorationSimulatorScannerImpl(CoordinateCalculator coordinateCalculator, Map map) {
        this.coordinateCalculator = coordinateCalculator;
        this.map = map;
    }

    @Override
    public void scanForResources(MarsRover rover) {
        final String mineralSymbol = ConstantValues.MINERAL_SYMBOL;
        final String waterSymbol = ConstantValues.WATER_SYMBOL;

        int sight = rover.getSight();
        int size = map.getRepresentation().length;
        ArrayList<Coordinate> allCoordinatesInSight = new ArrayList<>();

        Coordinate firstPosition = rover.getCurrentPosition();
        allCoordinatesInSight.add(firstPosition);

        for(int i= 0;i<=sight;i++){
            if(i == 0){
                allCoordinatesInSight.addAll((Collection<? extends Coordinate>) coordinateCalculator.getAdjacentCoordinates(firstPosition,size));
            }else {
                ArrayList<Coordinate> coordinates = (ArrayList<Coordinate>) coordinateCalculator.getAdjacentCoordinates(allCoordinatesInSight, size);
                allCoordinatesInSight.addAll(coordinates);
            }
        }

        ArrayList<Coordinate> coordinatesOfWater = new ArrayList<>();
        ArrayList<Coordinate> coordinatesOfMinerals = new ArrayList<>();


        allCoordinatesInSight.forEach(coordinate ->{
            if(Objects.equals(map.getByCoordinate(coordinate), waterSymbol)){
                coordinatesOfWater.add(coordinate);
            }
            if(Objects.equals(map.getByCoordinate(coordinate), mineralSymbol)){
                coordinatesOfMinerals.add(coordinate);
            }
        });

        rover.addResourceCoordinate(waterSymbol, coordinatesOfWater);
        rover.addResourceCoordinate(mineralSymbol, coordinatesOfMinerals);

    }
}

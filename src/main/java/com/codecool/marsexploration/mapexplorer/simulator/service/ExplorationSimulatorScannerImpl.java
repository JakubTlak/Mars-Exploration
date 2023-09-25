package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapelements.model.Map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ExplorationSimulatorScannerImpl implements ExplorationSimulatorScanner{

    private final CoordinateCalculator coordinateCalculator;
    private final Map mapx;

    public ExplorationSimulatorScannerImpl(CoordinateCalculator coordinateCalculator, Map mapx) {
        this.coordinateCalculator = coordinateCalculator;
        this.mapx= mapx;
    }

    @Override
    public void scanForResources(MarsRover rover) {
        int sight = rover.getSight();
        int size = mapx.getRepresentation().length;
        ArrayList<Coordinate> allCoordinatesInSight = new ArrayList<>();

        Coordinate firstPosition = rover.getCurrentPosition();
        allCoordinatesInSight.add(firstPosition);

        for(int i= 0;i<=sight;i++){
            if(i == 0){
                allCoordinatesInSight.addAll((Collection<? extends Coordinate>) coordinateCalculator.getAdjacentCoordinates(firstPosition,size));
            }else {
                ArrayList<Coordinate> coordy = (ArrayList<Coordinate>) coordinateCalculator.getAdjacentCoordinates(allCoordinatesInSight, size);
                allCoordinatesInSight.addAll(coordy);
            }
        }

        ArrayList<Coordinate> coordinatesOfWater = new ArrayList<>();
        ArrayList<Coordinate> coordinatesOfMinerals = new ArrayList<>();


        allCoordinatesInSight.forEach(coordinate ->{
            if(Objects.equals(mapx.getByCoordinate(coordinate), "*")){
                coordinatesOfWater.add(coordinate);
            }
            if(Objects.equals(mapx.getByCoordinate(coordinate), "%")){
                coordinatesOfMinerals.add(coordinate);
            }
        });

        rover.addResourceCoordinate("*", coordinatesOfWater);
        rover.addResourceCoordinate("%", coordinatesOfMinerals);

    }
}

package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;



public class GatheringServiceImpl implements GatheringService{
    @Override
    public void gatherResource(MarsRover marsRover, Coordinate coordinate, Map map) {
        marsRover.gatherResource(map.getByCoordinate(coordinate));
        map.changeMapElement(coordinate, " ");
        }
    }


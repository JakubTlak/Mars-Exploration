package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.Map;
import java.util.Set;

public interface GatheringService {
    void gatherResources(Map<String, Set<Coordinate>> resources, MarsRover marsRover);
}

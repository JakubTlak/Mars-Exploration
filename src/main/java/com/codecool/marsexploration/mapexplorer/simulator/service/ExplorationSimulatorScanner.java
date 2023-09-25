package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

import java.util.Collection;
import java.util.HashMap;

public interface ExplorationSimulatorScanner {
    public void scanForResources(MarsRover rover);
}

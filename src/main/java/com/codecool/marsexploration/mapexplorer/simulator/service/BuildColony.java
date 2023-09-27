package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.colonization.model.Colony;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;
import com.codecool.marsexploration.mapelements.model.Map;

import java.util.List;


public interface BuildColony {
    public boolean canBuild(MarsRover rover);
    public Coordinate construcionSite(Coordinate coordinateOfLastMAterial);
    public Colony build(MarsRover rover, Map map);
}

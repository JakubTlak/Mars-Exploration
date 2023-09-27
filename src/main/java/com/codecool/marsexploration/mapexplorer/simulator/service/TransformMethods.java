package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TransformMethods {
    public List<Coordinate> mapStringSetOfCoordinatesToListOfCoordinates(
            Map<String, Set<Coordinate>> resourceCoordinates) ;
}

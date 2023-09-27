package com.codecool.marsexploration.mapexplorer.simulator.service;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransformMethodsImpl implements TransformMethods{

    @Override
    public List<Coordinate> mapStringSetOfCoordinatesToListOfCoordinates(Map<String, Set<Coordinate>> resourceCoordinates) {
        return new ArrayList<>(resourceCoordinates.values()
                .stream()
                .flatMap(Set::stream).toList());
    }
}

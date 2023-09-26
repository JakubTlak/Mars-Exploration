package com.codecool.marsexploration.colonization.model;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.ArrayList;
import java.util.List;

public class Colony {
    private final Coordinate colonyCoordinates;
    private List<MapElement> resourceStock = new ArrayList<>();

    public Colony(Coordinate colonyCoordinates) {
        this.colonyCoordinates = colonyCoordinates;
    }

    public Coordinate getColonyCoordinates() {
        return colonyCoordinates;
    }

    public List<MapElement> getResourceStock() {
        return resourceStock;
    }

    public void setResourceStock(List<MapElement> resourceStock) {
        this.resourceStock = resourceStock;
    }
}

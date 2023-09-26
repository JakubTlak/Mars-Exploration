package com.codecool.marsexploration.colonization.model;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Colony {
    private final Coordinate colonyCoordinates;
    private List<String> resourceStock = new ArrayList<>();

    public Colony(Coordinate colonyCoordinates) {
        this.colonyCoordinates = colonyCoordinates;
    }

    public Coordinate getColonyCoordinates() {
        return colonyCoordinates;
    }

    public List<String> getResourceStock() {
        return resourceStock;
    }

    public void setResourceStock(List<String> resourceStock) {
        this.resourceStock = resourceStock;
    }
}

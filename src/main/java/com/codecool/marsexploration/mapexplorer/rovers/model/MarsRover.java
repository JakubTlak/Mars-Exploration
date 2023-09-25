package com.codecool.marsexploration.mapexplorer.rovers.model;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.*;

public class MarsRover {
    public int idCount = 1;
    private final String rover_id;
    private Coordinate currentPosition;
    private final int sight;
    private final Map<String, Set<Coordinate>> resourceCoordinates = new HashMap<>();

    public MarsRover(Coordinate currentPosition, int sight) {
        this.rover_id = "rover-" + idCount;
        this.currentPosition = currentPosition;
        this.sight = sight;
        idCount++;
    }

    public Map<String, Set<Coordinate>> getResourceCoordinates() {
        return Collections.unmodifiableMap(resourceCoordinates);
    }

    public String getRover_id() {
        return rover_id;
    }

    public Coordinate getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Coordinate newPosition) {
        this.currentPosition = newPosition;
    }

    public int getSight() {
        return sight;
    }

    public void addResourceCoordinate(String resourceSymbol,List<Coordinate> foundResources) {
        resourceCoordinates.computeIfAbsent(resourceSymbol, k -> new HashSet<>())
                .addAll(foundResources);
    }

    @Override
    public String toString() {
        return "MarsRover{" +
                "rover_id='" + rover_id + '\'' +
                ", currentPosition=" + currentPosition +
                ", sight=" + sight +
                ", resourceCoordinates=" + resourceCoordinates +
                '}';
    }
}

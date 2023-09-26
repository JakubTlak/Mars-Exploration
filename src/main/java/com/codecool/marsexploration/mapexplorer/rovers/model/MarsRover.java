package com.codecool.marsexploration.mapexplorer.rovers.model;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.*;

public class MarsRover {
    public int idCount = 1;
    private final String rover_id;
    private Coordinate currentPosition;
    private final int sight;
    private final Map<String, List<Coordinate>> resourceCoordinates = new HashMap<>();

    private ArrayList<String> gatheredResources = new ArrayList<>();

    public MarsRover(Coordinate currentPosition, int sight, Map<String, List<Coordinate>> resourceCoordinates) {
//        this.resourceCoordinates = resourceCoordinates;
        this.rover_id = "rover-" + idCount;
        this.currentPosition = currentPosition;
        this.sight = sight;

        idCount++;
    }

    public Map<String, List<Coordinate>> getResourceCoordinates() {
        return resourceCoordinates;
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

    public ArrayList<String> getGatheredResources() {
        return gatheredResources;
    }

    public void setGatheredResources(ArrayList<String> gatheredResources) {
        this.gatheredResources = gatheredResources;
    }

    public void addResourceCoordinate(String resourceSymbol, List<Coordinate> foundResources) {

        if(resourceCoordinates.get(resourceSymbol) == null){
            resourceCoordinates.put(resourceSymbol,new ArrayList<>(new HashSet<>(foundResources)));
        }
        else {
            List<Coordinate> allCoordinates = new ArrayList<>();
            allCoordinates.addAll(resourceCoordinates.get(resourceSymbol));
            allCoordinates.addAll(foundResources);
            resourceCoordinates.put(resourceSymbol,new ArrayList<>(new HashSet<>(allCoordinates)));
        }

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

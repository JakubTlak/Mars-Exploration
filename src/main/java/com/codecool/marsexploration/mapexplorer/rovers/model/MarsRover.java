package com.codecool.marsexploration.mapexplorer.rovers.model;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.*;

public class MarsRover {
    public int idCount = 1;
    private int currentStep = 0;
    private final String rover_id;
    private Coordinate currentPosition;
    private final int sight;
    private final Map<String, Set<Coordinate>> resourceCoordinates = new HashMap<>();

    private final RoverType roverType;

    private List<String> gatheredResources;



    public MarsRover(Coordinate currentPosition, int sight, RoverType type) {
        this.rover_id = "rover-" + idCount;
        this.currentPosition = currentPosition;
        this.sight = sight;
        this.roverType = type;
        gatheredResources = new ArrayList<>();
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

    public List<String> getGatheredResources() {
        return gatheredResources;
    }

    public void setGatheredResources(ArrayList<String> gatheredResources) {
        this.gatheredResources = gatheredResources;
    }


    public RoverType getRoverType() {
        return roverType;
    }


    public void addResourceCoordinate(String resourceSymbol, List<Coordinate> foundResources) {
        resourceCoordinates.computeIfAbsent(resourceSymbol, k -> new HashSet<>())
                .addAll(foundResources);
    }

    public void gatherResource(String resource){
        gatheredResources.add(resource);
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

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }
}

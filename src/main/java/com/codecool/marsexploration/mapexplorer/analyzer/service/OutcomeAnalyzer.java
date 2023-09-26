package com.codecool.marsexploration.mapexplorer.analyzer.service;

import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

public interface OutcomeAnalyzer {
    public boolean hasReachedTimeout(int currentStep);
    public boolean meetsColonizationConditions(MarsRover marsRover);
    public boolean hasNotEnoughResources(MarsRover marsRover, int currentStep);
}

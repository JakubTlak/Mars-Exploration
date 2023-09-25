package com.codecool.marsexploration.mapexplorer.analyzer.service;

import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

public interface OutcomeAnalyzer {
    public boolean hasReachedTimeout(int currentStep, int totalSteps);
    public boolean meetsColonizationConditions(String mineralSymbol, String waterSymbol, MarsRover marsRover);
    public boolean hasNotEnoughResources(String mineralSymbol,
                                         String waterSymbol,
                                         MarsRover marsRover,
                                         int currentStep,
                                         int totalSteps);
}

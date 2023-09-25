package com.codecool.marsexploration.mapexplorer.analyzer.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

public class OutcomeAnalyzerImpl implements OutcomeAnalyzer{

    /**
     * @param currentStep of rover
     * @param totalSteps that rover can make before timeout
     * @return true if currentStep is greater than totalSteps, false otherwise
     */
    @Override
    public boolean hasReachedTimeout(int currentStep, int totalSteps) {
        return currentStep >= totalSteps;
    }

    /**
     * @param mineralSymbol String representation of mineral
     * @param waterSymbol String representation of water
     * @param marsRover
     * @return true if rover found at least 4 minerals and 3 water fields, false otherwise
     */
    @Override
    public boolean meetsColonizationConditions(String mineralSymbol, String waterSymbol, MarsRover marsRover) {
        boolean condition = marsRover.getResourceCoordinates().get(mineralSymbol).size() >= 4
                && marsRover.getResourceCoordinates().get(waterSymbol).size() >= 3;
        return condition;
    }

    /**
     * @param mineralSymbol String representation of mineral
     * @param waterSymbol String representation of water
     * @param marsRover
     * @param currentStep of rover
     * @param totalSteps that rover can make before timeout
     * @return true if colonization conditions are not met and current step is higher than 90% of max rover steps,
     * false otherwise
     */
    @Override
    public boolean hasNotEnoughResources(String mineralSymbol,
                                         String waterSymbol,
                                         MarsRover marsRover,
                                         int currentStep,
                                         int totalSteps) {
        boolean condition = !meetsColonizationConditions(mineralSymbol, waterSymbol, marsRover)
                && currentStep > 0.9 * totalSteps;
        return condition;
    }
}

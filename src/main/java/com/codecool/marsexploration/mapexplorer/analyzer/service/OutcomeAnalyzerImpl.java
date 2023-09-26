package com.codecool.marsexploration.mapexplorer.analyzer.service;

import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapexplorer.rovers.model.MarsRover;

public class OutcomeAnalyzerImpl implements OutcomeAnalyzer{
    private final int totalSteps = ConstantValues.TOTAL_STEPS;

    /**
     * @param currentStep of rover
     * @return true if currentStep is greater than totalSteps, false otherwise
     */
    @Override
    public boolean hasReachedTimeout(int currentStep) {
        return currentStep >= totalSteps;
    }

    /**
     * @param marsRover
     * @return true if rover found at least 4 minerals and 3 water fields, false otherwise
     */
    @Override
    public boolean meetsColonizationConditions(MarsRover marsRover) {
        final String mineralSymbol = ConstantValues.MINERAL_SYMBOL;
        final String waterSymbol = ConstantValues.WATER_SYMBOL;

        boolean condition = marsRover.getResourceCoordinates().get(mineralSymbol).size() >= 4
                && marsRover.getResourceCoordinates().get(waterSymbol).size() >= 3;
        return condition;
    }

    /**
     * @param marsRover
     * @param currentStep of rover
     * @return true if colonization conditions are not met and current step is higher than 90% of max rover steps,
     * false otherwise
     */
    @Override
    public boolean hasNotEnoughResources(MarsRover marsRover,
                                         int currentStep) {
        boolean condition = !meetsColonizationConditions(marsRover)
                && currentStep > 0.9 * totalSteps;
        return condition;
    }
}

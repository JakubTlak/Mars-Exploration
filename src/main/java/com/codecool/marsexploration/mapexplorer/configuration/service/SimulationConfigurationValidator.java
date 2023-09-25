package com.codecool.marsexploration.mapexplorer.configuration.service;


import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;

public interface SimulationConfigurationValidator {
    boolean validate(SimulationConfiguration simulationConfiguration);
}

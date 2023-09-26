package com.codecool.marsexploration.mapexplorer.configuration.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.logger.service.ConsoleLogger;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoader;


public class SimulationConfigurationValidatorImpl implements SimulationConfigurationValidator {

    private final MapLoader mapLoader;
    private final ConsoleLogger logger;
    private final String filePath;

    public SimulationConfigurationValidatorImpl(MapLoader mapLoader,
                                                ConsoleLogger logger,
                                                String filePath) {
        this.mapLoader = mapLoader;
        this.logger = logger;
        this.filePath = filePath;
    }

    /**
     * @return true if landing simulation config is correct, false otherwise
     */
    @Override
    public boolean validate(SimulationConfiguration simulationConfiguration) {
        Coordinate landingSpot = simulationConfiguration.landingCoordinate();
        Map map = mapLoader.load(filePath);

        boolean validation = isLandingSpotEmpty(map, landingSpot)
                && hasLandingSpotEmptyAdjacentField(map, landingSpot)
                && isMapPathNotEmpty()
                && isResourceSpecified(simulationConfiguration)
                && isTimeoutGreaterThanZero(simulationConfiguration);

        if (validation) {
            logger.logInfo("Landing simulation: SUCCESSFUL");
        } else {
            logger.logError("Landing simulation: FAILED");
        }
        return validation;
    }

    /**
     * @param coordinate landing spot coordinates
     * @param map        map to check
     * @return true if landing spot is empty, false otherwise
     */
    private boolean isLandingSpotEmpty(Map map, Coordinate coordinate) {
        String coordinateValue = map.getByCoordinate(coordinate);
        boolean condition = coordinateValue.equals(" ");
        if (!condition) {
            logger.logError("Spot empty: FAILED");
        }
        return condition;
    }

    /**
     * @param map        map to check
     * @param coordinate landing spot coordinates
     * @return true if landing spot has empty adjacent field, false otherwise
     */
    private boolean hasLandingSpotEmptyAdjacentField(Map map, Coordinate coordinate) {
        int coordinateX = coordinate.x();
        int coordinateY = coordinate.y();

        boolean isEmptyAdjacentSpot = map.isEmpty(new Coordinate(coordinateX + 1, coordinateY)) ||
                map.isEmpty(new Coordinate(coordinateX - 1, coordinateY)) ||
                map.isEmpty(new Coordinate(coordinateX, coordinateY + 1)) ||
                map.isEmpty(new Coordinate(coordinateX, coordinateY - 1));

        if (!isEmptyAdjacentSpot) {
            logger.logError("Empty adjacent spot: FAILED");
        }
        return isEmptyAdjacentSpot;
    }

    /**
     * @return true if file path is not empty, false otherwise
     */
    private boolean isMapPathNotEmpty() {
        boolean condition = !filePath.trim().isEmpty();
        if (!condition) {
            logger.logError("File path not empty: FAILED");
        }
        return condition;
    }

    /**
     * @return true if list of resources is not empty, false otherwise
     */
    private boolean isResourceSpecified(SimulationConfiguration simulationConfiguration) {
        boolean condition = !simulationConfiguration.symbolsToScan().isEmpty();
        if (!condition) {
            logger.logError("Found resources to scan: FAILED");
        }
        return condition;
    }

    /**
     * @return true if simulations steps are greater than zero, false otherwise
     */
    private boolean isTimeoutGreaterThanZero(SimulationConfiguration simulationConfiguration) {
        boolean condition = simulationConfiguration.simulationStepsToTimeout() > 0;
        if (!condition) {
            logger.logError("Timeout greater than zero: FAILED");
        }
        return condition;
    }
}

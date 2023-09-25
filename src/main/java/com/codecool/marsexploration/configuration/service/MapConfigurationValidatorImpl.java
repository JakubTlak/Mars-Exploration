package com.codecool.marsexploration.configuration.service;

import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import org.atteo.evo.inflector.English;

import java.util.HashSet;
import java.util.Set;

public class MapConfigurationValidatorImpl implements MapConfigurationValidator {
    private final ConsoleLoggerImpl consoleLogger;

    public MapConfigurationValidatorImpl(ConsoleLoggerImpl consoleLogger) {
        this.consoleLogger = consoleLogger;
    }

    @Override
    public boolean validate(MapConfiguration mapConfig) {
        boolean validation = validateAllElements(mapConfig) && validateTotalNumbersOfElements(mapConfig);

        if (validation) {
            consoleLogger.logInfo("Validation: SUCCESSFUL");
        } else {
            consoleLogger.logError("Validation: FAILED");
        }
        return validation;
    }

    private boolean validateAllElements(MapConfiguration mapConfig) {
        Set<String> totalElements = new HashSet<>();
        for (MapElementConfiguration element : mapConfig.mapElementConfigurations()) {
            switch (element.name()) {
                case "mountain" -> {
                    if (validateElement(element,
                            true,
                            "mountain",
                            "#",
                            "",
                            3
                    )) {
                        totalElements.add(element.name());
                    }
                }
                case "pit" -> {
                    if (validateElement(element,
                            true,
                            "pit",
                            "&",
                            "",
                            10)) {
                        totalElements.add(element.name());
                    }
                }
                case "mineral" -> {
                    if (validateElement(element,
                            false,
                            "mineral",
                            "%",
                            "#",
                            0)) {
                        totalElements.add(element.name());
                    }
                }
                case "water" -> {
                    if (validateElement(element,
                            false,
                            "water",
                            "*",
                            "&",
                            0)) {
                        totalElements.add(element.name());
                    }
                }
            }
        }
        return totalElements.size() == 4;
    }

    private boolean validateElement(
            MapElementConfiguration mapElementConfiguration,
            boolean multidimensional,
            String elementName,
            String elementSymbol,
            String preferredLocationSymbol,
            int dimensionGrowth
    ) {
        multidimensional = multidimensional == isElementMultiDimensional(mapElementConfiguration);
        boolean isValidate = multidimensional && isElementDataValid(
                mapElementConfiguration,
                elementName,
                elementSymbol,
                preferredLocationSymbol,
                dimensionGrowth);
        if (isValidate) {
            consoleLogger.logInfo(English.plural(mapElementConfiguration.name()) + " validation: SUCCESSFUL");
        } else {
            consoleLogger.logError(English.plural(mapElementConfiguration.name()) + " validation: FAILED");
        }
        return isValidate;
    }

    private boolean isElementDataValid(MapElementConfiguration mapElementConfiguration,
                                       String elementName,
                                       String elementSymbol,
                                       String preferredLocationSymbol,
                                       int dimensionGrowth) {
        return (mapElementConfiguration.name().equals(elementName)
                && mapElementConfiguration.symbol().equals(elementSymbol)
                && mapElementConfiguration.dimensionGrowth() == dimensionGrowth
                && mapElementConfiguration.preferredLocationSymbol().equals(preferredLocationSymbol));
    }

    private boolean isElementMultiDimensional(MapElementConfiguration mapElementConfiguration) {
        return mapElementConfiguration.elementToSizes().size() > 1;
    }

    private boolean validateTotalNumbersOfElements(MapConfiguration mapConfiguration) {
        int totalNumberOfElements = 0;
        double limitOfElements = mapConfiguration.mapSize() * mapConfiguration.elementToSpaceRatio();

        for (MapElementConfiguration mapElement : mapConfiguration.mapElementConfigurations()) {
            for (ElementToSize elementToSize : mapElement.elementToSizes()) {
                totalNumberOfElements += elementToSize.size();
            }
        }

        boolean isLessElementsThanLimit = totalNumberOfElements <= limitOfElements;

        if (isLessElementsThanLimit) {
            consoleLogger.logInfo("Number of total elements validation: SUCCESSFUL");
        }else{
            consoleLogger.logError("Number of total elements validation: FAILED");
        }

        return isLessElementsThanLimit;
    }
}

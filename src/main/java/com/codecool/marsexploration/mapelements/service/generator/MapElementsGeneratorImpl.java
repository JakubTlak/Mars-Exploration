package com.codecool.marsexploration.mapelements.service.generator;

import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilderImpl;

import java.util.ArrayList;
import java.util.List;

public class MapElementsGeneratorImpl implements MapElementsGenerator {
    private final ConsoleLoggerImpl consoleLogger;
    private final MapElementBuilderImpl mapElementBuilder;

    public MapElementsGeneratorImpl(ConsoleLoggerImpl consoleLogger, MapElementBuilderImpl mapElementBuilder) {
        this.consoleLogger = consoleLogger;
        this.mapElementBuilder = mapElementBuilder;
    }

    @Override
    public Iterable<MapElement> createAll(MapConfiguration mapConfig) {
        List<MapElement> allElements = new ArrayList<>();

        for (MapElementConfiguration elementCfg : mapConfig.mapElementConfigurations()) {
            for (ElementToSize elementToSize: elementCfg.elementToSizes()) {
                for (int i = 0; i < elementToSize.elementCount(); i++) {
                    allElements.add(mapElementBuilder.build(
                            elementToSize.size(),
                            elementCfg.symbol(),
                            elementCfg.name(),
                            elementCfg.dimensionGrowth(),
                            elementCfg.preferredLocationSymbol()
                    ));
                }
            }
        }
        consoleLogger.logInfo("Elements Generated");
        return allElements;
    }
}

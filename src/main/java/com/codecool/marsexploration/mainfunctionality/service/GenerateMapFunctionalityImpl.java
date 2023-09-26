package com.codecool.marsexploration.mainfunctionality.service;

import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.calculators.service.DimensionCalculatorImpl;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.service.MapConfigurationService;
import com.codecool.marsexploration.configuration.service.MapConfigurationServiceImpl;
import com.codecool.marsexploration.configuration.service.MapConfigurationValidatorImpl;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilderImpl;
import com.codecool.marsexploration.mapelements.service.generator.MapElementsGeneratorImpl;
import com.codecool.marsexploration.mapelements.service.generator.MapGenerator;
import com.codecool.marsexploration.mapelements.service.generator.MapGeneratorImpl;
import com.codecool.marsexploration.mapelements.service.placer.MapElementPlacerImpl;
import com.codecool.marsexploration.output.service.MapFileWriterImpl;

import java.io.IOException;

public class GenerateMapFunctionalityImpl implements GenerateMapFunctionality{
    private final ConsoleLoggerImpl consoleLogger;
    private final CoordinateCalculatorImpl coordinateCalculator;

    public GenerateMapFunctionalityImpl(ConsoleLoggerImpl consoleLogger,
                                        CoordinateCalculatorImpl coordinateCalculator) {
        this.consoleLogger = consoleLogger;
        this.coordinateCalculator = coordinateCalculator;
    }

    public void generateMap() throws IOException{

        System.out.println("Mars Exploration Sprint 1");
        MapConfigurationService mapConfigurationService = new MapConfigurationServiceImpl();
        MapConfiguration mapConfig = mapConfigurationService.getConfiguration();

        DimensionCalculatorImpl dimensionCalculator = new DimensionCalculatorImpl();


        MapElementBuilderImpl mapElementFactory = new MapElementBuilderImpl();
        MapElementsGeneratorImpl mapElementsGenerator = new MapElementsGeneratorImpl(consoleLogger, mapElementFactory);

        MapConfigurationValidatorImpl mapConfigValidator = new MapConfigurationValidatorImpl(consoleLogger);
        MapElementPlacerImpl mapElementPlacer = new MapElementPlacerImpl(coordinateCalculator);

        MapGenerator mapGenerator = new MapGeneratorImpl(
                mapElementFactory,
                mapElementsGenerator,
                mapElementPlacer,
                mapConfigValidator,
                dimensionCalculator,
                coordinateCalculator);

        if (!mapConfigValidator.validate(mapConfig)) {
            consoleLogger.logError("MAP CONFIG NOT ALLOWED");
            return;
        }

        int numberOfMaps = ConstantValues.GENERATE_MAP_QUANTITY;
        createAndWriteMaps(numberOfMaps, mapGenerator, mapConfig);
        System.out.println("Mars maps successfully generated.");
    }

    private void createAndWriteMaps(int count, MapGenerator mapGenerator, MapConfiguration mapConfig) throws IOException {
        String saveMapFolderPath = ConstantValues.SAVE_MAP_FOLDER_PATH;

        for (int i = 1; i <= count; i++) {
            String fileName = "map_" + i + ".map";

            MapFileWriterImpl mapFileWriter = new MapFileWriterImpl();

            mapFileWriter.writeMapFile(mapGenerator.generate(mapConfig), saveMapFolderPath + fileName);
        }
    }
}

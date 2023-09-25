package com.codecool.marsexploration;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.*;
import com.codecool.marsexploration.configuration.model.*;
import com.codecool.marsexploration.configuration.service.*;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import com.codecool.marsexploration.logger.service.FileLoggerImpl;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.service.builder.*;
import com.codecool.marsexploration.mapelements.service.generator.*;
import com.codecool.marsexploration.mapelements.service.placer.*;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.analyzer.service.OutcomeAnalyzerImpl;
import com.codecool.marsexploration.mapexplorer.configuration.model.SimulationConfiguration;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoader;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoaderImpl;
import com.codecool.marsexploration.mapexplorer.configuration.service.SimulationConfigurationValidatorImpl;
import com.codecool.marsexploration.mapexplorer.routines.RoutineGenerator;
import com.codecool.marsexploration.mapexplorer.rovers.service.RoverPlacer;
import com.codecool.marsexploration.mapexplorer.simulator.service.ExplorationSimulator;
import com.codecool.marsexploration.mapexplorer.simulator.service.ExplorationSimulatorImpl;
import com.codecool.marsexploration.mapexplorer.simulator.service.ExplorationSimulatorScannerImpl;
import com.codecool.marsexploration.output.service.MapFileWriterImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Application {
    private static final String WorkDir = "src/main";


    public static void main(String[] args) throws IOException {

        clearTextFile(WorkDir + "/resources/steps.txt");
        ConsoleLoggerImpl consoleLogger = new ConsoleLoggerImpl();

        System.out.println("Mars Exploration Sprint 1");
        MapConfiguration mapConfig = getConfiguration();

        DimensionCalculatorImpl dimensionCalculator = new DimensionCalculatorImpl();
        CoordinateCalculatorImpl coordinateCalculator = new CoordinateCalculatorImpl();

        MapElementBuilderImpl mapElementFactory = new MapElementBuilderImpl();
        MapElementsGeneratorImpl mapElementsGenerator = new MapElementsGeneratorImpl(consoleLogger, mapElementFactory);

        MapConfigurationValidatorImpl mapConfigValidator = new MapConfigurationValidatorImpl(consoleLogger);
        MapElementPlacerImpl mapElementPlacer = new MapElementPlacerImpl(coordinateCalculator);

        MapGenerator mapGenerator = new MapGeneratorImpl(mapElementFactory, mapElementsGenerator, mapElementPlacer, mapConfigValidator, dimensionCalculator, coordinateCalculator);

        if (!mapConfigValidator.validate(mapConfig)) {
            consoleLogger.logError("MAP CONFIG NOT ALLOWED");
            return;
        }
        createAndWriteMaps(3, mapGenerator, mapConfig);
        System.out.println("Mars maps successfully generated.");

        String mapFile = WorkDir + "/resources/map_1.map";
        Coordinate landingSpot = new Coordinate(6, 6);
        List<String> toScan = Arrays.asList("#", "&", "%", "*");
        int simulationSteps = 10;
        SimulationConfiguration simulation = new SimulationConfiguration(mapFile, landingSpot, toScan, simulationSteps);

        MapLoader mapLoader = new MapLoaderImpl();

        Map map = mapLoader.load(mapFile);

        int totalSteps = 600;
        int roverSight = 4;
        int mapDimension = 64;
        String mineralSymbol = "%";
        String waterSymbol = "*";


        RoutineGenerator routineGenerator = new RoutineGenerator(map, mapDimension);
        RoverPlacer roverPlacer = new RoverPlacer(simulation, map, mapDimension);
        OutcomeAnalyzer outcomeAnalyzer = new OutcomeAnalyzerImpl();
        SimulationConfigurationValidatorImpl simulationConfigurationValidator =
                new SimulationConfigurationValidatorImpl(mapLoader, consoleLogger, mapFile);
        ExplorationSimulatorScannerImpl simulatorScanner = new ExplorationSimulatorScannerImpl(coordinateCalculator,
                map);

        String loggFile = WorkDir + "/resources/steps.txt";
        FileLoggerImpl fileLogger = new FileLoggerImpl(loggFile);


        ExplorationSimulator explorationSimulator = new ExplorationSimulatorImpl(
                totalSteps,
                roverSight,
                mineralSymbol,
                waterSymbol,
                mapLoader,
                roverPlacer,
                outcomeAnalyzer,
                simulation,
                simulationConfigurationValidator,
                routineGenerator,
                simulatorScanner,
                fileLogger);

        explorationSimulator.simulateRoverExploration();
    }

    private static void createAndWriteMaps(int count, MapGenerator mapGenerator, MapConfiguration mapConfig) throws IOException {
        for (int i = 1; i <= count; i++) {
            String fileName = "map_" + i + ".map";

            MapFileWriterImpl mapFileWriter = new MapFileWriterImpl();

            mapFileWriter.writeMapFile(mapGenerator.generate(mapConfig), WorkDir + "/resources/" + fileName);
        }
    }

    private static MapConfiguration getConfiguration() {
        final String mountainSymbol = "#";
        final String pitSymbol = "&";
        final String mineralSymbol = "%";
        final String waterSymbol = "*";

        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                mountainSymbol,
                "mountain",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                3,
                ""
        );
        MapElementConfiguration pitCfg = new MapElementConfiguration(
                pitSymbol,
                "pit",
                List.of(
                        new ElementToSize(2, 10),
                        new ElementToSize(1, 20)
                ),
                10,
                ""
        );
        MapElementConfiguration mineralCfg = new MapElementConfiguration(
                mineralSymbol,
                "mineral",
                List.of(
                        new ElementToSize(10, 1)
                ),
                0,
                mountainSymbol
        );
        MapElementConfiguration waterCfg = new MapElementConfiguration(
                waterSymbol,
                "water",
                List.of(
                        new ElementToSize(10, 1)
                ),
                0,
                pitSymbol
        );


        List<MapElementConfiguration> elementsCfg = List.of(mountainsCfg, pitCfg, mineralCfg, waterCfg);
        return new MapConfiguration(4096, 0.5, elementsCfg);
    }

    private static void clearTextFile(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path, false);
            fileWriter.close();
            System.out.println("File cleared successfully.");
        } catch (IOException e) {
            System.err.println("Error clearing the file: " + e.getMessage());
        }
    }
}


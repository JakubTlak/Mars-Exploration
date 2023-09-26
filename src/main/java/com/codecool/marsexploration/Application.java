package com.codecool.marsexploration;

import com.codecool.marsexploration.calculators.service.*;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import com.codecool.marsexploration.mainfunctionality.service.GenerateMapFunctionality;
import com.codecool.marsexploration.mainfunctionality.service.GenerateMapFunctionalityImpl;
import com.codecool.marsexploration.mainfunctionality.service.RoverExplorationFunctionality;
import com.codecool.marsexploration.mainfunctionality.service.RoverExplorationFunctionalityImpl;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        ConsoleLoggerImpl consoleLogger = new ConsoleLoggerImpl();
        CoordinateCalculatorImpl coordinateCalculator = new CoordinateCalculatorImpl();

        GenerateMapFunctionality generateMapFunctionality = new GenerateMapFunctionalityImpl
                (consoleLogger,coordinateCalculator);
        RoverExplorationFunctionality explorationFunctionality = new RoverExplorationFunctionalityImpl
                (consoleLogger,coordinateCalculator);

        generateMapFunctionality.generateMap();
        explorationFunctionality.startRoverExploration();
    }


}


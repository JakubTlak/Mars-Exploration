package com.codecool.marsexploration.mapelements.service.generator;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.calculators.service.DimensionCalculatorImpl;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.service.MapConfigurationValidatorImpl;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilderImpl;
import com.codecool.marsexploration.mapelements.service.placer.MapElementPlacerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MapGeneratorImpl implements MapGenerator {

    private final MapElementBuilderImpl builder;
    private final MapElementsGeneratorImpl generator;
    private final MapElementPlacerImpl placer;
    private final MapConfigurationValidatorImpl validator;
    private final DimensionCalculatorImpl dimensionCalculator;
    private final CoordinateCalculatorImpl coordinateCalculator;

    @Override
    public String toString() {
        return "MapGeneratorImpl{" +
                "builder=" + builder +
                ", generator=" + generator +
                ", placer=" + placer +
                ", validator=" + validator +
                ", dimensionCalculator=" + dimensionCalculator +
                ", coordinateCalculator=" + coordinateCalculator +
                '}';
    }

    public MapGeneratorImpl(MapElementBuilderImpl builder, MapElementsGeneratorImpl generator, MapElementPlacerImpl placer, MapConfigurationValidatorImpl validator, DimensionCalculatorImpl dimensionCalculator, CoordinateCalculatorImpl coordinateCalculator) {
        this.builder = builder;
        this.generator = generator;
        this.placer = placer;
        this.validator = validator;
        this.dimensionCalculator = dimensionCalculator;
        this.coordinateCalculator = coordinateCalculator;
    }

    @Override
    public Map generate(MapConfiguration mapConfig) {
        int size = (int) Math.sqrt(mapConfig.mapSize());
        String[][] emptyMap = new String[size][size];
        String[][] representation = fillEmptyMap(emptyMap, size);
        ArrayList<MapElement> allElements = (ArrayList<MapElement>) generator.createAll(mapConfig);

        for (MapElement element : allElements) {

            Coordinate coordinate = coordinateCalculator.getRandomCoordinate(size);

            while (!placer.canPlaceElement(element, representation, coordinate)) {

                coordinate = coordinateCalculator.getRandomCoordinate(size);

            }
            placer.placeElement(element, representation, coordinate);
        }

        return new Map(representation);
    }

    private String[][] fillEmptyMap(String[][] map, int size) {
        final String emptyFieldSymbol = ConstantValues.EMPTY_FIELD_SYMBOL;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = emptyFieldSymbol;
            }
        }
        return map;
    }
}

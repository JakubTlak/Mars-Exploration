package com.codecool.marsexploration.mapelements.service.builder;

import com.codecool.marsexploration.calculators.service.DimensionCalculator;
import com.codecool.marsexploration.calculators.service.DimensionCalculatorImpl;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.Random;

public class MapElementBuilderImpl implements MapElementBuilder {
    @Override
    public MapElement build(int size, String symbol, String name, int dimensionGrowth, String preferredLocationSymbol) {
        final String emptyFieldSymbol = ConstantValues.EMPTY_FIELD_SYMBOL;
        DimensionCalculator dimensionCalculator = new DimensionCalculatorImpl();
        int dimension = dimensionCalculator.calculateDimension(size, dimensionGrowth);
        String[][] representation;
        if (size == 1) {
            representation = new String[][]{new String[]{symbol}};
        } else {
            representation = new String[dimension][dimension];
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    representation[i][j] = emptyFieldSymbol;
                }
            }
            Random random = new Random();
            int remainingSize = size;
            while (remainingSize > 0) {
                int row = random.nextInt(dimension);
                int column = random.nextInt(dimension);
                if (representation[row][column].equals(emptyFieldSymbol)) {
                    representation[row][column] = symbol;
                    remainingSize--;
                }
            }
        }
        return new MapElement(representation, name, dimension, preferredLocationSymbol);

    }
}

package com.codecool.marsexploration.mapelements.service.builder;

import com.codecool.marsexploration.calculators.service.DimensionCalculatorImpl;
import com.codecool.marsexploration.mapelements.model.MapElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapElementBuilderImplTest {
    private final MapElementBuilderImpl mapElementBuilder = new MapElementBuilderImpl();
    private final DimensionCalculatorImpl dimensionCalculator = new DimensionCalculatorImpl();

    @Test
    @DisplayName("Method: build() - Amount: 1 - Result: #")
    void buildOneMapElement() {
        //arrange
        String symbol = "#";
        String name = "";
        String preferredLocationSymbol = "";
        int size = 1;
        int dimensionGrowth = 0;
        MapElement mapElement = new MapElement(new String[][]{new String[]{symbol}},"",
                0,"");

        //act
        MapElement result = mapElementBuilder.build(size,symbol,name,dimensionGrowth,preferredLocationSymbol);

        //assert
        assertEquals(mapElement.toString(), result.toString());
    }

    @Test
    @DisplayName("Method: build() - Amount: 5 - Result: #####")
    void buildFiveElements(){
        //arrange
        String symbol = "#";
        String name = "";
        String preferredLocationSymbol = "";
        int size = 5;
        int dimensionGrowth = 1;
        int dimension = dimensionCalculator.calculateDimension(size, dimensionGrowth);

        //act
        MapElement result = mapElementBuilder.build(size,symbol,name,dimensionGrowth,preferredLocationSymbol);

        long hashCounter = result.toString().chars().filter(ch -> ch == '#').count();

        //assert
        assertEquals(dimension, result.getDimension());
        assertEquals("", result.getName());
        assertEquals("", result.getPreferredLocationSymbol());
        assertEquals(5, hashCounter);
    }

    @Test
    @DisplayName("Method: build() - Amount: 0 - Result: empty strings, ints 0")
    void buildZeroElements(){
        //arrange
        String symbol = "#";
        String name = "";
        String preferredLocationSymbol = "";
        int size = 0;
        int dimensionGrowth = 0;
        int dimension = dimensionCalculator.calculateDimension(size, dimensionGrowth);

        //act
        MapElement result = mapElementBuilder.build(size,symbol,name,dimensionGrowth,preferredLocationSymbol);

        long hashCounter = result.toString().chars().filter(ch -> ch == '#').count();

        //assert
        assertEquals(dimension, result.getDimension());
        assertEquals("", result.getName());
        assertEquals("", result.getPreferredLocationSymbol());
        assertEquals(0, hashCounter);
    }

    @Test
    @DisplayName("Method: build() - Null values for string - Result: strings null, ints 0")
    void stringParametersEqualsNull(){
        //arrange
        String symbol = null;
        String name = null;
        String preferredLocationSymbol = null;
        int size = 0;
        int dimensionGrowth = 0;
        int dimension = dimensionCalculator.calculateDimension(size, dimensionGrowth);

        //act
        MapElement result = mapElementBuilder.build(size,symbol,name,dimensionGrowth,preferredLocationSymbol);

        long hashCounter = result.toString().chars().filter(ch -> ch == '#').count();

        //assert
        assertEquals(dimension, result.getDimension());
        assertEquals(null, result.getName());
        assertEquals(null, result.getPreferredLocationSymbol());
        assertEquals(0, hashCounter);
    }
}
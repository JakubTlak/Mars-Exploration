package com.codecool.marsexploration.calculators.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DimensionCalculatorImplTest {

    @Test
    void calculateDimension() {
        DimensionCalculator dimensionCalculator = new DimensionCalculatorImpl();

        int result = dimensionCalculator.calculateDimension(8,2);
        assertEquals(4,result);

        int result1 = dimensionCalculator.calculateDimension(16,5);
        assertEquals(9,result1);

        int result2 = dimensionCalculator.calculateDimension(5,0);
        assertEquals(2,result2);

        int result3 = dimensionCalculator.calculateDimension(12,4);
        assertEquals(7,result3);

        int result4 = dimensionCalculator.calculateDimension(4,2);
        assertEquals(3,result4);

        int result5 = dimensionCalculator.calculateDimension(7,2);
        assertEquals(4,result5);
    }
}
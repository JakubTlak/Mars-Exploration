package com.codecool.marsexploration.calculators.service;

import com.codecool.marsexploration.calculators.model.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateCalculatorImplTest {

    private final CoordinateCalculator coordinateCalculator = new CoordinateCalculatorImpl();


    @Test
    void getRandomCoordinateReturnsCorrectCoordinate() {
        int dimension = 8;
        Coordinate randomCoordinate = coordinateCalculator.getRandomCoordinate(dimension);

        Coordinate[][] mapMock = new Coordinate[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                mapMock[i][j] = new Coordinate(i, j);
            }
        }

        boolean coordinateFound = false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (mapMock[i][j].equals(randomCoordinate)) {
                    coordinateFound = true;
                    break;
                }
            }
        }

        assertTrue(coordinateFound);
    }

    @Test
    void getAdjacentCoordinates() {
        int dimension = 3;
        Coordinate chosenCoordinates = new Coordinate(1, 1);
        List<Coordinate> expectedCoordinates = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i != 1 || j != 1) {
                    expectedCoordinates.add(new Coordinate(i, j));
                }
            }
        }
        Iterable<Coordinate> actualCoordinates = coordinateCalculator.getAdjacentCoordinates(chosenCoordinates, 3);
        assertTrue(expectedCoordinates.containsAll((Collection<?>) actualCoordinates));
    }

    @Test
    void testGetAdjacentCoordinates() {
        int dimension = 3;
        Coordinate chosenCoordinate1 = new Coordinate(1, 1);
        Coordinate chosenCoordinate2 = new Coordinate(1, 2);
        List<Coordinate> expectedCoordinates = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == 1 && j == 1) {
                    continue;
                }
                if (i == 1 && j == 2) {
                    continue;
                }
                expectedCoordinates.add(new Coordinate(i, j));
            }
        }
        List<Coordinate> chosenCoordinates = new ArrayList<>();
        chosenCoordinates.add(chosenCoordinate1);
        chosenCoordinates.add(chosenCoordinate2);
        Iterable<Coordinate> actualCoordinates = coordinateCalculator.getAdjacentCoordinates(chosenCoordinates, 3);
        assertTrue(expectedCoordinates.containsAll((Collection<?>) actualCoordinates));
    }
}
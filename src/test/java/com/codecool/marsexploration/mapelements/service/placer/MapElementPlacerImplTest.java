package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.mapelements.model.MapElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapElementPlacerImplTest {

    private final MapElementPlacerImpl mapElementPlacer = new MapElementPlacerImpl(new CoordinateCalculatorImpl());
    private MapElement mapElement;
    private String[][] map;
    private int arraySize;

    @BeforeEach
    void beforeEach(){
        mapElement = new MapElement(new String[][]{new String[]{"#"}},"",
                0,"");
        arraySize = 3;
        map = new String[arraySize][arraySize];

        //populate map with whitespace
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                map[i][j] = " ";
            }
        }
    }

    @Test
    @DisplayName("Method: canPlaceElement() - On empty field - Return: true")
    void canPlaceElementOnEmptyFieldReturnTrue() {
        //arrange
        Coordinate coordinate = new Coordinate(2,2);

        //act
        boolean canPlace = mapElementPlacer.canPlaceElement(mapElement, map, coordinate);

        //assert
        assertEquals(true, canPlace);
    }

    @Test
    @DisplayName("Method: canPlaceElement() - On occupied field - Return: false")
    void canPlaceElementOnOccupiedFieldReturnFalse() {
        //arrange
        Coordinate coordinate = new Coordinate(2,2);
        map[2][2] = "something";

        //act
        boolean canPlace = mapElementPlacer.canPlaceElement(mapElement, map, coordinate);

        //assert
        assertEquals(false, canPlace);
    }

    @Test
    @DisplayName("Method: canPlaceElement() - Coordinate out of map - Return: false")
    void canPlaceElementCoordinateOutOfRangeReturnFalse(){
        //arrange
        Coordinate coordinate = new Coordinate(4,4);

        //act
        boolean canPlace = mapElementPlacer.canPlaceElement(mapElement, map, coordinate);

        //assert
        assertEquals(false, canPlace);
    }

    @Test
    @DisplayName("Method: placeElement() - On empty field - Return: true")
    void placeElementOnEmptyFieldReturnTrue() {
        //arrange
        Coordinate coordinate = new Coordinate(2,2);

        //act
        mapElementPlacer.placeElement(mapElement, map, coordinate);

        long hashCounter = map[2][2].toString().chars().filter(ch -> ch == '#').count();
        System.out.println(map[2][2]);
        //assert
        assertEquals(1, hashCounter);
    }
}


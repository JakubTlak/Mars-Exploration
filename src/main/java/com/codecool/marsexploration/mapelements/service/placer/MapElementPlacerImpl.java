package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.*;

public class MapElementPlacerImpl implements MapElementPlacer {
    private final CoordinateCalculatorImpl coordinateCalculator;

    final String emptyFieldSymbol = ConstantValues.EMPTY_FIELD_SYMBOL;
    private final int mapDimensionSize = (int) Math.sqrt(ConstantValues.MAP_SIZE);

    public MapElementPlacerImpl(CoordinateCalculatorImpl coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }

    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        int x = coordinate.x();
        int y = coordinate.y();

        List<Coordinate> areaToCheck = new ArrayList<>();
        for (int i = 0; i < element.getDimension(); i++) {
            for (int j = 0; j < element.getDimension(); j++) {
                areaToCheck.add(new Coordinate(x + i, y + j));
            }
        }
        if (areaToCheck.stream().allMatch(coord -> coord.x() < mapDimensionSize && coord.y() < mapDimensionSize)) {
            return areaToCheck.stream().allMatch(c -> Objects.equals(map[c.x()][c.y()], emptyFieldSymbol));
        }
        return false;
    }

    @Override
    public void placeElement(MapElement element, String[][] map, Coordinate coordinate) {
        final String mountainSymbol = ConstantValues.MOUNTAIN_SYMBOL;
        final String pitSymbol = ConstantValues.PIT_SYMBOL;

        switch (element.getName()) {
            case "mountain", "pit" -> {
                setPitsAndMountainsOnMap(element, map, coordinate);
            }
            case "mineral" -> {
                setMineralsOnMap(element, map, mountainSymbol);
            }
            case "water" -> {
                setMineralsOnMap(element, map, pitSymbol);
            }
        }
    }

    private void setPitsAndMountainsOnMap(MapElement element, String[][] map, Coordinate coordinate) {
        int x0 = coordinate.x();
        int y0 = coordinate.y();

        for (int i = 0; i < element.getDimension(); i++) {
            for (int j = 0; j < element.getDimension(); j++) {
                String rep = element.getRepresentation()[i][j];
                map[x0 + i][y0 + j] = rep;
            }
        }
    }

    private void setMineralsOnMap(MapElement element, String[][] map, String elementSymbol) {
        Random random = new Random();
        String preferredElementSymbol = element.getPreferredLocationSymbol();
        List<Coordinate> emptyFieldsList = new ArrayList<>();

        while (emptyFieldsList.isEmpty()) {
            Coordinate randomElementCoordinate = findRandomPreferredSymbolCoordinates(map, preferredElementSymbol);

            Iterable<Coordinate> adjacentFields = coordinateCalculator.getAdjacentCoordinates(randomElementCoordinate,
                    mapDimensionSize);

            for (Coordinate coord : adjacentFields) {
                if (map[coord.x()][coord.y()].equals(emptyFieldSymbol)) {
                    emptyFieldsList.add(coord);
                }
            }
        }

        int randomIndex = random.nextInt(emptyFieldsList.size());
        Coordinate coordsToPlace = emptyFieldsList.get(randomIndex);
        map[coordsToPlace.x()][coordsToPlace.y()] = elementSymbol;
    }

    private Coordinate findRandomPreferredSymbolCoordinates(String[][] map, String preferredSymbol) {
        Random random = new Random();

        while (true) {
            int randomRow = random.nextInt(map.length);
            int randomCol = random.nextInt(map.length);

            if (map[randomRow][randomCol].equals(preferredSymbol)) {
                return new Coordinate(randomRow, randomCol);
            }
        }
    }
}

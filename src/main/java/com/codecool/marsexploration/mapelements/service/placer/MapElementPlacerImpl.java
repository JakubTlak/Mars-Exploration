package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.*;
import java.util.stream.Stream;

public class MapElementPlacerImpl implements MapElementPlacer {
    private final CoordinateCalculatorImpl coordinateCalculator;

    public MapElementPlacerImpl(CoordinateCalculatorImpl coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }


    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        int x = coordinate.x();
        int y = coordinate.y();
        int mapSize = 64;

        List<Coordinate> areaToCheck = new ArrayList<>();
        for (int i = 0; i < element.getDimension(); i++) {
            for (int j = 0; j < element.getDimension(); j++) {
                areaToCheck.add(new Coordinate(x + i, y + j));
            }
        }
        if (areaToCheck.stream().allMatch(coord -> coord.x() < mapSize && coord.y() < mapSize)) {
            return areaToCheck.stream().allMatch(c -> Objects.equals(map[c.x()][c.y()], " "));
        }
        return false;
    }

    @Override
    public void placeElement(MapElement element, String[][] map, Coordinate coordinate) {
        switch (element.getName()) {
            case "mountain", "pit" -> {
                setPitsAndMountainsOnMap(element, map, coordinate);
            }
            case "mineral" -> {
                setMineralsOnMap(element, map, "%");
            }
            case "water" -> {
                setMineralsOnMap(element, map, "*");
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
                    64);

            for (Coordinate coord : adjacentFields) {
                if (map[coord.x()][coord.y()].equals(" ")) {
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

package com.codecool.marsexploration.mapelements.model;

import com.codecool.marsexploration.calculators.model.Coordinate;

public class Map {
    private final String[][] representation;

    private boolean successfullyGenerated;

    public Map(String[][] representation) {
        this.representation = representation;
    }

    public boolean isSuccessfullyGenerated() {
        return successfullyGenerated;
    }

    public void setSuccessfullyGenerated(boolean successfullyGenerated) {
        this.successfullyGenerated = successfullyGenerated;
    }

    private static String createStringRepresentation(String[][] arr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] row : arr) {
            for (String cell : row) {
                stringBuilder.append(cell != null ? cell : " ");
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public String getByCoordinate(Coordinate coordinate) {
        return representation[coordinate.x()][coordinate.y()];
    }

    public boolean isEmpty(Coordinate coordinate) {
        return representation[coordinate.x()][coordinate.y()] == null
                || representation[coordinate.x()][coordinate.y()].isEmpty()
                || representation[coordinate.x()][coordinate.y()].equals(" ");
    }

    public void changeMapElement(Coordinate coordinate, String string){
        int x = coordinate.x();
        int y = coordinate.y();
               if (x >= 0 && x < representation.length && y >= 0 && y < representation[x].length) {
            representation[x][y] = string;
        }
    }
    @Override
    public String toString() {
        return createStringRepresentation(representation);
    }

    public String[][] getRepresentation() {
        return representation;
    }
}


package com.codecool.marsexploration.calculators.service;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.*;

public class CoordinateCalculatorImpl implements CoordinateCalculator {
    private final Random random = new Random();

    @Override
    public Coordinate getRandomCoordinate(int dimension) {


        return new Coordinate(random.nextInt(dimension), random.nextInt(dimension));

    }

    @Override
    public Iterable<Coordinate> getAdjacentCoordinates(Coordinate coordinate, int dimension) {
//
//        ArrayList<Coordinate> coordinates = new ArrayList<>();
//
//        if(coordinate.x() == 0 && coordinate.y() == 0){
//
//            coordinates.add(new Coordinate(1,0));
//            coordinates.add(new Coordinate(0,1));
//
//        } else if (coordinate.x() == dimension && coordinate.y() == 0) {
//
//            coordinates.add(new Coordinate((dimension - 1),0));
//            coordinates.add(new Coordinate(dimension,1));
//
//        } else if (coordinate.x() == 0 && coordinate.y() == dimension) {
//
//            coordinates.add(new Coordinate(1,dimension));
//            coordinates.add(new Coordinate(0,(dimension - 1)));
//
//        } else if (coordinate.x() == dimension && coordinate.y() == dimension) {
//
//            coordinates.add(new Coordinate((dimension - 1),dimension));
//            coordinates.add(new Coordinate(dimension,(dimension - 1)));
//
//        }else if(coordinate.x() > 0 && coordinate.x() < dimension && coordinate.y() == 0) {
//
//            coordinates.add(new Coordinate(coordinate.x(), 1));
//            coordinates.add(new Coordinate((coordinate.x() + 1), coordinate.y()));
//            coordinates.add(new Coordinate((coordinate.x() - 1), coordinate.y()));
//
//        }else if(coordinate.x() > 0 && coordinate.x() < dimension && coordinate.y() == dimension) {
//
//            coordinates.add(new Coordinate(coordinate.x(), (coordinate.y()) - 1));
//            coordinates.add(new Coordinate((coordinate.x() + 1), coordinate.y()));
//            coordinates.add(new Coordinate((coordinate.x() - 1), coordinate.y()));
//
//        }else if(coordinate.y() > 0 && coordinate.y() < dimension && coordinate.x() == 0) {
//
//            coordinates.add(new Coordinate(1, coordinate.y()));
//            coordinates.add(new Coordinate(coordinate.x(), coordinate.y() - 1));
//            coordinates.add(new Coordinate(coordinate.x(), coordinate.y() + 1));
//
//        }else if(coordinate.y() > 0 && coordinate.y() < dimension && coordinate.x() == dimension) {
//
//            coordinates.add(new Coordinate(coordinate.x() - 1, coordinate.y()));
//            coordinates.add(new Coordinate(coordinate.x(), coordinate.y() - 1));
//            coordinates.add(new Coordinate(coordinate.x(), coordinate.y() + 1));
//
//        }else{
//
//            coordinates.add(new Coordinate(coordinate.x(), (coordinate.y()+1)));
//            coordinates.add(new Coordinate(coordinate.x(), (coordinate.y()-1)));
//            coordinates.add(new Coordinate((coordinate.x()+1), (coordinate.y())));
//            coordinates.add(new Coordinate((coordinate.x()-1), (coordinate.y())));
//
//        }
//        return coordinates;
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        int x = coordinate.x();
        int y = coordinate.y();

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                int newX = x + xOffset;
                int newY = y + yOffset;

                if (newX >= 0 && newX < dimension && newY >= 0 && newY < dimension) {
                    coordinates.add(new Coordinate(newX, newY));
                }
            }
        }

        return coordinates;
    }

    @Override
    public Iterable<Coordinate> getAdjacentCoordinates(Iterable<Coordinate> coordinates, int dimension) {

        List<Coordinate> adjacent = new ArrayList<>();

        List<Coordinate> chosenCoordinates = new ArrayList<>();

        for (Coordinate coordinate : coordinates) {
            adjacent.addAll((Collection<? extends Coordinate>) getAdjacentCoordinates(coordinate, dimension));
            chosenCoordinates.add(coordinate);
        }

        adjacent.removeIf(chosenCoordinates::contains);


        return adjacent;
    }
}

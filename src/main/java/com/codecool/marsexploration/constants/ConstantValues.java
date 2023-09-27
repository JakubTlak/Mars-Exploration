package com.codecool.marsexploration.constants;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.Arrays;
import java.util.List;

public interface ConstantValues {
    int MAP_SIZE = 4096;
    int SIMULATION_STEPS = 10;
    int TOTAL_STEPS = 600;
    int ROVER_SIGHT = 4;
    int MAP_DIMENSION = 64;
    int ROVER_DEPLOY_DIMENSION = 1;
    int GENERATE_MAP_QUANTITY = 3;
    int WATER_TO_SET_UP_COLONY = 3;
    int MINERALS_TO_SET_UP_COLONY = 3;


    double ELEMENT_TO_SPACE_RATIO = 0.5;

    String WORK_DIR = "src/main";
    String EXPLORATION_LOG_PATH = WORK_DIR + "/resources/steps.txt";
    String MAP_TO_LOAD_PATH = WORK_DIR + "/resources/map_1.map";
    String SAVE_MAP_FOLDER_PATH = WORK_DIR + "/resources/";
    String MINERAL_SYMBOL = "%";
    String WATER_SYMBOL = "*";
    String MOUNTAIN_SYMBOL = "#";
    String PIT_SYMBOL = "&";
    String EMPTY_FIELD_SYMBOL = " ";

    Coordinate LANDING_SPOT = new Coordinate(6, 6);

    List<String> ELEMENTS_TO_SCAN = Arrays.asList("#", "&", "%", "*");

}

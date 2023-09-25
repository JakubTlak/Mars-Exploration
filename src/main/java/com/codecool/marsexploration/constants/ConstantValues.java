package com.codecool.marsexploration.constants;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.Arrays;
import java.util.List;

public interface ConstantValues {
    int SIMULATION_STEPS = 10;
    int TOTAL_STEPS = 600;
    int ROVER_SIGHT = 4;
    int MAP_DIMENSION = 64;

    String WORK_DIR = "src/main";
    String EXPLORATION_LOG_PATH = WORK_DIR + "/resources/steps.txt";
    String MAP_TO_LOAD_PATH = WORK_DIR + "/resources/map_1.map";
    String SAVE_MAP_FOLDER_PATH = WORK_DIR + "/resources/";
    String MINERAL_SYMBOL = "%";
    String WATER_SYMBOL = "*";

    Coordinate LANDING_SPOT = new Coordinate(6, 6);

    List<String> ELEMENTS_TO_SCAN = Arrays.asList("#", "&", "%", "*");

}

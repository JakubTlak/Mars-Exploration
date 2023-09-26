package com.codecool.marsexploration.configuration.service;

import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;
import com.codecool.marsexploration.constants.ConstantValues;

import java.util.List;

public class MapConfigurationServiceImpl implements MapConfigurationService{
    public MapConfiguration getConfiguration() {
        final String mountainSymbol = ConstantValues.MOUNTAIN_SYMBOL;
        final String pitSymbol = ConstantValues.PIT_SYMBOL;
        final String mineralSymbol = ConstantValues.MINERAL_SYMBOL;
        final String waterSymbol = ConstantValues.WATER_SYMBOL;
        final String emptyFieldSymbol = ConstantValues.EMPTY_FIELD_SYMBOL;

        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                mountainSymbol,
                "mountain",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                3,
                emptyFieldSymbol
        );
        MapElementConfiguration pitCfg = new MapElementConfiguration(
                pitSymbol,
                "pit",
                List.of(
                        new ElementToSize(2, 10),
                        new ElementToSize(1, 20)
                ),
                10,
                emptyFieldSymbol
        );
        MapElementConfiguration mineralCfg = new MapElementConfiguration(
                mineralSymbol,
                "mineral",
                List.of(
                        new ElementToSize(10, 1)
                ),
                0,
                mountainSymbol
        );
        MapElementConfiguration waterCfg = new MapElementConfiguration(
                waterSymbol,
                "water",
                List.of(
                        new ElementToSize(10, 1)
                ),
                0,
                pitSymbol
        );

        List<MapElementConfiguration> elementsCfg = List.of(mountainsCfg, pitCfg, mineralCfg, waterCfg);
        int mapSize = ConstantValues.MAP_SIZE;
        double elementToSpaceRatio = ConstantValues.ELEMENT_TO_SPACE_RATIO;

        return new MapConfiguration(mapSize, elementToSpaceRatio, elementsCfg);
    }

}

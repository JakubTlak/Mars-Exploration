package com.codecool.marsexploration.configuration.service;

import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;

import java.util.List;

public class MapConfigurationServiceImpl implements MapConfigurationService{
    public MapConfiguration getConfiguration() {
        final String mountainSymbol = "#";
        final String pitSymbol = "&";
        final String mineralSymbol = "%";
        final String waterSymbol = "*";

        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                mountainSymbol,
                "mountain",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                3,
                ""
        );
        MapElementConfiguration pitCfg = new MapElementConfiguration(
                pitSymbol,
                "pit",
                List.of(
                        new ElementToSize(2, 10),
                        new ElementToSize(1, 20)
                ),
                10,
                ""
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
        return new MapConfiguration(4096, 0.5, elementsCfg);
    }

}

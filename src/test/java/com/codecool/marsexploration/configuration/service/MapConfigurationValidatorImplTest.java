package com.codecool.marsexploration.configuration.service;

import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapConfigurationValidatorImplTest {

    private MapConfiguration mapConfiguration;
    private MapConfigurationValidatorImpl mapConfigurationValidator;

    private List<MapElementConfiguration> elementsCfg;

    @BeforeEach
    void beforeEach() {
        mapConfigurationValidator = new MapConfigurationValidatorImpl(new ConsoleLoggerImpl());
    }


    @Test
    void validateReturnsTrueWithCorrectConfiguration() {
        createCorrectConfiguration();
        assertTrue(mapConfigurationValidator.validate(mapConfiguration));
    }


    @Test
    void validateReturnsFalseWithWrongMapSize() {
        createCorrectElementsCfg();
        mapConfiguration = new MapConfiguration(100, 0.5, elementsCfg);
        assertFalse(mapConfigurationValidator.validate(mapConfiguration));
    }

    @Test
    void validateWrongMapSizeReturnsFalseWithSomeWrongElementData() {
        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                "^",
                "mountainbanana",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                2,
                ""
        );
        MapElementConfiguration pitCfg = new MapElementConfiguration(
                "&",
                "pit",
                List.of(
                        new ElementToSize(2, 10),
                        new ElementToSize(1, 20)
                ),
                10,
                ""
        );
        MapElementConfiguration mineralCfg = new MapElementConfiguration(
                "%",
                "mineral",
                List.of(
                        new ElementToSize(10, 1)
                ),
                0,
                "#"
        );
        MapElementConfiguration waterCfg = new MapElementConfiguration(
                "*",
                "water",
                List.of(
                        new ElementToSize(10, 1)
                ),
                0,
                "&"
        );
        elementsCfg = List.of(mountainsCfg, pitCfg, mineralCfg, waterCfg);
        mapConfiguration = new MapConfiguration(1024, 0.5, elementsCfg);
        assertFalse(mapConfigurationValidator.validate(mapConfiguration));
    }

    @Test
    void validateReturnsFalseWithWrongNumberOfElements() {
        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                "#",
                "mountain",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                3,
                ""
        );
        MapElementConfiguration pitCfg = new MapElementConfiguration(
                "&",
                "pit",
                List.of(
                        new ElementToSize(2, 10),
                        new ElementToSize(1, 20)
                ),
                10,
                ""
        );
        elementsCfg = List.of(mountainsCfg, pitCfg);
        mapConfiguration = new MapConfiguration(1024, 0.5, elementsCfg);
        assertFalse(mapConfigurationValidator.validate(mapConfiguration));
    }

    @Test
    void validateReturnsFalseWithDuplicatedElements() {
        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                "#",
                "mountain",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                3,
                ""
        );
        elementsCfg = List.of(mountainsCfg, mountainsCfg, mountainsCfg, mountainsCfg);
        mapConfiguration = new MapConfiguration(1024, 0.5, elementsCfg);
        assertFalse(mapConfigurationValidator.validate(mapConfiguration));
    }


    private void createCorrectConfiguration(){
        createCorrectElementsCfg();
        mapConfiguration = new MapConfiguration(1024, 0.5, elementsCfg);
    }


    private void createCorrectElementsCfg(){
        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                "#",
                "mountain",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                3,
                ""
        );
        MapElementConfiguration pitCfg = new MapElementConfiguration(
                "&",
                "pit",
                List.of(
                        new ElementToSize(2, 10),
                        new ElementToSize(1, 20)
                ),
                10,
                ""
        );
        MapElementConfiguration mineralCfg = new MapElementConfiguration(
                "%",
                "mineral",
                List.of(
                        new ElementToSize(10, 1)
                ),
                0,
                "#"
        );
        MapElementConfiguration waterCfg = new MapElementConfiguration(
                "*",
                "water",
                List.of(
                        new ElementToSize(10, 1)
                ),
                0,
                "&"
        );
        elementsCfg = List.of(mountainsCfg, pitCfg, mineralCfg, waterCfg);
    }
}
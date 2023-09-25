package com.codecool.marsexploration.mapelements.service.generator;

import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;
import com.codecool.marsexploration.logger.service.ConsoleLoggerImpl;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapElementsGeneratorImplTest {
    private MapElementsGeneratorImpl mapElementsGenerator;

    @Mock
    private MapElementBuilderImpl mapElementBuilder;

    @Mock
    private ConsoleLoggerImpl consoleLogger;

    @BeforeEach
    public void beforeAll() {
        MockitoAnnotations.initMocks(this);
        mapElementsGenerator = new MapElementsGeneratorImpl(consoleLogger,mapElementBuilder);
    }

    @Test
    void generateZeroElements(){
        //arrange
        int mapSize = 64;
        double elementToSpaceRatio = 0.5;
        MapConfiguration mapConfiguration = new MapConfiguration(mapSize,
                elementToSpaceRatio, new ArrayList<>());

        //act
        Iterable<MapElement> iterable = mapElementsGenerator.createAll(mapConfiguration);
        int iterableSize = 0;
        for (MapElement mapElement: iterable) {
            iterableSize++;
        }

        //assert
        assertEquals(0,iterableSize);
    }

}
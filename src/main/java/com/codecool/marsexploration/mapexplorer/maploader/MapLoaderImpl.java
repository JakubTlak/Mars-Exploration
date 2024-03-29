package com.codecool.marsexploration.mapexplorer.maploader;

import com.codecool.marsexploration.constants.ConstantValues;
import com.codecool.marsexploration.mapelements.model.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MapLoaderImpl implements MapLoader{
    private final int mapDimensionSize = (int) Math.sqrt(ConstantValues.MAP_SIZE);

    @Override
    public Map load(String mapFile) {

        String[][] representation = new String[mapDimensionSize][mapDimensionSize];

        try {
            FileReader fileReader = new FileReader(mapFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> lines =  bufferedReader.lines().toList();

            for(int i =0;i < lines.size();i++){
                for (int j =0;j < lines.get(i).length();j++){
                    representation[i][j] = String.valueOf(lines.get(i).charAt(j));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Map(representation);

    }
}

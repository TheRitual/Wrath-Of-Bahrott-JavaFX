package eu.theritual.wrathofbahrott.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.theritual.wrathofbahrott.dataoperator.GameOptions;

import java.io.File;
import java.io.IOException;

public class SaveLoadUtils {
    public static void saveOptions(GameOptions options, String fileName){
        try {
            new ObjectMapper().writeValue(new File(fileName + ".json"), options);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameOptions loadOptions(String fileName) {
        try {
            File loadFile = new File(fileName + ".json");
            if (loadFile.exists()) {
                return new ObjectMapper().readValue(loadFile, GameOptions.class);
            } else {
                return new GameOptions();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

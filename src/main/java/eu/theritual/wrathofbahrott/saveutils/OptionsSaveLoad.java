package eu.theritual.wrathofbahrott.saveutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.theritual.wrathofbahrott.dataoperator.GameOptions;

import java.io.File;
import java.io.IOException;

public class OptionsSaveLoad {
    public static void saveOptions(GameOptions options, String fileName){
        try {
            new ObjectMapper().writeValue(new File(fileName + ".json"), options);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameOptions loadOptions(String fileName) {
        try {
            return new ObjectMapper().readValue(new File(fileName + ".json"), GameOptions.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

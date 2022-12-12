package com.blazeclan.qa.utility;

import com.blazeclan.qa.constants.IConstants;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class JsonUtility {

    public JSONObject readJson() {
//        File jsonFile =
        Reader readJson;
        try {
            readJson = new FileReader(IConstants.TEST_DATA_FILE_PATH, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONTokener jsonToken = new JSONTokener(readJson);
        return new JSONObject(jsonToken);
    }

}

package com.practica.controller.dao.implement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonFileManager {

    public static final String JSON_FILE_DIR = "./media/list/";
    public static final String CURRENT_ID_FILENAME = "currentIDs.json";
    
    // WRITE ===============================================================================================================

    public static void writeStringInJsonFile(String data, String fileName) {
        try (FileWriter fw = new FileWriter(JSON_FILE_DIR + fileName)) {
            fw.write(data);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeObjectInJsonFile(Object object, String fileName) {
        writeStringInJsonFile(new GsonBuilder().setPrettyPrinting().create().toJson(object),fileName);
    }

    // READ ================================================================================================================

    public static String readJsonFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bf = new BufferedReader(new FileReader(JSON_FILE_DIR + fileName))) {
            String line;
            while((line = bf.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
            System.out.println("PEQUENIO ERROR:" + e.getMessage());
            sb = new StringBuilder("{}");
        }
        return sb.toString();
    }

    //CURRENT ID ===========================================================================================================

    public static HashMap<String,Integer> readCurrentIDsMap() {
        Type mapType = new TypeToken<HashMap<String,Integer>>(){}.getType();
        return new Gson().fromJson(readJsonFile(CURRENT_ID_FILENAME), mapType);
    }

    public static void writeCurrentIDs(String className, Integer currentId) {
        HashMap<String,Integer> currentIDsMap = (readCurrentIDsMap() == null) ? new HashMap<>() : readCurrentIDsMap();
        currentIDsMap.put("currentId" + className, currentId);
        writeObjectInJsonFile(currentIDsMap, CURRENT_ID_FILENAME);
    }

    public static Integer readAndUpdateCurrentIdOf(String className) {
        HashMap<String, Integer> currentIdHashMap = readCurrentIDsMap();
        if (currentIdHashMap == null ) {
            writeCurrentIDs(className, 0);
        } else if (currentIdHashMap.get("currentId" + className) == null) {
            writeCurrentIDs(className, 0);
        }
        currentIdHashMap = readCurrentIDsMap();

        Integer currentId = currentIdHashMap.get("currentId" + className);
        currentId++;
        writeCurrentIDs(className, currentId);
        return currentId;
    }
}

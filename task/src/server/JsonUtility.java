package server;

import com.google.gson.GsonBuilder;

public class JsonUtility {

    public static String prettyPrint(Object jsonObject) {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(jsonObject);
    }
}

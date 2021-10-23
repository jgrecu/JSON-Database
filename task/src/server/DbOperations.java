package server;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DbOperations {
    private final String type;
    private final String key;
    private final String value;
    static final Map<String, String> mapDb = new HashMap<>();


    public DbOperations(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String set() {
        //if (!mapDb.containsKey(key)) {
            mapDb.put(key, value);
            return new Gson().toJson(Map.of("response", "OK"));
        //} else {
            //return new Gson().toJson(new ErrorMessage());
        //}
    }

    public String get() {
        if (mapDb.containsKey(key)) {
            return new Gson().toJson(new Message("OK", mapDb.get(key)));
        } else {
            return new Gson().toJson(new ErrorMessage());
        }
    }

    public String delete() {
        if (mapDb.containsKey(key)) {
            mapDb.remove(key);
            return new Gson().toJson(Map.of("response","OK"));
        } else {
            return new Gson().toJson(new ErrorMessage());
        }
    }

    public String exit() {
        return new Gson().toJson(Map.of("response","OK"));
    }

    public String getType() {
        return type;
    }
}

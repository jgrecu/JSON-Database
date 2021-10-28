package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.exceptions.NoSuchKeyException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum DbOperations {

    INSTANCE;

    private JsonObject database;
    private static final String fileName = "db.json";
    private static final Path dbFilePath = Paths.get(System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "server" + File.separator +
            "data" + File.separator + fileName);


    DbOperations() {
    }

    public void init() throws IOException {
        if (Files.exists(dbFilePath)) {
            String content = new String(Files.readAllBytes(dbFilePath));
            database = new Gson().fromJson(content, JsonObject.class);
        } else {
            Files.createFile(dbFilePath);
            database = new JsonObject();
            exportToFile();
        }
    }

    public synchronized void set(JsonElement key, JsonElement value) {
        if (key.isJsonPrimitive()) {
            database.add(key.getAsString(), value);
            //System.out.println(database.getAsString());
        } else if (key.isJsonArray()) {
            JsonArray keys = key.getAsJsonArray();
            String toAdd = keys.remove(keys.size() - 1).getAsString();
            findElement(keys, true).getAsJsonObject().add(toAdd, value);
        } else {
            throw new NoSuchKeyException();
        }
        exportToFile();
    }

    public synchronized JsonElement get(JsonElement key) {
        if (key.isJsonPrimitive() && database.getAsJsonObject().has(key.getAsString())) {
            //System.out.println(jsonDb.getAsString());
            return database.getAsJsonObject().get(key.getAsString());
        } else if (key.isJsonArray()){
            return findElement(key.getAsJsonArray(), false);
        }
        throw new NoSuchKeyException();
    }

    public synchronized void delete(JsonElement key) {
        if (key.isJsonPrimitive() && database.has(key.getAsString())) {
            database.remove(key.getAsString());
            //System.out.println(jsonDb.getAsString());
        } else if (key.isJsonArray()) {
            JsonArray keys = key.getAsJsonArray();
            String toRemove = keys.remove(keys.size() - 1).getAsString();
            findElement(keys, false).getAsJsonObject().remove(toRemove);
            exportToFile();
        } else {
            throw new NoSuchKeyException();
        }
    }

    private JsonElement findElement(JsonArray keys, boolean createIfAbsent) {
        JsonElement tmp = database;
        if (createIfAbsent) {
            for (JsonElement key: keys) {
                if (!tmp.getAsJsonObject().has(key.getAsString())) {
                    tmp.getAsJsonObject().add(key.getAsString(), new JsonObject());
                }
                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }
        } else {
            for (JsonElement key: keys) {
                if (!key.isJsonPrimitive() || !tmp.getAsJsonObject().has(key.getAsString())) {
                    throw new NoSuchKeyException();
                }
                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }
        }
        return tmp;
    }

    private void exportToFile() {
        try {
            Files.write(dbFilePath, JsonBuilder.prettyPrint(database).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

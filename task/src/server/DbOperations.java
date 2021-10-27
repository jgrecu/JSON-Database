package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DbOperations {
    private final String type;
    private final String key;
    private final String value;
    static final JsonBuilder jsonDb = JsonBuilder.newBuilder();
    private static final String RESPONSE = "response";
    private static final String OK_STATUS = "OK";
    private static final String ERROR_STATUS = "ERROR";
    private static final String NO_SUCH_KEY_REASON = "No such key";
    private static final String fileName = "db.json";
    private static final String dbFilePath = System.getProperty("user.dir") + File.separator +
            /*"JSON Database" + File.separator +
            "task" + File.separator + */
            "src" + File.separator +
            "server" + File.separator +
            "data" + File.separator + fileName;


    public DbOperations(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public synchronized String set() {
        jsonDb.addValue(key, value);
        //System.out.println(jsonDb.getAsString());
        exportToFile();
        return JsonBuilder.newBuilder().addValue(RESPONSE, OK_STATUS).getAsString();
    }

    public synchronized String get() {
        if (jsonDb.getAsJsonObject().has(key)) {
            String givenValue = jsonDb.getAsJsonObject().get(key).getAsString();
            //System.out.println(jsonDb.getAsString());
            return JsonBuilder.newBuilder().addValue(RESPONSE, OK_STATUS)
                    .addValue("value", givenValue).getAsString();
        } else {
            return JsonBuilder.newBuilder().addValue(RESPONSE, ERROR_STATUS)
                    .addValue("reason", NO_SUCH_KEY_REASON).getAsString();
        }
    }

    public synchronized String delete() {
        if (jsonDb.getAsJsonObject().has(key)) {
            jsonDb.getAsJsonObject().remove(key);
            //System.out.println(jsonDb.getAsString());
            exportToFile();
            return JsonBuilder.newBuilder().addValue(RESPONSE, OK_STATUS).getAsString();
        } else {
            return JsonBuilder.newBuilder().addValue(RESPONSE, ERROR_STATUS)
                    .addValue("reason", NO_SUCH_KEY_REASON).getAsString();
        }
    }

    public synchronized String exit() {
        return JsonBuilder.newBuilder().addValue(RESPONSE, OK_STATUS).getAsString();
    }

    public String getType() {
        return type;
    }

    private void exportToFile() {
        try {
            Files.write(Paths.get(dbFilePath), jsonDb.getAsJsonObject().toString().getBytes());
//            FileWriter file = new FileWriter(dbFilePath);
//            file.write(jsonDb.getAsJsonObject().toString());
//            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

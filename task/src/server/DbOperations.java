package server;

import java.util.Arrays;

public class DbOperations {
    private final int position;
    private final String message;
    static String[] array = new String[1000];
    static {
        Arrays.fill(array, "");
    }

    public DbOperations() {
        this.position = 0;
        this.message = "";
    }

    public DbOperations(int position) {
        this.position = position;
        this.message = "";
    }

    public DbOperations(int position, String message) {
        this.position = position;
        this.message = message;
    }

    public String set() {
        if (position > 0 && position < 1001) {
            array[position - 1] = message;
            return "OK";
        } else {
            return "ERROR";
        }
    }

    public String get() {
        if (position > 0 && position < 1001) {
            String result = array[position - 1];
            if (!result.equalsIgnoreCase("")) {
                return result;
            } else {
                return "ERROR";
            }
        } else {
            return "ERROR";
        }
    }

    public String delete() {
        if (position > 0 && position < 1001) {
            array[position - 1] = "";
            return "OK";
        } else {
            return "ERROR";
        }
    }

    public String exit() {
        return "OK";
    }
}

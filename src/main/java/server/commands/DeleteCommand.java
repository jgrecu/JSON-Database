package server.commands;

import com.google.gson.JsonElement;
import server.DbOperations;

public class DeleteCommand implements Command {
    private final JsonElement key;

    public DeleteCommand( JsonElement key) {
        this.key = key;
    }

    @Override
    public void execute() {
         DbOperations.INSTANCE.delete(key);
    }
}

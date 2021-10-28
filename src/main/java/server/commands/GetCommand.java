package server.commands;

import com.google.gson.JsonElement;
import server.DbOperations;

public class GetCommand implements Command {
    private final JsonElement key;
    private JsonElement result;

    public GetCommand(JsonElement key) {
        this.key = key;
    }

    public JsonElement getResult() {
        return result;
    }

    @Override
    public void execute() {
        result =  DbOperations.INSTANCE.get(key);
    }
}

package server;

public class SetCommand implements Command {
    private DbOperations dbOperations;

    public SetCommand(DbOperations dbOperations) {
        this.dbOperations = dbOperations;
    }

    @Override
    public String execute() {
        return dbOperations.set();
    }
}

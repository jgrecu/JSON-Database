package server;

public class ExitCommand implements Command {
    private DbOperations dbOperations;

    public ExitCommand(DbOperations dbOperations) {
        this.dbOperations = dbOperations;
    }

    @Override
    public String execute() {
        return dbOperations.exit();
    }
}

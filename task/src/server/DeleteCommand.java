package server;

public class DeleteCommand implements Command {
    private DbOperations dbOperations;

    public DeleteCommand(DbOperations dbOperations) {
        this.dbOperations = dbOperations;
    }

    @Override
    public String execute() {
        return dbOperations.delete();
    }
}

package server;

public class GetCommand implements Command {
    private DbOperations dbOperations;

    public GetCommand(DbOperations dbOperations) {
        this.dbOperations = dbOperations;
    }

    @Override
    public String execute() {
        return dbOperations.get();
    }
}

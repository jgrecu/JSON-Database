package client;

import com.beust.jcommander.Parameter;

public class GetArgs {
    @Parameter(names={"-t"})
    String command;
    @Parameter(names={"-i"})
    int position;
    @Parameter(names={"-m"})
    String message;

    public String getMessage() {
        switch (command) {
            case "get": case "delete":
                return command + " " + position;
            case "set":
                return command + " " + position + " " + message;
            case "exit":
                return command;
            default:
                return null;
        }
    }
}

package server;

public class ErrorMessage {
    String response;
    String reason;

    public ErrorMessage() {
        this.response = "ERROR";
        this.reason = "No such key";
    }

}

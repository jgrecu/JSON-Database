package server;

import com.google.gson.Gson;
import server.commands.DeleteCommand;
import server.commands.GetCommand;
import server.commands.SetCommand;
import server.exceptions.BadRequestException;
import server.exceptions.NoSuchKeyException;
import server.requests.Request;
import server.requests.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Session extends Thread{
    private final Socket socket;
    private final Controller controller = new Controller();

    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    public void run() {
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String msg = input.readUTF();
            String outMsg = processMessage(msg);
            output.writeUTF(outMsg);
            if (msg.contains("exit")) {
                Main.setDoStop(new AtomicBoolean(true));
            }
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    public String processMessage(String receivedMessage) {
        final Gson gson = new Gson();
        final Controller controller = new Controller();
        Request request = gson.fromJson(receivedMessage, Request.class);
        Response response = new Response();

        try {
            switch (request.getType()) {
                case "get":
                    GetCommand getCommand = new GetCommand(request.getKey());
                    controller.setCommand(getCommand);
                    controller.executeCommand();
                    response.setValue(getCommand.getResult());
                    break;
                case "set":
                    SetCommand setCommand = new SetCommand(request.getKey(), request.getValue());
                    controller.setCommand(setCommand);
                    controller.executeCommand();
                    break;
                case "delete":
                    DeleteCommand deleteCommand = new DeleteCommand(request.getKey());
                    controller.setCommand(deleteCommand);
                    controller.executeCommand();
                    break;
                case "exit":
                    break;
                default:
                    throw new BadRequestException();
            }
            response.setResponse(Response.STATUS_OK);
        } catch (BadRequestException | NoSuchKeyException e) {
            response.setResponse(Response.STATUS_ERROR);
            response.setReason(e.getMessage());
        } catch (Exception e) {
            response.setResponse(Response.STATUS_ERROR);
        } finally {
            return JsonBuilder.prettyPrint(response);
        }
    }

}

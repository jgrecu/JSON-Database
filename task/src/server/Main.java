package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static final AtomicBoolean doStop = new AtomicBoolean(false);


    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (!doStop.get()) {
                Session session = new Session(server.accept());
                session.start();
                session.join();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("ERROR");
        }
    }

    public static void setDoStop(AtomicBoolean stop) {
        doStop.set(stop.get());
        //doStop = stop;
    }
}

class Session extends Thread {
    private final Socket socket;

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
        DbOperations dbOperations = gson.fromJson(receivedMessage, DbOperations.class);

        Command get = new GetCommand(dbOperations);
        Command set = new SetCommand(dbOperations);
        Command delete = new DeleteCommand(dbOperations);
        Command exit = new ExitCommand(dbOperations);

        switch (dbOperations.getType()) {
            case "get":
                controller.setCommand(get);
                return controller.executeCommand();
            case "set":
                controller.setCommand(set);
                return controller.executeCommand();
            case "delete":
                controller.setCommand(delete);
                return controller.executeCommand();
            case "exit":
                controller.setCommand(exit);
                return controller.executeCommand();
            default:
                return "{\"response\":\"ERROR\"}";
        }
    }

}


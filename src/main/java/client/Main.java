package client;

import com.beust.jcommander.JCommander;
import server.JsonUtility;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final Path clientDataPath = Paths.get(System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "main" + File.separator +
            "java" + File.separator +
            "client" + File.separator +
            "data");

    public static void main(String[] args) throws FileNotFoundException {
        GetArgs getArgs = new GetArgs();
        JCommander.newBuilder()
                .addObject(getArgs)
                .build()
                .parse(args);

        Path filePath = Path.of(clientDataPath + File.separator + getArgs.fileName);
        String msg = "";
        try {
            msg = getArgs.fileName != null ?
                    new String(Files.readAllBytes(filePath)) : JsonUtility.print(getArgs);
        } catch (IOException e) {
            System.out.println("No file found: " + filePath);
        }

        String address = "127.0.0.1";
        int port = 23456;
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            if (msg.isEmpty()) {
                throw new IOException("no message");
            }
            output.writeUTF(msg);
            System.out.println("Sent: " + msg);
            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            System.out.println("ERROR");
        }

    }
}

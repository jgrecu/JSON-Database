package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        GetArgs getArgs = new GetArgs();
        JCommander.newBuilder()
                .addObject(getArgs)
                .build()
                .parse(args);
        Gson gson = new Gson();
        //String msg = getArgs.getValue();
        String msg = gson.toJson(getArgs);

        String address = "127.0.0.1";
        int port = 23456;
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            if (msg == null) {
                throw new IOException("no message");
            }
            output.writeUTF(msg);
            System.out.println("Sent: " + msg);
            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e ) {
            System.out.println("ERROR");
        }

    }
}

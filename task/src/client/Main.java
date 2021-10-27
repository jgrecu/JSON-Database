package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static final String clientDataPath = System.getProperty("user.dir") + File.separator +
            /*"JSON Database" + File.separator +
            "task" + File.separator + */
            "src" + File.separator +
            "client" + File.separator +
            "data";

    public static void main(String[] args) throws FileNotFoundException {
        GetArgs getArgs = new GetArgs();
        JCommander.newBuilder()
                .addObject(getArgs)
                .build()
                .parse(args);
        String msg = "";
        if (getArgs.fileName == null) {
            Gson gson = new Gson();
            //String msg = getArgs.getValue();
            msg = gson.toJson(getArgs);
        } else {
            String file = File.separator + getArgs.fileName;
            msg = new Scanner(new File(clientDataPath + file)).nextLine().strip();
        }
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

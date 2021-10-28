package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static final AtomicBoolean doStop = new AtomicBoolean(false);


    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            DbOperations.INSTANCE.init();
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



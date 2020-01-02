package BIF.SWE1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket listener;

    Server(int port) {
        // set up a ServerSocket
        try {
            this.listener = new ServerSocket(port);
            System.out.println("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(80);

        // accept clients and pass socket to ClientHandler in new thread
        Socket socket = new Socket();
        while(true) {
            System.out.println("Waiting for a client...");

            try {
                if(server.listener != null)
                socket = server.listener.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Client accepted");
            Thread thread = new Thread(new ClientHandler(socket));
            thread.start();
        }
    }
}

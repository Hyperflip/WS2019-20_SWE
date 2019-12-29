package BIF.SWE1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket socket;

    Server(int port) {
        // set up a ServerSocket
        try {
            this.socket = new ServerSocket(port);
            System.out.println("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8080);

        // accept clients and pass socket to ClientHandler in new thread
        Socket socket = new Socket();
        while(true) {
            System.out.println("Waiting for a client...");

            try {
                assert server.socket != null;
                socket = server.socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Client accepted");
            Thread thread = new Thread(new ClientHandler(socket));
        }
    }
}

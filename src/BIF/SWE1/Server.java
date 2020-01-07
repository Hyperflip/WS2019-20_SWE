package BIF.SWE1;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static String siteDirName;

    private ServerSocket listener;

    Server(int port, String path) {
        // set up a ServerSocket
        try {
            this.listener = new ServerSocket(port);
            System.out.println("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }

        siteDirName = path;

        WebPluginManager.getPluginManager();
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Usage: port site_directory");
        }

        Server server = new Server(Integer.parseInt(args[0]), args[1]);

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

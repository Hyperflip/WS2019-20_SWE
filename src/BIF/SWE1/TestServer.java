package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    private Socket cliSocket;

    TestServer() throws IOException {
        try {
            this.server = new ServerSocket(8080);
            System.out.println("Server started");
            System.out.println("Waiting for a client...");

            this.socket = server.accept();
            System.out.println("Client accepted");

            // take input
            this.in = new DataInputStream((new BufferedInputStream(socket.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Request req = new RequestFactory().getWebRequest(in);
        Plugin plugin = new TestPlugin();
        Response resp = null;
        if(plugin.canHandle(req) == 1) {
            resp = plugin.handle(req);
        }

        assert this.socket != null;
        OutputStream out = this.socket.getOutputStream();
        assert resp != null;
        resp.send(out);

    }

    public static void main(String[] args) throws IOException {
        TestServer server = new TestServer();
    }
}

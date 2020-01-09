package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.plugins.StaticGetPlugin;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private OutputStream out;

    /**
     * sets socket for this client handler
     *
     * @param socket client connection socket
     */

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    private DataInputStream getDataInputStream() throws IOException {
        return new DataInputStream((new BufferedInputStream(this.socket.getInputStream())));
    }

    private OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    /**
     * run method of the client handler thread
     * handles the response based on the best suitable plugin
     */

    @Override
    public void run() {
        System.out.println("running new thread: " + this);

        try {
            this.in = this.getDataInputStream();
            this.out = this.getOutputStream();

            Request req = new RequestFactory().getWebRequest(this.in);
            Plugin plugin = WebPluginManager.getSuitablePluginForRequest(req);

            Response resp = plugin.handle(req);
            resp.send(out);

            this.socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

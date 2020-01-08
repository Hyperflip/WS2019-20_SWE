package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.plugins.StaticGetPlugin;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    private void setDataInputStream() throws IOException {
        this.in = new DataInputStream((new BufferedInputStream(this.socket.getInputStream())));
    }

    private void setOutputStream() throws IOException {
        this.out = this.socket.getOutputStream();
    }

    /**
     * run method of the client handler thread
     * handles the response based on the best suitable plugin
     */

    @Override
    public void run() {
        System.out.println("running new thread: " + this);

        try {
            this.setDataInputStream();
            this.setOutputStream();

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

package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private OutputStream out;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    private void setDataInputStream() throws IOException {
        this.in = new DataInputStream((new BufferedInputStream(this.socket.getInputStream())));
    }

    private void setOutputStream() throws IOException {
        this.out = this.socket.getOutputStream();
    }

    private Plugin getOptimalPlugin(Request req) {
        return null;
    }

    @Override
    public void run() {
        System.out.println("running new thread: " + this);

        try {
            this.setDataInputStream();
            this.setOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Request req = new RequestFactory().getWebRequest(this.in);


        // TODO: refactor + do properly:
        Plugin plugin = new TestPlugin();
        Response resp = null;

        if(plugin.canHandle(req) == 0.1f) {
            resp = plugin.handle(req);
        }

        if(resp != null) {
            resp.send(out);
        }
        else try {
            throw new IOException("Response object is null");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

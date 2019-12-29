package BIF.SWE1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream in;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    private void setDataInputStream() {
        try {
            this.in = new DataInputStream((new BufferedInputStream(this.socket.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.setDataInputStream();

        while(true) {}

        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("closing socket");
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }
}

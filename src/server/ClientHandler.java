package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    Socket client;
    DataInputStream in;
    DataOutputStream out;

    int id;
    double x, y;

    boolean connected;

    public ClientHandler(Socket client){
        this.client = client;
        try {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(this::readPackage).start();
    }

    public void readPackage(){
        try {
            connected = true;

            out.writeInt(id);

            while (connected) {
                x = in.readDouble();
                y = in.readDouble();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

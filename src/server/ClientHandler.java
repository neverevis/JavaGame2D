package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    Socket client;
    Server server;
    DataInputStream in;
    DataOutputStream out;

    int id;
    double x, y;

    boolean connected;
    boolean markToRemove;

    public ClientHandler(Socket client,Server server){
        this.client = client;
        this.server = server;

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
            try{client.close();} catch (IOException ex) {e.printStackTrace();}
            connected = false;
        }

        System.out.println("Cliente " + id + " saiu do jogo.");
    }
}

package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    Socket client;
    Server server;
    DataInputStream in;
    DataOutputStream out;

    Thread launch;

    int id;
    double x, y;
    String nickname;

    boolean connected;

    public ClientHandler(Socket client,Server server){
        this.client = client;
        this.server = server;

        try {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch = new Thread(this::readPackage);
    }

    public void readPackage(){
        try {
            connected = true;
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

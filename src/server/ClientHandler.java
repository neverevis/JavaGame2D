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
    int state;
    int facing;
    String nickname;

    boolean connected = true;

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
            while (connected) {
                x = in.readDouble();
                y = in.readDouble();
                state = in.readInt();
                facing = in.readInt();
            }
        } catch (IOException e) {
            try{client.close();} catch (IOException ex) {e.printStackTrace();}
            connected = false;
        }

        System.out.println("Cliente " + nickname + " saiu do jogo.");
    }
}

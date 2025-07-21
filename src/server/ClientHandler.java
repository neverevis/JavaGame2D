package server;

import math.Vector;
import server.logic.S_Player;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    Socket client;
    Server server;
    DataInputStream in;
    DataOutputStream out;

    S_Player player;

    Thread launch;

    int id;
    public boolean W, A ,S ,D ,click;
    String nickname;

    boolean connected = true;

    public ClientHandler(Socket client,Server server){
        this.client = client;
        this.server = server;
        player = new S_Player(this ,new Vector(32 * server.playerCount,96),server.serverWorld);
        server.serverWorld.players.add(player);

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
                W = in.readBoolean();
                A = in.readBoolean();
                S = in.readBoolean();
                D = in.readBoolean();
                click = in.readBoolean();
            }
        } catch (IOException e) {
            try{client.close();} catch (IOException ex) {e.printStackTrace();}
            connected = false;
            server.serverWorld.players.remove(player);
        }

        System.out.println("Cliente " + nickname + " saiu do jogo.");
    }

    public void setPlayer(S_Player player){
        this.player = player;
    }
}

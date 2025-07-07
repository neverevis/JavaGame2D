package server;

import elements.entities.Player;
import game.GamePanel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    ServerSocket server;
    GamePanel virtualGame;
    List<ClientHandler> clients;
    public static int connectedCount;

    public Server(){
        try{
            clients = new ArrayList<>();

            server = new ServerSocket(12345);
            System.out.println("Servidor inicializado na porta " + server.getLocalPort());
            new Thread(this::virtualGame).start();
            new Thread(this::broadcast).start();

            while(true) {
                Socket client = server.accept();

                connectedCount++;
                virtualGame.activeWorld.connectedPlayers.add(new Player(virtualGame,virtualGame.activeWorld,true));
                System.out.println("quantidade de players instanciados no jogo virtual: " + virtualGame.activeWorld.connectedPlayers.size());

                ClientHandler ch = new ClientHandler(client,clients);
                clients.add(ch);
                new Thread(ch).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast() {
        while(true){
            try {
                for (ClientHandler self : clients) {
                    self.out.writeInt(connectedCount);
                    for (ClientHandler other : clients) {
                        if (other != self) {
                            self.out.writeInt(other.id);
                            self.out.writeDouble(other.inX);
                            self.out.writeDouble(other.inY);
                            self.out.writeInt(other.inDirection);
                            self.out.writeInt(other.inState);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void virtualGame(){
        virtualGame = new GamePanel(true);
        virtualGame.game.start();
    }
}

package server;

import elements.Element;
import elements.entities.Player;
import elements.entities.Slime;
import game.GamePanel;
import utilities.C;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    ServerSocket server;
    GamePanel virtualGame;
    List<ClientHandler> clients;
    public static int connectedCount;

    public Server(){
        try{
            clients = new CopyOnWriteArrayList<>();

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
                    self.out.writeInt(2);
                    int slimeCount = 0;
                    for(Element elm: virtualGame.activeWorld.elements){
                        if(elm instanceof Slime)
                            slimeCount++;
                    }
                    self.out.writeInt(4);

                    //atualizar slimes
                    self.out.writeInt(3);

                    for (ClientHandler other : clients) {
                        if (other != self && other.hasSentData) {
                            self.out.writeInt(1);
                            self.out.writeInt(connectedCount);
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
                Thread.sleep((long)(1000/ C.FPS));
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

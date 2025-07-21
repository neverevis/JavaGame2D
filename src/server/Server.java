package server;

import core.AnimationTimer;
import core.G;
import math.Vector;
import server.logic.S_Player;
import server.logic.ServerWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    Random random = new Random();
    ServerSocket server;
    ServerWorld serverWorld;
    List<ClientHandler> clients;
    int port = 12345;
    int playerCount = 0;

    public Server(){
        try {
            server = new ServerSocket(port);
            clients = new CopyOnWriteArrayList<>();
            serverWorld = new ServerWorld(this);

            new AnimationTimer(300){
                @Override
                public void step(double dt) {
                  serverWorld.update(dt);
                }
            }.start();
            System.out.println("Servidor inicializado!");

            new AnimationTimer(60){
                @Override
                public void step(double dt) {
                    broadcast();
                }
            }.start();

            while(true){
                Socket client;
                client = server.accept();

                ClientHandler clientHandler = new ClientHandler(client,this);

                clientHandler.id = generateId();
                clientHandler.out.writeInt(clientHandler.id);
                clientHandler.nickname = clientHandler.in.readUTF();

                clients.add(clientHandler);
                clientHandler.launch.start();

                System.out.println("Cliente conectado: " + clientHandler.nickname);

                playerCount++;
            }
        } catch (IOException e) {
            System.out.println("Servidor finalizado.");
            e.printStackTrace();
        }
    }

    public void broadcast(){
        while (true) {
            for (ClientHandler self : clients) {
                for (ClientHandler other : clients) {
                    try {
                        //pacote de player
                        self.out.writeInt(0);

                        self.out.writeInt(other.id);
                        self.out.writeUTF(other.nickname);
                        self.out.writeDouble(other.player.pos.x);
                        self.out.writeDouble(other.player.pos.y);
                        self.out.writeInt(other.player.state);
                        self.out.writeInt(other.player.facing);
                    } catch (IOException e) {
                        System.out.println("falha ao enviar dados do cliente: " + self.id);
                    }
                }
            }

            clients.stream().filter((client) -> !client.connected).forEach((client) -> {
                for(ClientHandler other : clients){
                    if(other.connected){
                        try {
                            other.out.writeInt(1);

                            other.out.writeInt(client.id);
                        }catch (IOException e){
                            System.out.println("falha ao enviar para o cliente: " + other.id);
                        }
                    }
                }
                clients.remove(client);
            });

            sleep();
        }
    }

    public int generateId(){
        boolean valid;
        int id;

        do{
            valid = true;
            id = random.nextInt(9999999);

            for(ClientHandler client : clients){
                if(client.id == id)
                    valid = false;
            }
        }while (!valid);

        return id;
    }

    public void sleep(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}

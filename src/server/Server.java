package server;

import core.AnimationTimer;
import core.G;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    Random random = new Random();
    ServerSocket server;
    List<ClientHandler> clients;
    int port = 12345;

    public Server(){
        try {
            server = new ServerSocket(port);
            clients = new CopyOnWriteArrayList<>();
            System.out.println("Servidor inicializado!");

            new Thread(this::broadcast).start();

            while(true){
                Socket client;
                client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client,this);
                clientHandler.id = generateId();
                clients.add(clientHandler);

                System.out.println("Cliente conectado: " + clientHandler.id);
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
                    if (self.connected && other.connected && other != self) {
                        try {
                            //pacote de player
                            self.out.writeInt(0);

                            self.out.writeInt(other.id);
                            self.out.writeDouble(other.x);
                            self.out.writeDouble(other.y);
                        } catch (IOException e) {
                            System.out.println("falha ao enviar dados do cliente: " + self.id);
                        }
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

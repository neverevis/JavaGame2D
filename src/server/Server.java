package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        try{
            List<ClientHandler> clients = new ArrayList<>();

            ServerSocket server = new ServerSocket(12345);
            System.out.println("Servidor inicializado na porta " + server.getLocalPort());

            while(true) {
                Socket client = server.accept();
                ClientHandler ch = new ClientHandler(client,clients);
                System.out.println("Cliente conectado: " + client.getInetAddress());

                clients.add(ch);
                new Thread(ch).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(ClientHandler sender, List<ClientHandler> clients){
        for(ClientHandler client : clients){
            if(client != sender){
                try {
                    DataOutputStream out = new DataOutputStream(client.client.getOutputStream());
                    out.writeDouble(sender.inX);
                    out.writeDouble(sender.inY);
                    out.writeInt(sender.inDirection);
                    out.writeInt(sender.inState);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

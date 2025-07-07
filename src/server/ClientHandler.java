package server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{
    Socket client;
    List<ClientHandler> clients;
    DataInputStream in;
    DataOutputStream out;
    double inX;
    double inY;
    int id;
    int inDirection;
    int inState;
    public ClientHandler(Socket client,List<ClientHandler> clients){
        this.client = client;
        this.clients = clients;
    }
    @Override
    public void run() {
        try {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            id = Server.connectedCount;
            out.writeInt(Server.connectedCount);

            while(true){
                try {
                    inX = in.readDouble();
                    inY = in.readDouble();
                    inDirection = in.readInt();
                    inState = in.readInt();
                } catch (EOFException e) {
                    System.out.println("Cliente desconectou: " + client.getInetAddress());
                    break; // Sai do loop para fechar o socket
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

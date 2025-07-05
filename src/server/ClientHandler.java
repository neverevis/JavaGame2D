package server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{
    Socket client;
    List<ClientHandler> clients;
    double inX;
    double inY;
    int inDirection;
    int inState;
    public ClientHandler(Socket client,List<ClientHandler> clients){
        this.client = client;
        this.clients = clients;
    }
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            while(true){
                inX = in.readDouble();
                inY = in.readDouble();
                inDirection = in.readInt();
                inState = in.readInt();

                Server.broadcast(this,clients);
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

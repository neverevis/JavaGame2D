package server;

import elements.entities.Player;
import elements.states.Direction;
import game.GamePanel;
import utilities.Global;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    DataOutputStream out;
    public DataInputStream in;
    GamePanel gp;
    public boolean connected = false;
    Scanner sc = new Scanner(System.in);
    int id;

    //dados para receber
    int connectedCount;
    int inId;
    double inX;
    double inY;
    int inDirection;
    int inState;

    //dados para enviar
    double outX;
    double outY;
    int outDirection;
    int outState;

    public Client(GamePanel gp){
        this.gp = gp;

        new Thread(this::connect).start();
    }

    public void connect() {
        System.out.println("Digite o ip do servidor: ");
        String ip = sc.nextLine();
        int port = 12345;

        try {
            Socket client = new Socket(ip,port);
            System.out.println("Conectado ao servidor!");
            connected = true;

            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());

            id = in.readInt();
            System.out.println("Seu ID: " + id);

            outX = gp.activeWorld.player.position.x;
            outY = gp.activeWorld.player.position.y;
            outDirection = gp.activeWorld.player.sprite.direction.ordinal();
            if(gp.activeWorld.player.sprite.moving)
                outState = 1;
            else
                outState = 0;

            out.writeDouble(outX);
            out.writeDouble(outY);
            out.writeInt(outDirection);
            out.writeInt(outState);

            for(int i = 1; i < id; i++) {
                System.out.println("instanciando player: " + i);
                Player newP = new Player(gp, gp.activeWorld, true);
                newP.id = i;
                gp.activeWorld.connectedPlayers.add(newP);
                gp.activeWorld.elements.add(newP);
            }
            System.out.println("quantidade de players instanciados na lista: " + gp.activeWorld.connectedPlayers.size());

            new Thread(this::send).start();
            new Thread(this::receive).start();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Falha ao se conectar no servidor");
        }

    }

    public void send(){
        while(connected){
            try{
                outX = gp.activeWorld.player.position.x;
                outY = gp.activeWorld.player.position.y;
                outDirection = gp.activeWorld.player.sprite.direction.ordinal();
                if(gp.activeWorld.player.sprite.moving)
                    outState = 1;
                else
                    outState = 0;

                out.writeDouble(outX);
                out.writeDouble(outY);
                out.writeInt(outDirection);
                out.writeInt(outState);

                sleep();

            }
            catch (EOFException e) {
                System.out.println("Cliente desconectou normalmente.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void receive(){
        try {
            while(connected){
                connectedCount = in.readInt();
                for(int i = 1; i <= connectedCount; i++) {
                    if(i != id) {
                        inId = in.readInt();
                        inX = in.readDouble();
                        inY = in.readDouble();
                        inDirection = in.readInt();
                        inState = in.readInt();

                        System.out.println("=-=Pacotes=-=");
                        System.out.println("Conectados recebido: " + connectedCount);
                        System.out.println("Id recebido: " + inId);
                        System.out.println("X recebido: " + inX);
                        System.out.println("Y recebido: " + inY);
                        System.out.println("Direção recebido: " + inDirection);
                        System.out.println("State recebido: " + inState);

                        if (connectedCount != gp.activeWorld.connectedPlayers.size() + 1) {
                            Player newP = new Player(gp, gp.activeWorld, true);
                            newP.id = connectedCount;
                            gp.activeWorld.connectedPlayers.add(newP);
                            gp.activeWorld.elements.add(newP);
                        }

                        //achar o player que está sendo atualizado
                        Player connectedPlayer = null;
                        for (Player p : gp.activeWorld.connectedPlayers) {
                            if (p.id == inId) {
                                connectedPlayer = p;
                                System.out.println("achou: " + connectedPlayer.id);
                            }
                        }

                        //atualizar
                        if (connectedPlayer != null) {
                            connectedPlayer.position.set(inX, inY);

                            if (inDirection == 0) {
                                connectedPlayer.sprite.setDirection(Direction.DOWN);
                            } else if (inDirection == 1) {
                                connectedPlayer.sprite.setDirection(Direction.UP);
                            } else if (inDirection == 2) {
                                connectedPlayer.sprite.setDirection(Direction.LEFT);
                            } else if (inDirection == 3) {
                                connectedPlayer.sprite.setDirection(Direction.RIGHT);
                            }

                            if (inState == 0) {
                                connectedPlayer.sprite.moving = false;
                            } else if (inState == 1) {
                                connectedPlayer.sprite.moving = true;
                            }
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    void sleep(){
        try{
            Thread.sleep(16);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

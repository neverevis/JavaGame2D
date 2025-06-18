package server;

import elements.states.Direction;
import game.GamePanel;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    DataOutputStream out;
    public DataInputStream in;
    GamePanel gp;
    public boolean connected = false;
    Scanner sc = new Scanner(System.in);

    //dados para receber
    String inId;
    double inX;
    double inY;
    int inDirection;
    int inState;

    //dados para enviar
    String outId;
    double outX;
    double outY;
    int outDirection;
    int outState;

    public Client(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void run() {
        System.out.println("Digite o ip do servidor: ");
        String ip = sc.nextLine();
        System.out.println("Digite a porta do servidor: ");
        int port = sc.nextInt();

        try (Socket client = new Socket(ip,port)){
            System.out.println("Conectado ao servidor!");
            connected = true;

            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());

            while(connected){
                //enviar
                if(connected) {
                    try {

                        out.writeDouble(outX);
                        out.writeDouble(outY);
                        out.writeInt(outDirection);
                        out.writeInt(outState);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //receber
                inX = in.readDouble();
                inY = in.readDouble();
                inDirection = in.readInt();
                inState = in.readInt();

                //atualizar
                gp.activeWorld.connectedPlayer.position.set(inX,inY);

                if(inDirection == 0) {
                    gp.activeWorld.connectedPlayer.sprite.setDirection(Direction.DOWN);
                }else if(inDirection == 1){
                    gp.activeWorld.connectedPlayer.sprite.setDirection(Direction.UP);
                }else if(inDirection == 2){
                    gp.activeWorld.connectedPlayer.sprite.setDirection(Direction.LEFT);
                }else if(inDirection == 3){
                    gp.activeWorld.connectedPlayer.sprite.setDirection(Direction.RIGHT);
                }

                if(inState == 0) {
                    gp.activeWorld.connectedPlayer.sprite.moving = false;
                }else if(inState == 1){
                    gp.activeWorld.connectedPlayer.sprite.moving = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Falha ao se conectar no servidor");
        }

    }

    public void updatePosition(double x, double y){
        this.outX = x;
        this.outY = y;
    }

    public void updateDirection(Direction direction){
        if(direction != null)
            this.outDirection = direction.ordinal();
    }

    public void updateState(int state){
        outState = state;
    }
}

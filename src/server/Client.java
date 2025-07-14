package server;

import core.AnimationTimer;
import core.Core;
import core.G;
import elements.ELM_ConnectedPlayer;
import elements.ELM_Player;
import world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Core core;
    World world;
    Scanner sc = new Scanner(System.in);

    Socket client;
    DataInputStream in;
    DataOutputStream out;

    String ip;
    int port = 12345;
    int id;

    //dados para receber
    int package_type;
    int in_id;
    double in_x;
    double in_y;

    //dados para enviar
    double out_x;
    double out_y;

    boolean connected = false;

    public Client(Core core){
        this.core = core;
        this.world = core.world;
        new Thread(this::connect).start();
    }

    public void connect(){
        System.out.print("Digite o ip do servidor: ");
        ip = sc.nextLine();

        try {
            client = new Socket(ip, port);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            connected = true;

            id = in.readInt();
            world.player.id = id;

            System.out.println("Conectado! seu id: " + id);
        }catch (IOException e){
            System.out.println("Falha ao conectar-se ao servidor");
            e.printStackTrace();
        }

        new AnimationTimer(G.FPS){
            @Override
            public void step(double dt) {
                send();
            }
        }.start();
        new Thread(this::receive).start();
    }

    public void send(){
        try {
            out_x = world.player.pos.x;
            out_y = world.player.pos.y;

            out.writeDouble(out_x);
            out.writeDouble(out_y);
        } catch (IOException e) {
            System.out.println("falha ao enviar pacote");
        }
    }

    public void receive(){
        try{
            while(connected){
                package_type = in.readInt();

                switch (package_type) {
                    case 0 -> playerPackage();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void playerPackage() throws IOException{
        System.out.println("pacote de player recebido!");
        in_id = in.readInt();
        in_x = in.readDouble();
        in_y = in.readDouble();

        ELM_ConnectedPlayer player = null;
        for(ELM_Player p : world.players){
            if(p.id == in_id){
                System.out.println("encontrou!");
                player = (ELM_ConnectedPlayer) p;
            }
        }

        if(player == null){
            System.out.println("criou");
            player = new ELM_ConnectedPlayer(world);
            player.id = in_id;
            world.elements.add(player);
            world.players.add(player);
        }

        player.pos.x = in_x;
        player.pos.y = in_y;
    }
}

package server;

import core.AnimationTimer;
import core.Core;
import core.G;
import elements.ELM_Emmiter;
import elements.ELM_Player;
import world.World;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Core core;
    World world;
    Scanner sc = new Scanner(System.in);

    Socket client;
    DataInputStream in;
    DataOutputStream out;
    BufferedReader string_in;
    PrintWriter string_out;

    String ip;
    int port = 12345;
    int id;

    //dados para receber
    int package_type;
    int in_id;
    String in_nickname;
    double in_x;
    double in_y;
    int in_state;
    int in_facing;

    //dados para enviar
    double out_x;
    double out_y;
    int out_state;
    int out_facing;

    boolean connected = false;

    public Client(Core core){
        this.core = core;
        this.world = core.world;
        new Thread(this::connect).start();
    }

    public void connect(){
        System.out.print("Digite seu nickname: ");
        this.world.player.nickname = sc.nextLine();
        System.out.print("Digite o ip do servidor: ");
        ip = sc.nextLine();

        try {
            client = new Socket(ip, port);

            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            connected = true;

            id = in.readInt();
            world.player.id = id;
            out.writeUTF(world.player.nickname);

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
            out_state = world.player.state;
            out_facing = world.player.facing;

            out.writeDouble(out_x);
            out.writeDouble(out_y);
            out.writeInt(out_state);
            out.writeInt(out_facing);
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
                    case 1 -> playerDisconnected();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void playerPackage() throws IOException{
        in_id = in.readInt();
        in_nickname = in.readUTF();
        in_x = in.readDouble();
        in_y = in.readDouble();
        in_state = in.readInt();
        in_facing = in.readInt();

        ELM_Player player = selectPlayer();

        player.pos.x = in_x;
        player.pos.y = in_y;
        player.state = in_state;
        player.facing = in_facing;

        System.out.println("=-=-= PlayerPackage =-=-=");
        System.out.println("NICKNAME: " + in_nickname);
        System.out.println("X : " + in_x);
        System.out.println("Y : " + in_y);
        System.out.println("STATE : " + in_state);
    }

    public void playerDisconnected() throws IOException{
        in_id = in.readInt();

        ELM_Player player = selectPlayer();

        world.players.remove(player);
        world.elements.remove(player);
        world.collSys.unregister(player.collider);

        System.out.println("Jogador " + player.id + " saiu do jogo.");
    }

    public ELM_Player selectPlayer(){
        ELM_Player player = null;
        for(ELM_Player p : world.players){
            if(p.id == in_id){
                player = p;
                break;
            }
        }

        if(player == null){
            player = new ELM_Player(world,false);
            player.id = in_id;
            player.nickname = in_nickname;
            world.elements.add(player);
            world.players.add(player);

            System.out.println("Jogador " + player.id + " entrou no jogo!");
        }

        return player;
    }
}

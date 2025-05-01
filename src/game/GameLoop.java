package game;

import utilities.Global;

public class GameLoop implements Runnable {

    /*========== ATRIBUTOS ==========*/

    GamePanel gp;                                                       // Instância do tipo GamePanel
    int realFps = 0;                                                    // Frames passando no mundo "real"
    public int finalFps;                                                // O quanto de Frames que foram percorridos a cada segundo

    /*========== CONSTRUTOR ==========*/

    public GameLoop(GamePanel gp) {
        this.gp = gp;                                                   // Instanciado o GamePanel
    }

    /*========== MÉTODOS ==========*/

    @Override
    public void run() { // chamado no instante que a Thread é iniciada automaticamente


        //variáveis do metodo run
        double frameTime = 1_000_000_000.0/ Global.FPS;
        double deltaTime;
        double lastTime = System.nanoTime();
        double nextFrame = System.nanoTime() + frameTime;
        double sleepTime;
        long sleepMilli;
        int sleepNano;

        double start;
        double secondCounter = 0;

        // o loop em si
        while (true) {
            start = System.nanoTime();

            deltaTime = (start - lastTime) / 1_000_000_000;
            lastTime = start;

            // Limita deltaTime máximo
            deltaTime = Math.min(deltaTime, 1.0 / 30.0); // Limita a aceleração excessiva

            update(deltaTime); //Atualiza
            render(); //Pinta

            sleepTime = (nextFrame - System.nanoTime())/1_000_000;
            if(sleepTime < 0)
                sleepTime = 0;

            sleepMilli = (long)sleepTime;
            sleepNano = (int) ((sleepTime - sleepMilli)*1_000_000);

            try {
                Thread.sleep(sleepMilli,sleepNano);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            secondCounter += (System.nanoTime() - start)/1_000_000_000;

            if(secondCounter >= 1){
                finalFps = realFps;
                realFps = 0;
                secondCounter = 0;
            }

            realFps++;
            nextFrame += frameTime;
        }
    }

    /*========== METODO UPDATE ==========*/
    public void update(double deltaTime) {
        gp.update(deltaTime);
    }

    public void render(){
        gp.render();
    }
}

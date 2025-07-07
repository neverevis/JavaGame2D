package game;

import utilities.Global;

public class GameLoop implements Runnable {
    GamePanel gp;
    int realFps = 0;
    public int finalFps;

    public GameLoop(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void run() {
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

            deltaTime = Math.min(deltaTime, 1.0 / 30.0);

            update(deltaTime);
            if(!gp.isVirtual)
                render();

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

    public void update(double deltaTime) {
        gp.update(deltaTime);
    }

    public void render(){
        gp.render();
    }
}

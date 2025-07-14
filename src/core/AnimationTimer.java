package core;

public abstract class AnimationTimer{
    int frameRateTarget = 120;
    boolean running = true;

    public AnimationTimer(int frameRateTarget){
        this.frameRateTarget = frameRateTarget;
    }

    public abstract void step(double dt);

    public void start(){
        new Thread(this::loop).start();
    }

    public void loop(){
        double frameTime = 1e9/ frameRateTarget;
        double dt;
        double lastTime = System.nanoTime();
        double nextFrame = System.nanoTime() + frameTime;
        double sleepTime;
        long sleepMilli;
        int sleepNano;

        double start;
        double secondCounter = 0;

        while (running) {
            start = System.nanoTime();

            dt = (start - lastTime) / 1e9;
            lastTime = start;

            step(dt);

            sleepTime = (nextFrame - System.nanoTime())/1e6;
            if(sleepTime < 0)
                sleepTime = 0;

            sleepMilli = (long)sleepTime;
            sleepNano = (int) ((sleepTime - sleepMilli)*1e6);

            try {
                Thread.sleep(sleepMilli,sleepNano);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            nextFrame += frameTime;
        }
    }

    public void stop(){
        running = false;
    }
}

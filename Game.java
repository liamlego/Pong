package pong;


import pong.listeners.KeyManager;
import pong.states.GameState;
import pong.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    int width, height;

    boolean running = false;

    private Display display;

    private BufferStrategy bs;
    private Graphics g;

    private Thread thread;

    private GameState currentState;

    private KeyManager keyManager;


    public Game(int width, int height) {
        this.width = width;
        this.height = height;


    }

    private void init() {

        display = new Display("Pong", width, height);

        keyManager = new KeyManager();


        display.getFrame().addKeyListener(keyManager);

        currentState = new GameState(width, height, keyManager);




    }

    public void render() {

        if (display.getCanvas().getBufferStrategy() == null)
            display.getCanvas().createBufferStrategy(3);

        bs = display.getCanvas().getBufferStrategy();

        g = bs.getDrawGraphics();

        g.clearRect(0, 0, width, height);

        currentState.render(g);

        bs.show(); // Shows BufferStrategy
        g.dispose(); // disposes of graphics to free up memory

    }

    public void update() {

        keyManager.update();

        currentState.update();

    }



    public void run() {

        init();


        long lastTime = System.nanoTime();
        long currentTime;

        double delta;

        int frames = 0;

        long timeOne = System.nanoTime();
        long timeTwo;
        double change;

        double interval = 0.01666667;

        while (running) {

            currentTime = System.nanoTime();

            delta = (currentTime-lastTime)/1000000000.0;

            if (delta >= interval) {

                // Code

                update();
                render();

                //Log
                lastTime = currentTime;

                frames++;
                if (frames == 60) {
                    timeTwo = System.nanoTime();
                    change = (timeTwo-timeOne)/1000000000.0;
                    System.out.println("Running at: " + frames + " per " + change + " seconds");
                    frames = 0;
                    timeOne = timeTwo;
                }

            }

        }


/*
        int fps = 60;
        double target = 1000000000/fps;

        long lastTime = System.nanoTime();
        long now;

        long timer = 0;
        int ticks = 0;

        double delta = 0; // Delta referring to change in something


        while (running) { // You have to constantly redraw

            now = System.nanoTime();
            delta += (now - lastTime)/target;
            timer += now - lastTime;
            lastTime = now;


            if (delta >= 1) {

                update();
                render();

                ticks++;
                delta--;
            }

/*
            if (timer >= 1000000000) {
                System.out.println("Running at: " + ticks + " per 1second");
                ticks = 0;
                timer = 0;
            }

        }
*/



        stopGame();

    }

    public void startGame() {

        if (running) return;

        running = true;
        thread = new Thread(this);
        thread.start();


    }

    public void stopGame() {
        if (!running) return;
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

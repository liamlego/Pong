package pong.states;

import pong.listeners.KeyManager;

import java.awt.*;

public interface State {


    void update();

    void render(Graphics g);



}

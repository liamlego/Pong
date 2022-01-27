package pong.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyManager implements KeyListener {

    private boolean[] keys = new boolean[4];

    private boolean keyLeftUp = false;
    private boolean keyLeftDown = false;

    private boolean keyRightUp = false;
    private boolean keyRightDown = false;

    public KeyManager() {

        keys[0] = keyLeftUp;
        keys[1] = keyLeftDown;
        keys[2] = keyRightUp;
        keys[3] = keyRightDown;


    }

    public void update() {

        keys[0] = keyLeftUp;
        keys[1] = keyLeftDown;
        keys[2] = keyRightUp;
        keys[3] = keyRightDown;

    }

    public boolean[] getKeys() {

        return keys;

    }


    public boolean isFocusable() {

        return true;
    }


    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) {
            keyLeftUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            keyLeftDown = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keyRightUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keyRightDown = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) {
            keyLeftUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            keyLeftDown = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keyRightUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keyRightDown = false;
        }

    }


}

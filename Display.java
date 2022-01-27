package pong;

import javax.swing.*;
import java.awt.*;

public class Display {

    private JFrame frame;
    private Canvas canvas;

    private String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(title); //initializes JFrame
        frame.setSize(width, height); //Sets size of JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes program as well as window
        frame.setResizable(false); // Makes so you cant resize it
        frame.setLocationRelativeTo(null); //Makes display pop up in center of screen

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false); // Makes JFrame have focus not canvas
        canvas.setBackground(Color.BLACK);

        frame.add(canvas);
        frame.pack();

        frame.setVisible(true); //Shows JFrame
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
}

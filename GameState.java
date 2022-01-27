package pong.states;

import pong.Display;
import pong.listeners.KeyManager;

import java.awt.*;
import java.util.ArrayList;

public class GameState implements State {

    private int width;
    private int height;

    private KeyManager keyManager;

    private int margin = 15;

    private int paddleWidth = 10;
    private int paddleHeight = 50;

    private int leftPaddleY;
    private int rightPaddleY;

    private int leftPaddleX;
    private int rightPaddleX;

    private int ballX;
    private int ballY;

    private int ballSize = 10;

    private int paddleSpeed = 1;

    private int ballSpeedX;
    private int ballSpeedY;

    private Direction ballDirectionX;
    private Direction ballDirectionY;

    private Rectangle lpCollisonBox;
    private Rectangle rpCollisonBox;

    private Rectangle ballCollisionBox;

    private int leftScore;
    private int rightScore;


    public GameState(int width, int height, KeyManager keyManager) {

        this.width = width;
        this.height = height;

        this.keyManager = keyManager;

        leftPaddleY = height/2-paddleHeight/2;
        rightPaddleY = height/2-paddleHeight/2;

        leftPaddleX = margin;
        rightPaddleX = width-(paddleWidth+margin);

        ballX = width/2+10;
        ballY = height/2;

        ballSpeedX = 3;
        ballSpeedY = 0;

        ballDirectionX = Direction.RIGHT;
        ballDirectionY = Direction.DOWN;

        lpCollisonBox = new Rectangle(leftPaddleX, leftPaddleY, paddleWidth, paddleHeight);
        rpCollisonBox = new Rectangle(rightPaddleX, leftPaddleY, paddleWidth, paddleHeight);

        ballCollisionBox = new Rectangle(ballX, ballY, ballSize, ballSize);

        rightScore = 0;
        leftScore = 0;

    }


    public void update() {



        updatePaddles();
        detectCollision();
        updateBallVelocity();


    }


    public void render(Graphics g) {

        g.setColor(Color.WHITE);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 100));
        g.drawString(leftScore + "   " + rightScore, width/2-100, 100);


        // Mid line
        g.fillRect(width/2-5, 0, 5, height);

        //Left paddle
        g.fillRect(leftPaddleX, leftPaddleY, paddleWidth, paddleHeight);

        //Right paddle
        g.fillRect(rightPaddleX, rightPaddleY, paddleWidth, paddleHeight);

        //ball
        g.fillRect(ballX, ballY, ballSize, ballSize);

        checkPoints(g);

    }

    private void updatePaddles() {

        if (keyManager.getKeys()[0]) {
            if (!(lpCollisonBox.y <= 0)) {
                leftPaddleY -= paddleSpeed;
                lpCollisonBox.y -= paddleSpeed;
            }
        }
        if (keyManager.getKeys()[1]) {
            if (!(lpCollisonBox.y+paddleHeight >= height)) {
                leftPaddleY += paddleSpeed;
                lpCollisonBox.y += paddleSpeed;
            }
        }


        if (keyManager.getKeys()[2]) {
            if (!(rpCollisonBox.y <= 0)) {
                rightPaddleY -= paddleSpeed;
                rpCollisonBox.y -= paddleSpeed;
            }
        }
        if (keyManager.getKeys()[3]) {
            if (!(rpCollisonBox.y+paddleHeight >= height)) {
                rightPaddleY += paddleSpeed;
                rpCollisonBox.y += paddleSpeed;
            }
        }

    }

    private void updateBallVelocity() {

        if (ballDirectionX.equals(Direction.RIGHT)) {
            ballX += ballSpeedX;
            ballCollisionBox.x += ballSpeedX;
        } else if (ballDirectionX.equals(Direction.LEFT)) {
            ballX -= ballSpeedX;
            ballCollisionBox.x -= ballSpeedX;
        }

        if (ballDirectionY.equals(Direction.UP)) {
            ballY -= ballSpeedY;
            ballCollisionBox.y -= ballSpeedY;
        } else if (ballDirectionY.equals(Direction.DOWN)) {
            ballY += ballSpeedY;
            ballCollisionBox.y += ballSpeedY;
        }


    }

    private void checkPoints(Graphics g) {
        if (leftScore == 11) {
            paddleSpeed = 0;
            ballSpeedX = 0;
            ballSpeedY = 0;

            g.setColor(Color.GREEN);
            g.setFont(g.getFont().deriveFont(Font.PLAIN, 34));
            g.drawString("Left Won!", width/2-300, height/2);
        } else if (rightScore == 11) {
            paddleSpeed = 0;
            ballSpeedX = 0;
            ballSpeedY = 0;

            g.setColor(Color.GREEN);
            g.setFont(g.getFont().deriveFont(Font.PLAIN, 34));
            g.drawString("Right Won!", width/2+100, height/2);
        }
    }

    private void detectCollision() {

        // Ball Collisions

        if (ballCollisionBox.intersects(lpCollisonBox)) {
            checkLeftPaddlePart();
            //System.out.println("Hit left paddle");
            ballDirectionX = Direction.RIGHT;
        }

        if (ballCollisionBox.intersects(rpCollisonBox)) {
            checkRightPaddlePart();
            //System.out.println("Hit right paddle");
            ballDirectionX = Direction.LEFT;
        }

        //Edges of window

        if (ballCollisionBox.y >= height) {
            ballDirectionY = Direction.UP;
        }

        if (ballCollisionBox.y <= 0) {
            ballDirectionY = Direction.DOWN;
        }


        // Game Points

        if (ballCollisionBox.x >= width) {
            leftScore++;
            ballX = width/2;
            ballCollisionBox.x = width/2;
            ballDirectionX = Direction.LEFT;
            ballSpeedX = 3;
            ballSpeedY = 3;
            paddleSpeed = 7;
        }

        if (ballCollisionBox.x <= 0) {
            rightScore++;
            ballX = width/2;
            ballCollisionBox.x = width/2;
            ballDirectionX = Direction.RIGHT;
            ballSpeedX = 2;
            ballSpeedY = 2;
            paddleSpeed = 7;
        }


    }

    private void checkRightPaddlePart() {

        //Right paddle

        //DETECTING which part of the paddle
        if (ballCollisionBox.getCenterY() >= rpCollisonBox.getCenterY() - 2 && ballCollisionBox.getCenterY() <= rpCollisonBox.getCenterY() + 2) {
            ballDirectionY = Direction.ZERO;
            ballSpeedX = 4;
        } else if (ballCollisionBox.getCenterY() <= rpCollisonBox.getCenterY() - 3 && ballCollisionBox.getCenterY() >= rpCollisonBox.getCenterY() - 4) {
            ballDirectionY = Direction.UP;
            ballSpeedY = 3;
            ballSpeedX = 4;
        }  else if (ballCollisionBox.getCenterY() <= rpCollisonBox.getCenterY() - 5) {
            ballDirectionY = Direction.UP;
            ballSpeedY = 4;
            ballSpeedX = 6;
        } else if (ballCollisionBox.getCenterY() >= rpCollisonBox.getCenterY() + 3 && ballCollisionBox.getCenterY() <= rpCollisonBox.getCenterY() + 4) {
            ballDirectionY = Direction.DOWN;
            ballSpeedY = 3;
            ballSpeedX = 4;
        } else if (ballCollisionBox.getCenterY() >= rpCollisonBox.getCenterY() + 5) {
            ballDirectionY = Direction.DOWN;
            ballSpeedY = 4;
            ballSpeedX = 6;
        }

    }

    private void checkLeftPaddlePart() {

        //Left paddle

        //DETECTING which part of the paddle
        if (ballCollisionBox.getCenterY() >= lpCollisonBox.getCenterY() - 2 && ballCollisionBox.getCenterY() <= lpCollisonBox.getCenterY() + 2) {
            ballDirectionY = Direction.ZERO;
            ballSpeedX = 4;
        } else if (ballCollisionBox.getCenterY() <= lpCollisonBox.getCenterY() - 3 && ballCollisionBox.getCenterY() >= lpCollisonBox.getCenterY() - 4) {
            ballDirectionY = Direction.UP;
            ballSpeedY = 3;
            ballSpeedX = 4;
        }  else if (ballCollisionBox.getCenterY() <= lpCollisonBox.getCenterY() - 5) {
            ballDirectionY = Direction.UP;
            ballSpeedY = 4;
            ballSpeedX = 6;
        } else if (ballCollisionBox.getCenterY() >= lpCollisonBox.getCenterY() + 3 && ballCollisionBox.getCenterY() <= lpCollisonBox.getCenterY() + 4) {
            ballDirectionY = Direction.DOWN;
            ballSpeedY = 3;
            ballSpeedX = 4;
        } else if (ballCollisionBox.getCenterY() >= lpCollisonBox.getCenterY() + 5) {
            ballDirectionY = Direction.DOWN;
            ballSpeedY = 4;
            ballSpeedX = 6;
        }

    }



    private enum Direction {

        LEFT, RIGHT, UP, DOWN, ZERO

    }

}



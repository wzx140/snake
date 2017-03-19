package core;

import GUI.GUIPanel;
import base.Point;
import base.Snake;
import util.InitPoint;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * to operate the game
 */
public class GamePanel extends GUIPanel {
    private Snake snake;
    private Point deadPoint;
    private Point foodPoint;
    private boolean gameOver;
    private boolean gamePause;
    private int row;
    private int column;
    private int xOffset;  //point x (relative to column) offset
    private int yOffset;  //above
    private Timer snakeTimer; //timely move snake
    private int dir;  //last direction
    private int pauseTime; //the time you have paused
    private Timer pauseTimer; //set pauseTime

    public GamePanel(int row, int column) {
        initListen();
        initTimer();
        this.row = row;
        this.column = column;
        gameOver = true;
        gamePause = false;
        xOffset = 500 / (column+1);
        yOffset = 500 / (row+1);
        pauseTime =0;
    }

    private void initTimer() {
        pauseTimer =new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseTime++;
                System.out.println("已暂停"+ pauseTime +"秒");
            }
        });

        snakeTimer =new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (snake.canMove(dir) && !snake.isBorder(dir)) {
                        handleMove(dir);
                        print();
                    }
                    if (!snake.isAlive()&&!gameOver) {
                        gameOver = true;
                        snakeTimer.stop();
                        printGG();
                    }
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    //init snake,deadPoint,foodPoint and last direction only at beginning
    private void initElements() {
        snake = new Snake(row, column);
        deadPoint = InitPoint.initPoint(row, column, snake.getBodys());
        foodPoint = InitPoint.initPoint(row, column, snake.getBodys(), deadPoint);
        dir = snake.initDir();
    }

    //init listeners
    private void initListen() {
        startBton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameOver) {
                    gameOver = false;
                    initElements();
                    print();
                    snakeTimer.start();
                }
            }
        });

        pauseBton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver && !gamePause) {
                    gamePause = true;
                    snakeTimer.stop();
                    pauseTimer.start();
                } else if (!gameOver && gamePause) {
                    gamePause = false;
                    pauseTimer.stop();
                    snakeTimer.restart();
                }
            }
        });

        stopBton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    gameOver = true;
                    gamePause = false;
                    pauseTimer.stop();
                    snake.dead();
                    printGG();
                }
            }
        });
        displayPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    if (gamePause) {

                    }else if (e.getKeyChar() == 'w') {
                        snakeTimer.stop();
                        if (snake.canMove(1) && !snake.isBorder(1)) {
                            handleMove(1);
                            dir=1;
                            print();
                        }
                        if (!snake.isAlive()&&!gameOver) {
                            gameOver = true;
                            printGG();
                        }
                        snakeTimer.restart();
                    } else if (e.getKeyChar() == 'a') {
                        snakeTimer.stop();
                        if (snake.canMove(2) && !snake.isBorder(2)) {
                            handleMove(2);
                            dir=2;
                            print();
                        }
                        if (!snake.isAlive()&&!gameOver) {
                            gameOver = true;
                            printGG();
                        }
                        snakeTimer.restart();
                    } else if (e.getKeyChar() == 's') {
                        snakeTimer.stop();
                        if (snake.canMove(3) && !snake.isBorder(3)) {
                            handleMove(3);
                            dir = 3;
                            print();
                        }
                        if (!snake.isAlive()&&!gameOver) {
                            gameOver = true;
                            printGG();
                        }
                        snakeTimer.restart();
                    } else if (e.getKeyChar() == 'd') {
                        snakeTimer.stop();
                        if (snake.canMove(4) && !snake.isBorder(4)) {
                            handleMove(4);
                            dir = 4;
                            print();
                        }
                        if (!snake.isAlive()&&!gameOver) {
                            gameOver = true;
                            printGG();
                        }
                        snakeTimer.restart();
                    }
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * judge if next(dir) head reach food or dead
     * and handle the situation
     */
    private void handleMove(int dir) throws CloneNotSupportedException {
        Point tempHead = snake.nextHead(dir);
        ArrayList<Point> tempBody = snake.getBodys();
        boolean flag = false;
        for (int i=1;i<tempBody.size();i++) {
            if (tempBody.get(i).equal(tempHead)) {
                flag = true;
                break;
            }
        }
        if (foodPoint.equal(tempHead)&&!flag) {
            snake.addHead(foodPoint);
            produceElements();
        } else if (deadPoint.equal(tempHead)&&!flag) {
            snake.dead();
        } else if (flag) {
            snake.dead();
        } else {
            snake.move(dir);
        }
    }

    private void printGG() {
        cls();
        displayPanel.getGraphics().drawString("GAME OVER",150,100);
        displayPanel.getGraphics().drawString("YOUR SCORE: "+snake.size(),150,200);
        displayPanel.getGraphics().drawString("YOU Pause : "+ pauseTime +"s",150,300);
    }
    //produce deadPoint and foodPoint only when snake eat a foodPoint
    private void produceElements() {
        deadPoint = InitPoint.initPoint(row, column, snake.getBodys());
        foodPoint = InitPoint.initPoint(row, column, snake.getBodys(), deadPoint);
    }

    //print snake ,food and dead point
    private void print() {
        cls();
        ArrayList<Point> temp = snake.getBodys();
        printHOrF(temp.get(0));
        for (int i = 1; i < temp.size(); i++) {
            printBody(temp.get(i));
        }
        printHOrF(foodPoint);
        printDeadPt(deadPoint);
        displayPanel.requestFocus();
    }

    /**
     * print one head point
     * with its point
     * also it can print food point
     */
    private void printHOrF(base.Point point) {
        //displayPanel.getGraphics().fillOval(point.getX() * xOffset, point.getY() * yOffset, xOffset, yOffset);
        displayPanel.getGraphics().drawImage(new ImageIcon(GamePanel.class.getResource("/picture/head.jpg")).getImage(), point.getX() * xOffset, point.getY() * yOffset, xOffset, yOffset, null);

    }

    private void printBody(base.Point point) {
        displayPanel.getGraphics().fillOval(point.getX() * xOffset, point.getY() * yOffset, xOffset, yOffset);
    }
    //print dead point
    private void printDeadPt(Point point) {
        //displayPanel.getGraphics().fillRect(point.getX() * xOffset, point.getY() * yOffset, xOffset, yOffset);
        displayPanel.getGraphics().drawImage(new ImageIcon(GamePanel.class.getResource("/picture/dead.jpg")).getImage(), point.getX() * xOffset, point.getY() * yOffset, xOffset, yOffset, null);
    }

    //clear screen
    private void cls() {
        displayPanel.getGraphics().clearRect(0, 0, 500, 500);
    }

    public JPanel mainPanel() {
        return mainPanel;
    }
}

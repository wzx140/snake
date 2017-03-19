package base;

import util.RandomUtil;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * the snake
 * the points begin from (0,0)
 */
public class Snake {

    /**
     * body of snake
     * the head is the index 0
     * the index should be sorted by point
     */
    private ArrayList<Point> bodys;

    private int row;
    private int column;  //the border of the game
    private ReentrantReadWriteLock lock;
    private boolean alive;

    public Snake(int row, int column) {
        this.row = row;
        this.column = column;
        alive = true;
        bodys = new ArrayList<>();
        lock = new ReentrantReadWriteLock();

        //init head
        int headX = RandomUtil.restrictedRandom(1, row - 1);
        int headY = RandomUtil.restrictedRandom(1, column - 1);
        bodys.add(new Point(headX, headY));

        //init first bodys
        bodys.add(initBody(RandomUtil.restrictedRandom(0, 3)));

    }

    /**
     * snake moves by dir
     * dir:1 ->up,2 ->left,3 ->down,4 ->right
     * protected by readAndWriteLock
     */
    public void move(int dir) {
        lock.writeLock().lock();
        try {
            int x, y;
            switch (dir) {
                case 1:
                    x = getHead().getX();
                    y = getHead().getY() - 1;
                    for (int i = bodys.size() - 1; i > 0; i--) {
                        bodys.get(i).setPoint(bodys.get(i - 1));
                    }
                    getHead().setPoint(new Point(x, y));
                    break;
                case 2:
                    x = getHead().getX() - 1;
                    y = getHead().getY();
                    for (int i = bodys.size() - 1; i > 0; i--) {
                        bodys.get(i).setPoint(bodys.get(i - 1));
                    }
                    getHead().setPoint(new Point(x, y));
                    break;
                case 3:
                    x = getHead().getX();
                    y = getHead().getY() + 1;
                    for (int i = bodys.size() - 1; i > 0; i--) {
                        bodys.get(i).setPoint(bodys.get(i - 1));
                    }
                    getHead().setPoint(new Point(x, y));
                    break;
                case 4:
                    x = getHead().getX() + 1;
                    y = getHead().getY();
                    for (int i = bodys.size() - 1; i > 0; i--) {
                        bodys.get(i).setPoint(bodys.get(i - 1));
                    }
                    getHead().setPoint(new Point(x, y));
                    break;
                default:
                    break;
            }

        } finally {
            lock.writeLock().unlock();
        }

    }

    public int size() {
        return bodys.size();
    }

    //judge that the dir is legal
    public boolean canMove(int dir) {
        if (!isAlive()) {
            return false;
        }
        switch (dir) {
            case 1:
                if (bodys.get(1).getY() == getHead().getY() - 1) {
                    return false;
                }
                break;
            case 2:
                if (bodys.get(1).getX() == getHead().getX() - 1) {
                    return false;
                }
                break;
            case 3:
                if (bodys.get(1).getY() == getHead().getY() + 1) {
                    return false;
                }
                break;
            case 4:
                if (bodys.get(1).getX() == getHead().getX() + 1) {
                    return false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    //judge that next(dir) head is beyond border
    public boolean isBorder(int dir) throws CloneNotSupportedException {
        if (!isAlive()) {
            return true;
        }
        Point tempHead = (Point) getHead().clone();
        switch (dir) {
            case 1:
                if ((tempHead.getY() - 1) == -1) {
                    alive = false;
                    return true;
                }
                break;
            case 2:
                if ((tempHead.getX() - 1) == -1) {
                    alive = false;
                    return true;
                }
                break;
            case 3:
                if ((tempHead.getY() + 1) == row + 2) {
                    alive = false;
                    return true;
                }
                break;
            case 4:
                if ((tempHead.getX() + 1) == column + 2) {
                    alive = false;
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    public void addHead(Point point) {
        bodys.add(0, point);
    }

    public ArrayList<Point> getBodys() {
        return bodys;
    }

    public boolean isAlive() {
        return alive;
    }

    public void dead() {
        alive = false;
    }

    /**
     * return next(dir) head point
     * if dir can not recognize
     * return null
     */
    public Point nextHead(int dir) throws CloneNotSupportedException {
        Point tempHead = (Point) getHead().clone();
        switch (dir) {
            case 1:
                tempHead.setPoint(new Point(tempHead.getX(), tempHead.getY() - 1));
                return tempHead;
            case 2:
                tempHead.setPoint(new Point(tempHead.getX() - 1, tempHead.getY()));
                return tempHead;
            case 3:
                tempHead.setPoint(new Point(tempHead.getX(), tempHead.getY() + 1));
                return tempHead;
            case 4:
                tempHead.setPoint(new Point(tempHead.getX() + 1, tempHead.getY()));
                return tempHead;
            default:
                break;
        }
        return null;

    }

    //return the origin direction
    public int initDir() {
        if (getHead().getX() - 1 == getBodys().get(1).getX()) {
            return 4;
        } else if (getHead().getX() == getBodys().get(1).getX() - 1) {
            return 2;
        } else if (getHead().getY() == getBodys().get(1).getY() - 1) {
            return 1;
        } else if (getHead().getY() - 1 == getBodys().get(1).getY()) {
            return 3;
        }
        return 0;
    }

    /**
     * base on the pattern(0,1,2,3)
     * return the first bodys
     * else return null
     */
    private Point initBody(int pattern) {
        switch (pattern) {
            case 0:
                return new Point(getHead().getX(), getHead().getY() - 1);
            case 1:
                return new Point(getHead().getX() - 1, getHead().getY());
            case 2:
                return new Point(getHead().getX(), getHead().getY() + 1);
            case 3:
                return new Point(getHead().getX() + 1, getHead().getY());
            default:
                return null;
        }
    }

    private Point getHead() {
        return bodys.get(0);
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Snake snake = new Snake(20, 20);
        snake.addHead(new Point(snake.getHead().getX() - 1, snake.getHead().getY()));
        snake.addHead(new Point(snake.getHead().getX() - 1, snake.getHead().getY()));
//        for (int i=0;i<10;i++) {
        ArrayList<Point> temp = snake.getBodys();
        for (Point point : temp) {
            System.out.println(point.getX() + " " + point.getY());
        }
        System.out.println(" ");
        snake.isBorder(1);
        temp = snake.getBodys();
        for (Point point : temp) {
            System.out.println(point.getX() + " " + point.getY());
        }
        System.out.println(" ");
        //snake.move(4);
//    }

    }
}


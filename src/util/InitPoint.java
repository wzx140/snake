package util;

import base.Point;

import java.util.ArrayList;

/**
 * Created by wzx on 17-3-18.
 */
public class InitPoint {

    /**
     * @param row the row of game panel
     * @param column the column of game panel
     * @param bodys the body of a snake
     * @return a random point
     * within the row and the column
     * except bodys' point
     */
    public static Point initPoint(int row, int column, ArrayList<Point> bodys) {
        boolean flag = false;
        Point result=new Point();
        int x,y;
        while (!flag) {
            x = RandomUtil.restrictedRandom(0, column);
            y = RandomUtil.restrictedRandom(0, row);
            result = new Point(x, y);
            out:for (Point point : bodys) {
                if (point.equal(result)) {
                    flag = false;
                    break out;
                } else {
                    flag = true;
                }
            }
        }
        return result;
    }

    /**
     * except otherPoint
     * @see #initPoint(int, int, ArrayList)
     */
    public static Point initPoint(int row, int column, ArrayList<Point> bodys,Point otherPoint) {
        boolean flag = false;
        Point result=new Point();
        int x,y;
        while (!flag) {
            x = RandomUtil.restrictedRandom(0, column);
            y = RandomUtil.restrictedRandom(0, row);
            result = new Point(x, y);
            out:for (Point point : bodys) {
                if (point.equal(result)) {
                    flag = false;
                    break out;
                } else {
                    flag = true;
                }
            }
            if (flag&&otherPoint.equal(result)){
                flag = false;
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        ArrayList<Point> bodys = new ArrayList<>();
//        bodys.add(new Point(1, 1));
//        bodys.add(new Point(1, 2));
//        bodys.add(new Point(1, 3));
//        bodys.add(new Point(1, 0));
//        for (int i = 0; i < 10; i++) {
//            System.out.println(initPoint(2,3,bodys).getX());
//            System.out.println(initPoint(2,3,bodys).getY());
//            System.out.println(" ");
//        }
//
//    }
}

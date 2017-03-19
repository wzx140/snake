package base;

/**
 * Created by wzx on 17-3-18.
 */
public class Point implements Cloneable{
    protected int x;
    protected int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPoint(Point point) {
        x = point.getX();
        y = point.getY();
    }

    public boolean equal(Point point) {
        if (x == point.getX() && y == point.getY()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

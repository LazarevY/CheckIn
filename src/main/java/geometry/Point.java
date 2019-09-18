package geometry;

import util.SimpleMath;

public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int distanceSqr(Point point1, Point point2){
        return SimpleMath.sqr(point1.x - point2.x) + SimpleMath.sqr(point1.y - point2.y);
    }
}

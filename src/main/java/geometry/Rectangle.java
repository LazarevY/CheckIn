package geometry;

import java.util.ArrayList;

public class Rectangle implements Shape {
    private int x, y;
    private int halfWidth, halfHeight;

    public Rectangle(int x, int y, int halfWidth, int halfHeight) {
        this.x = x;
        this.y = y;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public boolean contains(int x, int y) {
        return Math.abs(this.x - x) < halfWidth && Math.abs(this.y - y) < halfHeight;
    }

    public boolean contains(Point point) {
        return contains(point.getX(), point.getY());
    }

    @Override
    public boolean contains(Point p, int radius) {
        return Math.abs(p.getY() - y) < halfHeight + radius && Math.abs(p.getX() - x) < halfWidth + radius;
    }

    @Override
    public ArrayList<Point> getBoundsPoints() {
        ArrayList<Point> res = new ArrayList<>();
        res.add(new Point(x + halfWidth, y + halfHeight));
        res.add(new Point(x - halfWidth, y + halfHeight));
        res.add(new Point(x + halfWidth, y - halfHeight));
        res.add(new Point(x - halfWidth, y - halfHeight));
        return res;
    }

}

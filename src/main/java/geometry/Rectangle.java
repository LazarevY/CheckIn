package geometry;

import map.Map;

public class Rectangle implements Shape {
    private int x, y;
    private int hWidth, hHeigth;

    public Rectangle(int x, int y, int hWidth, int hHeigth) {
        this.x = x;
        this.y = y;
        this.hWidth = hWidth;
        this.hHeigth = hHeigth;
    }

    public boolean contains(int x, int y) {
        return Math.abs(this.x - x) < hWidth && Math.abs(this.y - y) < hHeigth;
    }

    public boolean contains(Point point) {
        return contains(point.getX(), point.getY());
    }

    @Override
    public boolean contains(Point p, int radius) {
        return Math.abs(p.getY() - y) < hHeigth + radius && Math.abs(p.getX() - x) < hWidth + radius;
    }
}

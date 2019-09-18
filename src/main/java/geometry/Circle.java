package geometry;

public class Circle implements Shape {
    private Point center;
    private int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean contains(int x, int y) {
        return Math.pow(x - center.getX(), 2) + Math.pow(y - center.getY(),2) < Math.pow(radius,2);
    }

    @Override
    public boolean contains(Point point) {
        return contains(point.getX(), point.getY());
    }

    @Override
    public boolean contains(Point p, int radius) {
        return Point.distanceSqr(p, center) < radius * radius;
    }
}

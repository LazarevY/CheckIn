package geometry.geojson;

import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;
import java.util.List;

public class Circle extends Geometry {

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    private Point center;
    private double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public boolean intersects(Point point, double radius) {
        return Point.distanceBetween(center, point) < this.radius + radius;
    }

    @Override
    public List<Point> getBoundPoints() {
        return Arrays.asList(new Point(center.getX() - radius, center.getY()),
                new Point(center.getX() + radius, center.getY()),
                new Point(center.getX(), center.getY() - radius),
                new Point(center.getX(), center.getY() + radius));
    }

    @Override
    public void draw(GraphicsContext javafx) {
        javafx.fillOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
    }
}

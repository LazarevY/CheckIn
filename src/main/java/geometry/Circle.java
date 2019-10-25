package geometry;
/**
 * @author LAZAREV
 */

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;

/**
 * Класс, описывающий окружность как границу объекта
 */
public class Circle implements Shape {
    private Point center;
    private int radius;

    @JsonCreator
    public Circle(@JsonProperty("center") Point center, @JsonProperty("radius") int radius) {
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
        return Point.distanceSqr(p, center) < (radius + this.radius) * (radius + this.radius);
    }

    @Override
    public ArrayList<Point> getBoundsPoints() {
        ArrayList<Point> res = new ArrayList<>();
        res.add(new Point(center.getX() + radius, center.getY()));
        res.add(new Point(center.getX(), center.getY() + radius));
        res.add(new Point(center.getX() - radius, center.getY()));
        res.add(new Point(center.getX(), center.getY() - radius));
        return res;
    }
}

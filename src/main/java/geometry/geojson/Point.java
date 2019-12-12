package geometry.geojson;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import util.Vector2D;

import java.util.Arrays;
import java.util.List;

public class Point extends Geometry {

    private double x;
    private double y;

    public Point(double x,
                 double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p){
        x = p.x;
        y = p.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double distanceBetween(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static Point subVector(Point start, Point end){
        return new Point(end.x - start.x, end.y - start.y);
    }

    public static Point sum(Point p, Vector2D v){
        return new Point(p.x +v.x(), p.y + v.y());
    }

    @Override
    public boolean intersects(Point point, double radius) {
        return distanceBetween(this, point) < radius;
    }

    @Override
    public List<Point> getBoundPoints() {
        return Arrays.asList(this);
    }

    @Override
    public void draw(GraphicsContext javafx) {
        Paint fill = javafx.getFill();
        double alpha = javafx.getGlobalAlpha();
        javafx.setFill(Color.DIMGREY);
        javafx.setGlobalAlpha(1.0);
        javafx.fillRect(x-3, y-3, 6, 6);
        javafx.setFill(fill);
        javafx.setGlobalAlpha(alpha);
    }
}

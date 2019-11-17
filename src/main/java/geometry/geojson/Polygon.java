package geometry.geojson;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * This class describe polygon as figure bounded by a closed polyline without self intersections
 */
public class Polygon extends Geometry {

    private List<Point> points;

    public Polygon(List<Point> points){
        this.points = new ArrayList<>(points);
    }

    public Polygon(Point p1, Point p2, Point p3, Point... points){
        this.points = new ArrayList<>();
        this.points.add(p1);
        this.points.add(p2);
        this.points.add(p3);
        this.points.addAll(Arrays.asList(points));
    }

    public List<Point> getPoints(){
        return points;
    }


    @Override
    public boolean intersects(Point point, double radius) {
        return containsPoint(point) || intersectsWithBounds(point, radius);
    }

    private boolean containsPoint(Point point){
        return false;
    }

    private boolean intersectsWithBounds(Point point, double radius){
        Circle circle = new Circle(point, radius);
        Iterator<Point> it = points.iterator();
        Point curr = it.next();
        boolean boundIntersects = Geometry.lineCircleIntersects(curr, points.get(points.size() - 1), circle);
        Point next;
        while (it.hasNext()){
            next = it.next();
            boundIntersects |= Geometry.lineCircleIntersects(curr, next, circle);
            if (boundIntersects)
                return true;
            curr = next;
        }

        return false;
    }

    @Override
    public List<Point> getBoundPoints() {
        Point maxX = new Point(points.get(0));
        Point minX = new Point(points.get(0));
        Point maxY = new Point(points.get(0));
        Point minY = new Point(points.get(0));

        for (Point p : points) {
            if(p.getX() < minX.getX())
                minX = p;
            else if (p.getX() > maxX.getX())
                maxX = p;
            if(p.getY() < minY.getY())
                minY = p;
            else if (p.getY() > maxY.getX())
                maxX = p;
        }

        HashSet<Point> points = new HashSet<>();
        points.add(maxX);
        points.add(maxY);
        points.add(minX);
        points.add(minY);
        return new ArrayList<>(points);
    }

    @Override
    public void draw(GraphicsContext javafx) {
        javafx.setFill(Color.rgb(80, 80, 200));
        javafx.setGlobalAlpha(0.6);
        javafx.beginPath();
        for (Point p : points){
            p.draw(javafx);
            javafx.lineTo(p.getX(), p.getY());
        }
        javafx.closePath();
        javafx.stroke();
        javafx.fill();
    }
}

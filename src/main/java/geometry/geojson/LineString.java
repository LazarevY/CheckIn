package geometry.geojson;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;

/**
 * Class polyline
 */
public class LineString extends Geometry{
    private List<Point> points;

    public LineString(List<Point> points){
        this.points = new ArrayList<>(points);
    }

    public LineString(Point... points){
        this.points = Arrays.asList(points);
    }

    public List<Point> getPoints(){
        return points;
    }
    @Override
    public boolean intersects(Point point, double radius) {
        Circle circle = new Circle(point, radius);
        Iterator<Point> it = points.iterator();
        Point curr = it.next();
        Point next;
        while (it.hasNext()){
            next = it.next();
            if (Geometry.lineCircleIntersects(curr, next, circle))
                return true;
            curr = next;
        }

        return false;
    }

    @Override
    public List<Point> getBoundPoints() {
        Point maxX = new Point(points.get(0));
        Point minX = maxX;
        Point maxY = maxX;
        Point minY = maxX;

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

        javafx.beginPath();
        for (Point p : points){
            p.draw(javafx);
            javafx.lineTo(p.getX(), p.getY());
            javafx.moveTo(p.getX(), p.getY());
        }
        javafx.closePath();
        javafx.stroke();
    }
}

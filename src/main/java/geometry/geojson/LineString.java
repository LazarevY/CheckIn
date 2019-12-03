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
        return points;
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

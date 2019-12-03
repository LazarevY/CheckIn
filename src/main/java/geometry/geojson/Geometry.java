package geometry.geojson;

import geometry.IJavaFXDrawable;

import java.util.List;

public abstract class Geometry implements IJavaFXDrawable {
    public abstract boolean intersects(Point point, double radius);
    public abstract List<Point> getBoundPoints();


    public static boolean lineCircleIntersects(Point startLine, Point endLine, Circle circle){
        boolean containsEnds = circle.intersects(startLine, 0) || circle.intersects(endLine, 0);
        if (containsEnds)
            return true;

        Point center = circle.getCenter();

        double a = endLine.getX() - startLine.getX();
        double b = startLine.getY() - endLine.getY();
        double c = startLine.getX() * endLine.getY()  - startLine.getY() * endLine.getX();
        double factor = 1 / (Math.sqrt(a * a + b * b));

        a *= factor;
        b *= factor;
        c *= factor;

        double distance = Math.abs(a * center.getY() + b * center.getX() + c);

        if (distance > circle.getRadius())
            return false;

        Point v1 = Point.subVector(startLine, center);
        Point v2 = Point.subVector(endLine, center);

        return v1.getX() * v2.getX() < 0 && v1.getY() * v1.getY() >= 0 || v1.getY() * v2.getY() < 0 && v1.getX() * v1.getX() >= 0;
    }

    public static boolean lineCircleIntersects(Point line1, Point line2, Point circleCenter, double circleRadius){
        return lineCircleIntersects(line1, line2, new Circle(circleCenter, circleRadius));
    }
}

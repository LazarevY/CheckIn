package util;

import geometry.geojson.Circle;
import geometry.geojson.Point;

public class GeometryUtil {
    public static boolean limitedLineAndCircleIntersection(LimitedLine line, Circle circle){
        boolean containsEnds = circle.intersects(line.getP1(), 0) ||
                                circle.intersects(line.getP2(), 0);
        if (containsEnds)
            return true;
        double d = line.distanceToPoint(circle.getCenter());
        Point v1 = Point.subVector(line.getP1(), circle.getCenter());
        Point v2 = Point.subVector(line.getP2(), circle.getCenter());

        return d <= circle.getRadius() &&
                (v1.getX() * v2.getX() < 0 && v1.getY() * v1.getY() >= 0 || v1.getY() * v2.getY() < 0 && v1.getX() * v1.getX() >= 0);
    }
}

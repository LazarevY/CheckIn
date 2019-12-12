package util;

import geometry.geojson.Point;

public class Line {
    private Vector2D normal;
    private double distance;

    public Line(Vector2D normal, double distance) {
        this.normal = normal;
        this.distance = distance;
    }

    public Line(Point p1, Point p2){
        normal = new Vector2D(p2, p1).getNormalVector().getNormalized();
        distance = Vector2D.dot(normal, new Vector2D(p1.getX(), p1.getY()));
    }

    public double distanceToPoint(Point p){
        return Math.abs(signedDistanceToPoint(p));
    }

    public double signedDistanceToPoint(Point p){
        return Vector2D.dot(normal, new Vector2D(p.getX(), p.getY()));
    }

    public boolean pointInLine(Point p){
        return distanceToPoint(p) == Math.abs(distance);
    }


}

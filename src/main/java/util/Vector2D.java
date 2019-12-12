package util;

import geometry.geojson.Point;

public class Vector2D {
    private double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D (Point end, Point start){
        this(end.getX() - start.getX(), end.getY() - start.getY());
    }

    public Vector2D(Vector2D v){
        this(v.x, v.y);
    }

    public double x(){
        return x;
    }

    public double y(){
        return  y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double length(){
        return Math.sqrt(dot(this, this));
    }

    public Vector2D getNormalVector(){
        return new Vector2D(-y, x);
    }

    public Vector2D getNormalized(){
        Vector2D n = new Vector2D(this);
        n.normalize();
        return n;
    }

    public void normalize(){
        double l = length();
        if (l < 1e-7)
            return;
        x /= l;
        y /= l;
    }

    public void invert(){
        double t = x;
        this.x = -y;
        this.y = t;
    }

    public static Vector2D mul(Vector2D v, double r){
        return new Vector2D(v.x * r, v.y * r);
    }

    public static double dot(Vector2D l, Vector2D r){
        return l.x * r.x + l.y * r.y;
    }
}

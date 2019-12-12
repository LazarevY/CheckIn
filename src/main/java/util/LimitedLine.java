package util;

import geometry.geojson.Point;

public class LimitedLine extends Line{
    private Point p1, p2;
    public LimitedLine(Point p1, Point p2) {
        super(p1, p2);
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean pointInBoundsLine(Point p){
        return  Math.abs(p1.getX() - p2.getX()) == (Math.abs(p1.getX() - p.getX()) + Math.abs(p2.getX() - p.getX())) &&
                Math.abs(p1.getY() - p2.getY()) == (Math.abs(p1.getY() - p.getY()) + Math.abs(p2.getY() - p.getY()));
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    @Override
    public boolean pointInLine(Point p){
        return super.pointInLine(p) && pointInBoundsLine(p);
    }
}

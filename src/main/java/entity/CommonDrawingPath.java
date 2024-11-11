package entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CommonDrawingPath {
    private final List<Point> points;

    public CommonDrawingPath() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public void clear() {
        points.clear();
    }
}

package entity;

import java.awt.Point;
import java.util.List;

/**
 * The representation of a user in our program.
 */
public interface DrawingPath {

    /**
     * Adds point to the list of points.
     */
    void addPoint(Point point);

    /**
     * Returns the list of points.
     * @return the list of points.
     */
    List<Point> getPoints();

}

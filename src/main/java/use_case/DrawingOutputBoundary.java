package use_case;

import java.awt.Point;

public interface DrawingOutputBoundary {

    void startNewPath(Point startPoint);

    void addPoint(Point point);

    void endCurrentPath();
}

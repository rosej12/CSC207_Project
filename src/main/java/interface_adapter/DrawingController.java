package interface_adapter;

import use_case.DrawingOutputBoundary;

import java.awt.Point;

public class DrawingController {
    private final DrawingOutputBoundary presenter;

    public DrawingController(DrawingOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void startNewPath(Point startPoint){
        presenter.startNewPath(startPoint);
    }

    public void addPointToPath(Point point) {
        presenter.addPoint(point);
    }

    public void endCurrentPath() {
        presenter.endCurrentPath();
    }

    public DrawingPresenter getDrawingPresenter() {
        return (DrawingPresenter) presenter;
    }
}

package interface_adapter.Shape;

import entities.Shape;
import use_case.Shape.ShapeInputBoundary;

public class ShapeController {
    private final ShapeInputBoundary inputBoundary;

    public ShapeController(ShapeInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void drawShape(Shape shape) {
        inputBoundary.drawShape(shape);
    }
}

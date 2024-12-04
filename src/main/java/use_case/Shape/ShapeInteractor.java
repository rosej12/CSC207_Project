package use_case.Shape;

import entities.Shape;

public class ShapeInteractor implements ShapeInputBoundary {
    private final ShapeOutputBoundary outputBoundary;

    public ShapeInteractor(ShapeOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void drawShape(Shape shape) {
        if (shape == null) {
            outputBoundary.prepareFailView("Shape is null");
            return;
        }

        // logic for shape validation
        if (shape.getX1() == shape.getX2() && shape.getY1() == shape.getY2()) {
            outputBoundary.prepareFailView("Invalid shape dimensions.");
        } else {
            // pass the shape to the presenter
            outputBoundary.prepareSuccessView();
        }
    }
}

package interface_adapter.Shape;

import entities.Shape;
import interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapeViewModel extends ViewModel<Boolean> {

    private final List<Shape> shapes = new ArrayList<>();
    private String errorMessage;

    public ShapeViewModel() {
        super("ShapeView");
    }

    /**
     * Sets whether a shape has been successfully drawn.
     *
     * @param isDrawn true if the shape has been successfully drawn
     */
    public void setShapeDrawn(boolean isDrawn) {
        setState(isDrawn); // Update the state using the ViewModel's built-in mechanism
        firePropertyChanged("shapeDrawn");
    }

    /**
     * Retrieves an unmodifiable list of all shapes.
     *
     * @return a list of shapes
     */
    public List<Shape> getShapes() {
        return Collections.unmodifiableList(shapes);
    }

    /**
     * Adds a new shape to the list and notifies listeners.
     *
     * @param shape the shape to add
     */
    public void addShape(Shape shape) {
        shapes.add(shape);
        firePropertyChanged("shapes");
    }

    /**
     * Clears all shapes and notifies listeners.
     */
    public void clearShapes() {
        shapes.clear();
        firePropertyChanged("shapes");
    }

    /**
     * Sets an error message and notifies listeners.
     *
     * @param errorMessage the error message to set
     */
    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;
        firePropertyChanged("error");
    }

    /**
     * Retrieves the current error message.
     *
     * @return the error message
     */
    public String getError() {
        return errorMessage;
    }
}

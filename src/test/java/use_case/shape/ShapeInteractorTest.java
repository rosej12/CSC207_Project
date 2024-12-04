package use_case.shape;

import entities.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.Shape.ShapeInteractor;
import use_case.Shape.ShapeOutputBoundary;

import static org.mockito.Mockito.*;

public class ShapeInteractorTest {

    private ShapeInteractor interactor;
    private ShapeOutputBoundary outputBoundary;

    @BeforeEach
    public void setUp() {
        outputBoundary = mock(ShapeOutputBoundary.class);
        interactor = new ShapeInteractor(outputBoundary);
    }

    @Test
    public void testDrawShapeSuccess() {
        // Arrange: Create a valid shape
        Shape validShape = new Shape(Shape.ShapeType.RECTANGLE, 10, 20, 50, 80, java.awt.Color.RED, 2);

        // Act: Attempt to draw the shape
        interactor.drawShape(validShape);

        // Assert: Verify the success view is prepared
        verify(outputBoundary).prepareSuccessView();
    }

    @Test
    public void testDrawShapeFailNullShape() {
        // Act: Attempt to draw a null shape
        interactor.drawShape(null);

        // Assert: Verify the fail view is prepared with the appropriate error message
        verify(outputBoundary).prepareFailView("Shape is null");
    }

    @Test
    public void testDrawShapeFailInvalidDimensions() {
        // Arrange: Create a shape with invalid dimensions
        Shape invalidShape = new Shape(Shape.ShapeType.LINE, 10, 20, 10, 20, java.awt.Color.BLUE, 2);

        // Act: Attempt to draw the shape
        interactor.drawShape(invalidShape);

        // Assert: Verify the fail view is prepared with the appropriate error message
        verify(outputBoundary).prepareFailView("Invalid shape dimensions.");
    }
}
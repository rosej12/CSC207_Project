package interface_adapters.drawing;

import interface_adapter.Drawing.DrawingPresenter;
import interface_adapter.Render.RenderViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import interface_adapter.Drawing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;

public class DrawingPresenterTest {

    private DrawingPresenter drawingPresenter;
    private DrawingViewModel drawingViewModel;
    private RenderViewModel renderViewModel;
    private ViewManagerModel viewManagerModel;
    private TestPropertyChangeListener drawingListener;
    private TestPropertyChangeListener renderListener;
    private TestPropertyChangeListener viewManagerListener;

    @BeforeEach
    public void setUp() {
        // Mock the View Models
        drawingViewModel = new DrawingViewModel();
        renderViewModel = new RenderViewModel();
        viewManagerModel = new ViewManagerModel();

        // Initialize the presenter
        drawingPresenter = new DrawingPresenter(viewManagerModel, drawingViewModel, renderViewModel);

        // Set up listeners to capture property change events
        drawingListener = new TestPropertyChangeListener();
        renderListener = new TestPropertyChangeListener();
        viewManagerListener = new TestPropertyChangeListener();

        drawingViewModel.addPropertyChangeListener(drawingListener);
        renderViewModel.addPropertyChangeListener(renderListener);
        viewManagerModel.addPropertyChangeListener(viewManagerListener);
    }

    @Test
    public void testPrepareSuccessView() {
        RenderedImage drawing = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        drawingPresenter.prepareSuccessView(drawing);

        // Verify that the drawing is set in the view model
        assertEquals(drawing, drawingViewModel.getState().getDrawing());
        assertNull(drawingViewModel.getState().getError());

        // Verify that a property change event was fired
        assertTrue(drawingListener.eventFired);
    }

    @Test
    public void testPrepareFailView() {
        String errorMessage = "An error occurred while saving the drawing.";
        drawingPresenter.prepareFailView(errorMessage);

        // Verify that the error message is set in the view model
        assertEquals(errorMessage, drawingViewModel.getState().getError());

        // Verify that a property change event was fired
        assertTrue(drawingListener.eventFired);
    }

    @Test
    public void testSwitchToRenderView() {
        Image sketch = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        drawingPresenter.switchToRenderView(sketch);

        // Verify that the view manager model's state is set to the render view's name
        assertEquals(renderViewModel.getViewName(), viewManagerModel.getState());

        // Verify that the sketch is set in the render view model
        assertEquals(sketch, renderViewModel.getState().getSketch());

        // Verify that property change events were fired
        assertTrue(viewManagerListener.eventFired);
        assertTrue(renderListener.eventFired);
    }

    // Helper class to capture property change events
    private static class TestPropertyChangeListener implements PropertyChangeListener {
        boolean eventFired = false;

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            eventFired = true;
        }
    }
}

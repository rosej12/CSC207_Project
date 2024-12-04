package use_case.render;

import data_access.RenderDataAccessObject;
import org.junit.jupiter.api.Test;
import use_case.Render.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class RenderInteractorTest {

    @Test
    public void successTest() {
        RenderInputData inputData = new RenderInputData("A plate of grapes.", getBlankImage(100, 100));
        RenderDataAccessInterface renderDAO = new RenderDataAccessObject();

        RenderOutputBoundary successPresenter = new RenderOutputBoundary() {
            @Override
            public void prepareSuccessView(RenderOutputData outputData) {
                // Check if an image was successfully generated
                assertNotNull(outputData.getRenderedImage(), "Rendered image should not be null.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToDrawingView() {
                fail("Use case switchToDrawingView is unexpected.");
            }
        };

        RenderInputBoundary interactor = new RenderInteractor(renderDAO, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    public void switchToDrawingViewTest() {
        RenderDataAccessInterface renderDAO = new RenderDataAccessObject();

        RenderOutputBoundary successPresenter = new RenderOutputBoundary() {
            @Override
            public void prepareSuccessView(RenderOutputData outputData) {
                fail("Success View is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Fail View is unexpected.");
            }

            @Override
            public void switchToDrawingView() {
                // Nothing should happen on success
            }
        };

        RenderInputBoundary interactor = new RenderInteractor(renderDAO, successPresenter);
        interactor.switchToDrawingView();
    }

    @Test
    public void failureDescriptionTooLongTest() {
        StringBuilder description = new StringBuilder();
        description.append("a".repeat(5500));

        RenderInputData inputData = new RenderInputData(description.toString(), getBlankImage(100, 100));
        RenderDataAccessInterface renderDAO = new RenderDataAccessObject();

        RenderOutputBoundary successPresenter = new RenderOutputBoundary() {
            @Override
            public void prepareSuccessView(RenderOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Description is longer than 5000 characters.", errorMessage);
            }

            @Override
            public void switchToDrawingView() {
                fail("Use case switchToDrawingView is unexpected.");
            }
        };

        RenderInputBoundary interactor = new RenderInteractor(renderDAO, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    public void failureEmptyDescriptionTest() {
        RenderInputData inputData = new RenderInputData("", getBlankImage(100, 100));
        RenderDataAccessInterface renderDAO = new RenderDataAccessObject();

        RenderOutputBoundary successPresenter = new RenderOutputBoundary() {
            @Override
            public void prepareSuccessView(RenderOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Description is empty.", errorMessage);
            }

            @Override
            public void switchToDrawingView() {
                fail("Use case switchToDrawingView is unexpected.");
            }
        };

        RenderInputBoundary interactor = new RenderInteractor(renderDAO, successPresenter);
        interactor.execute(inputData);
    }

    private Image getBlankImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        return image;
    }
}


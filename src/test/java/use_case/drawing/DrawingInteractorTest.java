package use_case.drawing;

import use_case.Drawing.DrawingDataAccessInterface;
import use_case.Drawing.DrawingInteractor;
import use_case.Drawing.DrawingOutputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class DrawingInteractorTest {

    private DrawingDataAccessInterface dataAccess;
    private DrawingOutputBoundary outputBoundary;
    private DrawingInteractor interactor;

    @BeforeEach
    public void setUp() {
        dataAccess = mock(DrawingDataAccessInterface.class);
        outputBoundary = mock(DrawingOutputBoundary.class);
        interactor = new DrawingInteractor(dataAccess, outputBoundary);
    }

    @Test
    public void testExecuteSaveSuccess() throws IOException {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        File file = new File("test.png");

        interactor.executeSave(image, file);

        verify(dataAccess).saveDrawing(image, file);
        verify(outputBoundary).prepareSuccessView(image);
    }

    @Test
    public void testExecuteSaveFailure() throws IOException {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        File file = new File("test.png");

        doThrow(new IOException("Disk full")).when(dataAccess).saveDrawing(image, file);

        interactor.executeSave(image, file);

        verify(outputBoundary).prepareFailView("Error saving drawing: Disk full");
    }

    @Test
    public void testExecuteSaveWithNullImage() {
        File file = new File("test.png");

        interactor.executeSave(null, file);

        verify(outputBoundary).prepareFailView("Drawing is empty");
        verifyNoInteractions(dataAccess);
    }

    @Test
    public void testExecuteClear() {
        interactor.executeClear();

        verify(outputBoundary).prepareSuccessView(null);
    }

    @Test
    public void testSwitchToRenderView() {
        Image image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        interactor.switchToRenderView(image);

        verify(outputBoundary).switchToRenderView(image);
    }
}

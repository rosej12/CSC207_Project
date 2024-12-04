package use_case.Drawing;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class DrawingInteractor implements DrawingInputBoundary {

    private final DrawingDataAccessInterface dataAccess;
    private final DrawingOutputBoundary outputBoundary;

    public DrawingInteractor(DrawingDataAccessInterface dataAccess, DrawingOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void executeSave(RenderedImage image, File file) {
        try {
            if (image != null) {
                dataAccess.saveDrawing(image, file);
                outputBoundary.prepareSuccessView(image);
            }
            else {
                outputBoundary.prepareFailView("Drawing is empty");
            }
        }
        catch (IOException ex) {
            outputBoundary.prepareFailView("Error saving drawing: " + ex.getMessage());
        }
    }

    @Override
    public void executeClear() {
        outputBoundary.prepareSuccessView(null);
    }

    @Override
    public void switchToRenderView(Image sketch) {
        outputBoundary.switchToRenderView(sketch);
    }
}

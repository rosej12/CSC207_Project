package use_cases.Drawing;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class DrawingInteractor implements DrawingInputBoundary {

    private final DrawingOutputBoundary outputBoundary;
    private final DrawingDataAccessInterface dataAccessInterface;

    public DrawingInteractor(DrawingOutputBoundary outputBoundary, DrawingDataAccessInterface dataAccessInterface) {
        this.outputBoundary = outputBoundary;
        this.dataAccessInterface = dataAccessInterface;
    }

    @Override
    public void executeSave(RenderedImage image, File file) {
        try {
            if (image != null) {
                ImageIO.write(image, "png", file);
                outputBoundary.prepareSuccessView(image);
            } else {
                outputBoundary.prepareFailView("Drawing is empty");
            }
        } catch (IOException e) {
            outputBoundary.prepareFailView("Error saving drawing" + e.getMessage());
        }
    }

    @Override
    public void executeClear() {
        outputBoundary.prepareSuccessView(null);
    }
}
